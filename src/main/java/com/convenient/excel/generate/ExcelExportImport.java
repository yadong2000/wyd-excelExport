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
        Mono<ExcelField> then2 = null;
        int numberOfSheets = workBook.getNumberOfSheets();
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
            Mono<ExcelSheetDTO> then = (insertOperation.insert(ExcelSheetDTO.class).using(excelSheet));
            int physicalNumberOfRows = sheetAt.getPhysicalNumberOfRows();
            for (int rowBreak = 0; rowBreak < physicalNumberOfRows; rowBreak++) {
                Row row = sheetAt.getRow(rowBreak);
                if (row == null) {
                    continue;
                }

                ExcelRowConfDTO rowConfDTO = new ExcelRowConfDTO();
                rowConfDTO.setExcelRowId(UniqueIDUtil.getUniqueID());
                rowConfDTO.setSheetId(excelSheet.getExcelSheetId());
                Mono<ExcelRowConfDTO> then1 = then.then(insertOperation.insert(ExcelRowConfDTO.class).using(rowConfDTO));
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
                    if (cell.getStringCellValue() != null) {
                        excelField.setExcelFieldTitle(cell.getStringCellValue());
                    }
                    if(cell.getDateCellValue()!=null){
                        excelField.setExcelFieldTitle(cell.getDateCellValue().toString());
                    }



                    excelField.setExcelEndRowIndex(cell.getRow().getRowNum());
                    excelField.setExcelStartRowIndex(cell.getRowIndex());
                    then2 = then1.then(insertOperation.insert(ExcelField.class).using(excelField));
                }
            }
            then2
                    .then(insertOperation.insert(ExcelGetway.class).using(getway))
                    .as(operator::transactional)
                    .log()
                    .block();

        }
    }

}
