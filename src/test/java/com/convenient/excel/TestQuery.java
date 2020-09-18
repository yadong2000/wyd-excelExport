package com.convenient.excel;


import com.convenient.excel.beans.dto.ExcelFieldDTO;
import com.convenient.excel.beans.entity.ExcelSheet;
import com.convenient.excel.controller.ExcelExportHandler;
import com.convenient.excel.generate.ExcelExportGenerate;
import com.convenient.excel.generate.ExcelExportImport;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.ReactiveSelectOperation;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = WydExportApplication.class)
@TestPropertySource(value = {"classpath:application.properties"})
@RunWith(SpringRunner.class)
public class TestQuery {


    @Autowired
    ReactiveSelectOperation selectOperation;
    @Autowired
    ExcelExportHandler excelExportHandler;
    public static String para = "{\"startTime\":\"2020-01\",\"endTime\":\"2020-08\",\"stationCodes\":[380,3800,56,360],\"dateType\":\"month\",\"orderFiled\":\"report_time\",\"orderType\":\"asc\",\"pageNum\":1,\"pageSize\":10}";
    public static final String url = "/config/event/";
    @Autowired
    ExcelExportImport excelExportImport;

    @Test
    public void testQuery() throws IOException {
        Map<ExcelSheet, List<ExcelFieldDTO>> excelBody = excelExportHandler.selectExcelBody(url, Flux.just(para));
        ExcelExportGenerate excelExportGenerate = new ExcelExportGenerate();
        excelBody.forEach((sheet, fields) -> {
            Sheet sheet1 = excelExportGenerate.generateSheet(sheet);
            excelExportGenerate.generateHeadCell(sheet1, fields);
            excelExportGenerate.generateBodyRow(Collections.EMPTY_LIST);
        });
        excelExportGenerate.write(new FileOutputStream(new File("D:\\test\\excel\\" + System.currentTimeMillis() + ".xlsx")));
    }




    @Test
    public void importExcel() throws Exception {
        String path = "D:\\test\\excel\\04-组串式逆变器报表.xlsx";
        excelExportImport.importExcel(path);
    }


}
