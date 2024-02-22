package com.wydexcel.generate;

import com.alibaba.fastjson.JSON;
import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.generate.ExcelConfGenerateImpl;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import com.wydexcel.generate.properties.s.ExcelCellBaseProperties;
import com.wydexcel.generate.properties.s.ExcelFreezePaneProperties;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestExcelfreezePane {

    ExcelWorkPlaceProperties placeProperties = new ExcelWorkPlaceProperties();
    ExcelAbstractSheetProperties conf = new ExcelAbstractSheetProperties();
    ExcelConfGenerateImpl excelConfGenerate = null;

    @Before
    public void setUp() {
        placeProperties.setId(System.currentTimeMillis() + "");
        Map<String, ExcelAbstractSheetProperties> stringExcelAbstractSheetConfMap = new HashMap<>();
//        conf.setColumnWidth(10000);
        stringExcelAbstractSheetConfMap.put("sheet1", conf);
        placeProperties.setMap(stringExcelAbstractSheetConfMap);
        excelConfGenerate = new ExcelConfGenerateImpl(placeProperties,new SXSSFWorkbook());

        ExcelFieldProperties fieldDTO = new ExcelFieldProperties();
        fieldDTO.setExcelFieldName("title");
        fieldDTO.setValue("标题");


        conf.getCells().add(fieldDTO);
        //
        ExcelFieldProperties fieldDTO1 = new ExcelFieldProperties();
        ExcelCellBaseProperties excelCellBaseProperties1 =new ExcelCellBaseProperties();
        fieldDTO1.setExcelFieldName("desc");
        fieldDTO1.setValue("描述");
        excelCellBaseProperties1.setIndention((short) 3);
        fieldDTO1.setExcelStartCellIndex(1);
        excelCellBaseProperties1.setColumnWidth(25 * 256 + 185);
        excelCellBaseProperties1.setFieldName("title");
        excelCellBaseProperties1.setAlignment(HorizontalAlignment.CENTER);
        excelCellBaseProperties1.setVerticalAlignment(VerticalAlignment.CENTER);
        //
        excelCellBaseProperties1.setLeftBorderColor(IndexedColors.RED.getIndex());
        excelCellBaseProperties1.setRightBorderColor(IndexedColors.GOLD.getIndex());
        excelCellBaseProperties1.setBorderRight(BorderStyle.DASH_DOT);
//        fieldDTO1.setRotation((short)30);
        excelCellBaseProperties1.setFillPattern(FillPatternType.BRICKS);

        excelCellBaseProperties1.setFillBackgroundColor(IndexedColors.RED.getIndex());
        conf.getBases().add(excelCellBaseProperties1);
        conf.setSheetName("sheet1");
        conf.getCells().add(fieldDTO1);
        ExcelCellBaseProperties excelCellBaseProperties =new ExcelCellBaseProperties();
        excelCellBaseProperties.setFieldName("desc");
        excelCellBaseProperties.setIsHeader(1);
        excelCellBaseProperties.setRightBorderColor(IndexedColors.RED.getIndex());
        excelCellBaseProperties.setBorderRight(BorderStyle.DOUBLE);
        excelCellBaseProperties.setFillForegroundColor(IndexedColors.RED.getIndex());
        /* 设置单元格的填充方式，以及前景颜色和背景颜色
           三点注意：
           1.如果需要前景颜色或背景颜色，一定要指定填充方式，两者顺序无所谓；
           2.如果同时存在前景颜色和背景颜色，前景颜色的设置要写在前面；
           3.前景颜色不是字体颜色。
  */
        excelCellBaseProperties.setFillPattern(FillPatternType.BRICKS);
        conf.getBases().add(excelCellBaseProperties);
        conf.getBases().add(excelCellBaseProperties1);
    }

    /**
     * 测试 excel Font
     * <p>
     * <p>
     * font_metrics.properties
     */
//    @Test
    public void testExcelFreezePane() {
        List<ExcelFreezePaneProperties> freezes = conf.getFreezes();
        ExcelFreezePaneProperties freezePaneProperties=new ExcelFreezePaneProperties();
        freezePaneProperties.setCellNum(8);
        freezePaneProperties.setRowNum(3);
        freezePaneProperties.setFirstCellNum(12);
        freezePaneProperties.setFirstRowNum(5);
        freezes.add(freezePaneProperties);
        generateExcel(excelConfGenerate.generate(conf.getSheetName()));
    }


    private void generateExcel(ExcelSheetGenerate generateSheet) {
        long start;
        System.out.println(start = System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", "我说怎么回事呢" + i);
            map.put("desc", "desc" + i);
            generateSheet.generate(map);
        }
        excelConfGenerate.write("test_freezepan_" + System.currentTimeMillis() + ".xlsx");
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(JSON.toJSONString(placeProperties));
    }
}
