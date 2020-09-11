package com.convenient.excel.controller;

import com.alibaba.fastjson.JSONObject;
import com.convenient.excel.beans.dto.ExcelCellstyleConfigDTO;
import com.convenient.excel.beans.dto.ExcelFieldDTO;
import com.convenient.excel.beans.dto.ExcelSheetDTO;
import com.convenient.excel.beans.entity.ExcelCellstyleConfig;
import com.convenient.excel.beans.entity.ExcelField;
import com.convenient.excel.beans.entity.ExcelGetway;
import com.convenient.excel.beans.entity.ExcelSheet;
import com.convenient.excel.beans.param.ExcelCellstyleConfigParameter;
import com.convenient.excel.beans.param.ExcelFieldParameter;
import com.convenient.excel.beans.param.ExcelGetwayParameter;
import com.convenient.excel.beans.param.ExcelSheetParameter;
import com.convenient.excel.generate.ExcelExportGenerate;
import com.convenient.excel.util.BeanToColumn;
import com.convenient.excel.util.ColumnCoverBean;
import com.convenient.excel.util.ControllerUtil;
import com.convenient.excel.util.UniqueIDUtil;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.ReactiveDeleteOperation;
import org.springframework.data.r2dbc.core.ReactiveInsertOperation;
import org.springframework.data.r2dbc.core.ReactiveUpdateOperation;
import org.springframework.data.relational.core.query.Update;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component
public class ExcelExportHandler {


    @Autowired
    DatabaseClient databaseClient;
    @Autowired
    ReactiveInsertOperation insertOperation;
    @Autowired
    ReactiveUpdateOperation updateOperation;
    @Autowired
    ReactiveDeleteOperation deleteOperation;
    @Autowired
    TransactionalOperator operator;


    public Mono<ServerResponse> selectExcelBody(final ServerRequest request) {
        String url = request.queryParam("url").get();
        return ControllerUtil.BODYBUILDER.bodyValue(selectExcelBody(url, ControllerUtil.GET_REQUEST_BODY.apply(request)));
    }

    public Map<ExcelSheet, List<ExcelFieldDTO>> selectExcelBody(final String url, final Flux<String> param) {
        Map<ExcelSheet, List<ExcelFieldDTO>> collect = new HashMap<>();
        if (StringUtils.isEmpty(url)) {
            return collect;
        }
        List<Long> longList = new ArrayList<>();
        Map<Long, ExcelSheet> map = new HashMap();
        selectExcelsheetByMainId(url, param.blockFirst())
                .toStream()
                .forEach(sheet -> {
                    longList.add(sheet.getExcelSheetId());
                    map.put(sheet.getExcelSheetId(), sheet);
                });
        if (CollectionUtils.isEmpty(longList)) {
            return collect;
        }
        getExcelFieldBySheetIds(longList)
                .toStream()
                .collect(toList())
                .stream()
                .collect(groupingBy(ExcelFieldDTO::getExcelSheetId))
                .forEach((k, v) -> {
                    collect.put((map.get(k)), v);
                });
        return collect;
    }

    public Mono<ExcelSheetDTO> insertSheet(final ExcelSheetDTO excelSheet) {
        Mono<ExcelSheetDTO> using = insertOperation.insert(ExcelSheetDTO.class).using(excelSheet);
        return using;
    }

    @SneakyThrows
    public Mono<ServerResponse> insertSheet(final ServerRequest request) {
        String param = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        ExcelSheetParameter excelSheet = JSONObject.parseObject(param, ExcelSheetParameter.class);
        excelSheet.setExcelSheetId(UniqueIDUtil.getUniqueID());
        ExcelSheetDTO sheetDTO = new ExcelSheetDTO();
        BeanUtils.copyProperties(excelSheet, sheetDTO);
        return ControllerUtil.BODYBUILDER.bodyValue(insertSheet(sheetDTO).block());
    }

    @SneakyThrows
    public Mono<ServerResponse> insertMain(final ServerRequest request) {
        String param = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        ExcelGetwayParameter excelGetway = JSONObject.parseObject(param, ExcelGetwayParameter.class);
        excelGetway.setGetwayId(UniqueIDUtil.getUniqueID());
        ExcelGetway getway = new ExcelGetway();
        BeanUtils.copyProperties(excelGetway, getway);
        return ControllerUtil.BODYBUILDER.bodyValue(insertOperation.insert(ExcelGetway.class).using(getway).block());
    }

    @SneakyThrows
    public Mono<ServerResponse> insertCell(final ServerRequest request) {
        String param = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        ExcelCellstyleConfigParameter cellstyleConfigParameter = JSONObject.parseObject(param, ExcelCellstyleConfigParameter.class);
        cellstyleConfigParameter.setExcelCellstyleId(UniqueIDUtil.getUniqueID());
        ExcelCellstyleConfig cellstyleConfig = new ExcelCellstyleConfig();
        BeanUtils.copyProperties(cellstyleConfigParameter, cellstyleConfig);
        Mono<ExcelCellstyleConfig> using = insertOperation.insert(ExcelCellstyleConfig.class).using(cellstyleConfig);
        return ControllerUtil.BODYBUILDER.bodyValue(using.block());
    }

    @SneakyThrows
    public Mono<ServerResponse> insertField(final ServerRequest request) {
        String param = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        ExcelFieldParameter excelField = JSONObject.parseObject(param, ExcelFieldParameter.class);
        excelField.setExcelFieldId(UniqueIDUtil.getUniqueID());
        ExcelFieldDTO fieldDTO = new ExcelFieldDTO();
        BeanUtils.copyProperties(excelField, fieldDTO);
        ExcelCellstyleConfigDTO configDTO = new ExcelCellstyleConfigDTO();
        configDTO.setExcelCellstyleId(UniqueIDUtil.getUniqueID());
        BeanUtils.copyProperties(excelField, configDTO);
        Mono<ExcelCellstyleConfigDTO> as = insertOperation
                .insert(ExcelFieldDTO.class)
                .using(fieldDTO)
                .then(insertOperation
                        .insert(ExcelCellstyleConfigDTO.class)
                        .using(configDTO))
                .as(operator::transactional);
        return ControllerUtil.BODYBUILDER.bodyValue(as.block());
    }

    private Flux<ExcelFieldDTO> getExcelFieldBySheetIds(Collection<Long> sheetIds) {
        String sql = "select ef.excel_sheet_id, ef.excel_field_id, ef.excel_field_name, ef.excel_field_title, ef.excel_start_row_index, ef.excel_end_row_index, ef.excel_start_cell_index," +
                " ef.excel_end_cell_index, ef.default_value , ecc.shrink_to_fit, ecc.border_left, ecc.border_right, ecc.border_top, ecc.border_bottom,ecc.bottom_border_color, ecc.data_format," +
                " ecc.excel_cellstyle_id, ecc.fill_fore_ground_color, ecc.fill_pattern,ecc.font_id,ecc.hidden,ecc.horizontal_alignment, ecc.indention, ecc.left_border_color," +
                " ecc.locked, ecc.quote_prefixed, ecc.right_border_color, ecc.rotation,ecc.rotation, ecc.top_border_color,ecc.vertical_alignment,ecc.wrap_test, ef.cell_comment," +
                " ef.cell_formula, ef.hyperlink,ef.column_width, ef1.bold, ef1.charset,ef1.color, ef1.font_height, ef1.font_id as fontId ,ef1.font_height_points, ef1.font_name,ef1.italic, ef1.strikeout, ef1.type_offset,ef1.under_line " +
                " FROM excel_field ef " +
                "LEFT JOIN excel_cellstyle_config ecc ON ef.excel_field_id = ecc.excel_field_id " +
                "LEFT JOIN excel_font ef1 ON  ef1.excel_cellstyle_id=ecc.excel_cellstyle_id " +
                "WHERE ef.excel_sheet_id in (:sheetIds)";
        return databaseClient.sql(sql)
                .bind("sheetIds", sheetIds)
                .map((row, metadata) -> new ColumnCoverBean<ExcelFieldDTO>().COVER_MAP_TO_BEAN
                        .apply(ColumnCoverBean.COVER_COLUMN_TO_MAP_CAMEL
                                .apply(row, metadata), ExcelFieldDTO.class))
                .all()
                .log();
    }

    private Flux<ExcelSheet> selectExcelsheetByMainId(String url, String param) {
        String sql = "SELECT essc.* ,es.* FROM excel_sheet es LEFT JOIN excel_sheet_style_config essc ON es.excel_sheet_id = essc.excel_sheet_id LEFT JOIN excel_getway g ON g.getway_id=es.getway_id";
        Flux<ExcelSheet> param1 = databaseClient
                .sql(sql + " WHERE g.url=:url")
                .bind("url", url)
                .map((row, metadata) -> {
                    return new ColumnCoverBean<ExcelSheet>().COVER_MAP_TO_BEAN.apply(ColumnCoverBean.COVER_COLUMN_TO_MAP_CAMEL.apply(row, metadata), ExcelSheet.class);
                })
                .all()
                .doOnError(e -> {
                    e.printStackTrace();
                });
        return param1;
    }

    public Mono<ServerResponse> updatetSheet(final ServerRequest request) {
        String first = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        ExcelSheet excelSheet = JSONObject.parseObject(first, ExcelSheet.class);
        Mono<Integer> id = updateOperation
                .update(ExcelSheet.class)
                .matching(ControllerUtil.QUERY.apply("excel_sheet_id", excelSheet.getExcelSheetId()))
                .apply(Update.from(new BeanToColumn().getClassField(excelSheet)));
        return ControllerUtil.BODYBUILDER.bodyValue(id.block());
    }

    public Mono<ServerResponse> updateMain(final ServerRequest request) {
        String first = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        ExcelGetway excelGetway = JSONObject.parseObject(first, ExcelGetway.class);
        Mono<Integer> id = updateOperation
                .update(ExcelGetway.class)
                .matching(ControllerUtil.QUERY.apply("getway_id", excelGetway.getGetwayId()))
                .apply(Update.from(new BeanToColumn().getClassField(excelGetway)));
        return ControllerUtil.BODYBUILDER.bodyValue(id.block());
    }

    public Mono<ServerResponse> updateField(final ServerRequest request) {
        String first = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        ExcelField excelField = JSONObject.parseObject(first, ExcelField.class);
        Mono<Integer> id = updateOperation
                .update(ExcelGetway.class)
                .matching(ControllerUtil.QUERY.apply("excel_field_id", excelField.getExcelFieldId()))
                .apply(Update.from(new BeanToColumn().getClassField(excelField)));
        return ControllerUtil.BODYBUILDER.bodyValue(id.block());
    }

    public Mono<ServerResponse> updateExcelfifter(final ServerRequest request) {

        return ControllerUtil.BODYBUILDER.bodyValue(Mono.empty().block());
    }

    public Mono<ServerResponse> updateCell(final ServerRequest request) {
        String first = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        ExcelCellstyleConfig excelCellConfig = JSONObject.parseObject(first, ExcelCellstyleConfig.class);
        Mono<Integer> id = updateOperation
                .update(ExcelCellstyleConfig.class)
                .matching(ControllerUtil.QUERY.apply("excel_cellstyle_id", excelCellConfig.getExcelCellstyleId()))
                .apply(Update.from(new BeanToColumn().getClassField(excelCellConfig)));
        return ControllerUtil.BODYBUILDER.bodyValue(id.block());
    }

    public Mono<ServerResponse> deleteSheet(final ServerRequest request) {
        String first = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        Mono<Integer> mono = deleteOperation.delete(ExcelSheet.class).matching(ControllerUtil.QUERY.apply("excel_sheet_id", first)).all();
        return ControllerUtil.BODYBUILDER.bodyValue(mono.block());
    }

    public Mono<ServerResponse> deleteMain(final ServerRequest request) {
        String first = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        Mono<Integer> as = deleteOperation.delete(ExcelGetway.class).matching(ControllerUtil.QUERY.apply("getway_id", first)).all()
                .then(deleteOperation.delete(ExcelSheet.class).matching(ControllerUtil.QUERY.apply("getway_id", first)).all()).as(operator::transactional);
        return ControllerUtil.BODYBUILDER.bodyValue(as.block());
    }

    public Mono<ServerResponse> deleteExcelfifter(ServerRequest request) {

        return ControllerUtil.BODYBUILDER.bodyValue(Mono.empty());
    }

    public Mono<ServerResponse> deleteCell(final ServerRequest request) {
        String first = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        Mono<Integer> mono = deleteOperation.delete(ExcelCellstyleConfig.class).matching(ControllerUtil.QUERY.apply("excel_cell_id", first)).all();
        return ControllerUtil.BODYBUILDER.bodyValue(mono.block());
    }

    public Mono<ServerResponse> insertFifter(final ServerRequest request) {
        return null;
    }


    public Mono<ServerResponse> deleteField(final ServerRequest request) {
        String first = ControllerUtil.GET_REQUEST_BODY.apply(request).blockFirst();
        Mono<Integer> mono = deleteOperation.delete(ExcelField.class).matching(ControllerUtil.QUERY.apply("excel_field_id", first)).all();
        return ControllerUtil.BODYBUILDER.bodyValue(mono.block());
    }

    public Mono<ServerResponse> export(ServerRequest request) {
        Map<ExcelSheet, List<ExcelFieldDTO>> excelBody = selectExcelBody(request.queryParam("url").get(), ControllerUtil.GET_REQUEST_BODY.apply(request));
        ExcelExportGenerate excelExportGenerate = new ExcelExportGenerate();
        excelBody.forEach((sheet, fields) -> {
            Sheet sheet1 = excelExportGenerate.generateSheet(sheet);
            excelExportGenerate.generateHeadCell(sheet1, fields);
            excelExportGenerate.generateBodyRow(Collections.EMPTY_LIST);
        });
        return null;
    }
}