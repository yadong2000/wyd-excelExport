package com.convenient.excel.generate;

import com.convenient.excel.beans.dto.ExcelFieldDTO;
import com.convenient.excel.beans.dto.ExcelRowConfDTO;
import com.convenient.excel.beans.dto.ExcelSheetDTO;
import com.convenient.excel.beans.entity.ExcelField;
import com.convenient.excel.beans.entity.ExcelGetway;
import com.convenient.excel.beans.entity.ExcelSheet;
import com.convenient.excel.util.UniqueIDUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.ReactiveInsertOperation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;


import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelExportImport {
    private Workbook workBook;

    @Autowired
    ReactiveInsertOperation insertOperation;
    @Autowired
    TransactionalOperator operator;


    public ExcelExportImport() throws IOException {
//        workBook = new XSSFWorkbook(new FileInputStream(new File(path)));
    }


    public void importExcel(String path) throws Exception {
        if (null == this.workBook) {
            this.workBook = new XSSFWorkbook(new FileInputStream(new File(path)));
        }
        LocalDateTime now = LocalDateTime.now();
        ExcelGetway getway = new ExcelGetway();
        getway.setGetwayId(UniqueIDUtil.getUniqueID());
        getway.setUpdateTime(now);
        getway.setCreateTime(now);
        getway.setUpdateUserId(1);
        getway.setUserId(1);
        //   Mono<ExcelGetway> using = insertOperation.insert(ExcelGetway.class).using(getway);
        Mono<ExcelField> excelFieldMono = Mono.empty();
        int numberOfSheets = workBook.getNumberOfSheets();
        Mono<ExcelRowConfDTO> rowConfDTOMono = Mono.empty();
        Mono<ExcelSheetDTO> sheetDTOMono = Mono.empty();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheetAt = workBook.getSheetAt(i);

            ExcelSheetDTO excelSheet = new ExcelSheetDTO();
            excelSheet.setExcelSheetName(sheetAt.getSheetName());
            excelSheet.setGetwayId(getway.getGetwayId());
            excelSheet.setUpdateTime(now);
            excelSheet.setCreateTime(now);
            excelSheet.setUpdateUserId(getway.getUserId());
            excelSheet.setUserId(getway.getUserId());
            excelSheet.setExcelSheetId(UniqueIDUtil.getUniqueID());
            sheetDTOMono = sheetDTOMono.then(insertOperation.insert(ExcelSheetDTO.class).using(excelSheet));
            int physicalNumberOfRows = sheetAt.getPhysicalNumberOfRows();
            for (int rowBreak = 0; rowBreak < physicalNumberOfRows; rowBreak++) {
                Row row = sheetAt.getRow(rowBreak);
                if (row == null) {
                    continue;
                }
                ExcelRowConfDTO rowConfDTO = new ExcelRowConfDTO();
                rowConfDTO.setExcelRowId(UniqueIDUtil.getUniqueID());
                rowConfDTO.setSheetId(excelSheet.getExcelSheetId());
                rowConfDTOMono = rowConfDTOMono.then(insertOperation.insert(ExcelRowConfDTO.class).using(rowConfDTO));
                int numberOfCells = row.getPhysicalNumberOfCells();
                for (int i1 = 0; i1 < numberOfCells; i1++) {
                    Cell cell = row.getCell(i1);
                    if (cell == null) {
                        continue;
                    }
                    ExcelField excelField = new ExcelField();
                    excelField.setExcelFieldId(UniqueIDUtil.getUniqueID());
                    excelField.setExcelSheetId(excelSheet.getExcelSheetId());
                    excelField.setExcelStartCellIndex(cell.getColumnIndex());
                    excelField.setExcelEndRowIndex(cell.getColumnIndex());
                    CellType cellType = cell.getCellType();
                    int code = cellType.getCode();
                    String value = null;
                    switch (code) {
                        case -1:
                            break;
                        case 0:
                            value = String.valueOf(cell.getNumericCellValue());
                            break;
                        case 1:
                            value = cell.getStringCellValue();
                            break;
                        case 2:
                            value = cell.getCellFormula();
                            break;
                        case 3:
                            value = cell.getStringCellValue();
                            break;

                    }
                    if (value != null && !value.trim().equals("")) {
                        excelField.setExcelFieldTitle(value);
                    } else {
                        continue;
                    }
                    excelField.setExcelEndRowIndex(cell.getRow().getRowNum());
                    excelField.setExcelStartRowIndex(cell.getRowIndex());
                    excelFieldMono = excelFieldMono.then(insertOperation.insert(ExcelField.class).using(excelField));
                }
            }
        }
        excelFieldMono
                .then(insertOperation.insert(ExcelGetway.class).using(getway))
                .then(rowConfDTOMono)
                .then(sheetDTOMono)
                .as(operator::transactional)
                .log()
                .block();
    }

}
