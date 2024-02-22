package com.wydexcel.generate;

import com.alibaba.fastjson.JSON;
import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.generate.ExcelConfGenerateImpl;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import com.wydexcel.generate.properties.s.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestExcelRichText {

    ExcelWorkPlaceProperties placeProperties = new ExcelWorkPlaceProperties();
    ExcelAbstractSheetProperties conf = new ExcelAbstractSheetProperties();
    ExcelConfGenerateImpl excelConfGenerate = null;

    @Before
    public void setUp() {
        placeProperties.setId(UUID.randomUUID().toString());
        Map<String, ExcelAbstractSheetProperties> stringExcelAbstractSheetConfMap = new HashMap<>();
        stringExcelAbstractSheetConfMap.put("sheet1", conf);
        placeProperties.setMap(stringExcelAbstractSheetConfMap);


        ExcelFieldProperties fieldDTO = new ExcelFieldProperties();
        fieldDTO.setExcelFieldName("title");
        fieldDTO.setValue("标题");

        //
        ExcelFieldProperties fieldDTO1 = new ExcelFieldProperties();
        fieldDTO1.setExcelFieldName("desc");
        fieldDTO1.setValue("描述");
        fieldDTO1.setExcelStartCellIndex(1);
        ExcelCellBaseProperties excelCellBaseProperties1 = new ExcelCellBaseProperties();
        excelCellBaseProperties1.setAlignment(HorizontalAlignment.CENTER);
        excelCellBaseProperties1.setVerticalAlignment(VerticalAlignment.CENTER);
        //
        excelCellBaseProperties1.setLeftBorderColor((short) 10);
        excelCellBaseProperties1.setRightBorderColor((short) 10);


        ExcelFontProperties titleFont = new ExcelFontProperties();
        titleFont.setFontName("Franklin");
        titleFont.setItalic(true);//倾斜
        titleFont.setBold(true);//加粗
        titleFont.setStrikeout(true);//删除线
        titleFont.setIsHeader(1);
        titleFont.setFontColor((short) 12);
        titleFont.setFieldName("title");

        ExcelFontProperties descFont = new ExcelFontProperties();
        descFont.setFontName("Franklin");
        descFont.setItalic(true);//倾斜
        descFont.setBold(false);//加粗
        descFont.setStrikeout(false);//删除线
        descFont.setIsHeader(2);
        descFont.setFieldName("desc");
        descFont.setFontColor((short) 4);


        ExcelCellBaseProperties excelCellBaseProperties = new ExcelCellBaseProperties();
        excelCellBaseProperties.setFieldName("desc");
        excelCellBaseProperties.setWrapTest(true);
        excelCellBaseProperties.setIsHeader(1);
        excelCellBaseProperties.setRightBorderColor(IndexedColors.RED.getIndex());
        excelCellBaseProperties.setBorderRight(BorderStyle.DOUBLE);
        excelCellBaseProperties.setFillForegroundColor(IndexedColors.RED.getIndex());

        excelCellBaseProperties.setFillPattern(FillPatternType.BRICKS);
        conf.getBases().add(excelCellBaseProperties);
        conf.setSheetName("sheet1");
        conf.getCells().add(fieldDTO1);
        conf.getFonts().add(titleFont);
        conf.getFonts().add(descFont);
        conf.getCells().add(fieldDTO);

        ExcelFieldProperties date = new ExcelFieldProperties();
        date.setExcelFieldName("date");
        date.setValue("时间");
        date.setExcelStartCellIndex(2);

        ExcelCellBaseProperties dateProperties = new ExcelCellBaseProperties();
        dateProperties.setFieldName("date");
        dateProperties.setWrapTest(true);
        conf.getCells().add(date);
        conf.getBases().add(dateProperties);


    }

    /**
     * 测试 excel Font
     * <p>
     * <p>
     * font_metrics.properties
     */
    @Test
    public void testRichText() {
        ExcelRichTextCollectionProperties titleRichText = new ExcelRichTextCollectionProperties();
        titleRichText.setIsHeader(1);
        titleRichText.setFieldName("date");


        List<ExcelRichTextProperties> richTextPropertieList = titleRichText.getRichTextProperties();

        ExcelFontProperties excelFontProperties = new ExcelFontProperties();
        excelFontProperties.setBold(true);
        excelFontProperties.setRed(255);
        ExcelRichTextProperties richTextProperties = new ExcelRichTextProperties();
        richTextProperties.setFont(excelFontProperties);
        richTextProperties.setText("你们是在生活\n打发打发的");

//
        ExcelRichTextProperties excelRichTextProperties1 = new ExcelRichTextProperties();
        ExcelFontProperties excelFontProperties1 = new ExcelFontProperties();
        excelFontProperties1.setBold(true);
//        excelFontProperties1.setItalic(true);
//        excelFontProperties1.setStrikeout(true);
        excelFontProperties1.setGreen(255);
        excelRichTextProperties1.setText("只有我是 \n 在生存");
        excelRichTextProperties1.setFont(excelFontProperties1);
//
        ExcelRichTextProperties excelRichTextProperties2 = new ExcelRichTextProperties();
        excelRichTextProperties2.setText("\n--来自于祥子的说法");
        ExcelFontProperties excelFontProperties2 = new ExcelFontProperties();
        excelFontProperties2.setBold(true);
        excelFontProperties2.setBlue(255);
        excelRichTextProperties2.setFont(excelFontProperties2);
        richTextPropertieList.add(richTextProperties);
        richTextPropertieList.add(excelRichTextProperties1);
        richTextPropertieList.add(excelRichTextProperties2);

        conf.getRichTexts().add(titleRichText);
        excelConfGenerate = new ExcelConfGenerateImpl(placeProperties,new XSSFWorkbook());
        generateExcel(excelConfGenerate.generate(conf.getSheetName()));


    }


    private void generateExcel(ExcelSheetGenerate generateSheet) {
        long start;
        System.out.println(start = System.currentTimeMillis());
//        for (int i = 0; i < 9; i++) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("title", "我说怎么回事呢" + i);
//            map.put("desc", "我回来了" + i);
//            map.put("date", "我的野蛮女友直冲式发型" + i);
//            generateSheet.generate(map);
//        }
//        excelConfGenerate.writeToDisk("" + System.currentTimeMillis() + ".xlsx");
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(JSON.toJSONString(placeProperties));
    }
}
