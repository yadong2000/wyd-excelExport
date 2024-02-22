package com.wydexcel.generate;

import com.alibaba.fastjson.JSON;
import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.generate.ExcelConfGenerateImpl;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import com.wydexcel.generate.properties.s.ExcelCellBaseProperties;
import com.wydexcel.generate.properties.s.ExcelFormatProperties;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 设置单元格的填充方式，以及前景颜色和背景颜色
 * 三点注意：
 * 1.如果需要前景颜色或背景颜色，一定要指定填充方式，两者顺序无所谓；
 * 2.如果同时存在前景颜色和背景颜色，前景颜色的设置要写在前面；
 * 3.前景颜色不是字体颜色。
 */
public class TestExcelDataFormatByJSON extends Base {

    ExcelWorkPlaceProperties placeProperties = new ExcelWorkPlaceProperties();
    ExcelAbstractSheetProperties conf = new ExcelAbstractSheetProperties();
    ExcelConfGenerateImpl excelConfGenerate = null;

    @Before
    public void setUp() {
        placeProperties.setId(UUID.randomUUID().toString());
        Map<String, ExcelAbstractSheetProperties> stringExcelAbstractSheetConfMap = new HashMap<>();
        conf.setColumnWidth(9000);
        stringExcelAbstractSheetConfMap.put("sheet1", conf);
        placeProperties.setMap(stringExcelAbstractSheetConfMap);


        ExcelFieldProperties title = new ExcelFieldProperties();
        title.setExcelFieldName("title");
        title.setValue("标题");
        //
        ExcelFieldProperties email = new ExcelFieldProperties();
        email.setExcelFieldName("datetime");
        email.setValue("当前日期");
        email.setExcelStartCellIndex(1);
        //
        ExcelFieldProperties file = new ExcelFieldProperties();
        file.setExcelFieldName("date");
        file.setValue("当前时间");
        file.setExcelStartCellIndex(2);
        //
        ExcelFieldProperties none = new ExcelFieldProperties();
        none.setExcelFieldName("money");
        none.setValue("金钱");
        none.setExcelStartCellIndex(3);
        //
        ExcelFieldProperties document = new ExcelFieldProperties();
        document.setExcelFieldName("salary");
        document.setValue("工资(元)");
        document.setExcelStartCellIndex(4);
        //
        ExcelFieldProperties money = new ExcelFieldProperties();
        money.setExcelFieldName("dateForLocalDate");
        money.setValue("当前时间111");
        money.setExcelStartCellIndex(5);
        //
//        ExcelFontProperties emailFont = new ExcelFontProperties();
//        emailFont.setFontName("Franklin");
//        emailFont.setItalic(true);//倾斜
//        emailFont.setBold(true);//加粗
//        emailFont.setStrikeout(true);//删除线
//        emailFont.setIsHeader(0);
//        emailFont.setFontColor(IndexedColors.YELLOW1.getIndex());
//        emailFont.setFieldName("email");
//
//        ExcelFontProperties fileFont = new ExcelFontProperties();
//        fileFont.setFontName("Franklin");
//        fileFont.setItalic(true);//倾斜
//        fileFont.setBold(false);//加粗
//        fileFont.setStrikeout(true);//删除线
//        fileFont.setIsHeader(0);
//        fileFont.setFieldName("file");
//        fileFont.setFontColor(IndexedColors.BLUE.getIndex());
//
        ExcelCellBaseProperties baseProperties = new ExcelCellBaseProperties();
        baseProperties.setFieldName("datetime");
        baseProperties.setIsHeader(2);
        baseProperties.setRightBorderColor(IndexedColors.RED.getIndex());
        baseProperties.setBorderRight(BorderStyle.DOUBLE);
        baseProperties.setColumnWidth(20);
        //
        conf.setSheetName("sheet1");
        conf.getBases().add(baseProperties);
//        conf.getFonts().add(emailFont);
//        conf.getFonts().add(fileFont);
        conf.getCells().add(email);
        conf.getCells().add(money);
        conf.getCells().add(file);
        conf.getCells().add(title);
        conf.getCells().add(none);
        conf.getCells().add(document);


    }


    /**
     * 测试 excel Font
     * <p>
     * datetime，date，money，document，data
     * <p>
     * font_metrics.properties
     */
    @Test
    public void testDataFormat() {
        ExcelFormatProperties dateDataFormat = new ExcelFormatProperties();
        dateDataFormat.setFieldName("date");
        dateDataFormat.setDataformat("yyyy-MM-dd");
        dateDataFormat.setIsHeader(2);
        conf.getDataFormat().add(dateDataFormat);
        //
        ExcelFormatProperties moneyFormat = new ExcelFormatProperties();
        moneyFormat.setFieldName("money");
        moneyFormat.setDataformat("#,##0.00");
        moneyFormat.setIsHeader(2);
        conf.getDataFormat().add(moneyFormat);
        //
        ExcelFormatProperties dateTimeDataFormat = new ExcelFormatProperties();
        dateTimeDataFormat.setFieldName("datetime");
        dateTimeDataFormat.setDataformat("yyyy-MM-dd HH:mm:ss");
        dateTimeDataFormat.setIsHeader(2);
        conf.getDataFormat().add(dateTimeDataFormat);
        //
        ExcelFormatProperties salaryDataFormat = new ExcelFormatProperties();
        salaryDataFormat.setFieldName("salary");
        salaryDataFormat.setDataformat("¥#,##0");
        salaryDataFormat.setIsHeader(2);
        conf.getDataFormat().add(salaryDataFormat);


        ExcelFormatProperties dateForLocalDateDataFormat = new ExcelFormatProperties();
        dateForLocalDateDataFormat.setFieldName("dateForLocalDate");
        dateForLocalDateDataFormat.setDataformat("yyyy-MM-dd");
        dateForLocalDateDataFormat.setIsHeader(2);
        conf.getDataFormat().add(dateForLocalDateDataFormat);

        //
        ExcelFormatProperties titleDataFormat = new ExcelFormatProperties();
        titleDataFormat.setFieldName("title");
        titleDataFormat.setDataformat("yyyy-MM-dd");
        titleDataFormat.setIsHeader(2);
        conf.getDataFormat().add(titleDataFormat);

        excelConfGenerate = new ExcelConfGenerateImpl(placeProperties, new XSSFWorkbook());
        generateExcel(excelConfGenerate.generate(conf.getSheetName()));
    }


    private void generateExcel(ExcelSheetGenerate generateSheet) {
        long start;
        //  datetime，date，money，document，data
        System.out.println(start = System.currentTimeMillis());
        for (int i = 0; i < 2; i++) {
            generateSheet
                    .getRowGenerator()
                    .generateCell("title", LocalDateTime.now().toString())
                    .generateCell("datetime", LocalDateTime.now())
                    .generateCell("dateForLocalDate", LocalDate.now())
                    .generateCell("money", Double.valueOf("2045.0234324"))
                    .generateCell("salary", Double.valueOf("1900.0234324"))
                    .generateCell("date", new Date())
                    .completeCurrentRow();
        }
        excelConfGenerate.write("test_dataFormat_" + System.currentTimeMillis() + ".xlsx");
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(JSON.toJSONString(placeProperties));
    }
}
