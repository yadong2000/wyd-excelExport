package com.wydexcel.generate;

import com.alibaba.fastjson.JSON;
import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.generate.ExcelConfGenerateImpl;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import com.wydexcel.generate.properties.s.ExcelCellBaseProperties;
import com.wydexcel.generate.properties.s.ExcelFontProperties;
import com.wydexcel.generate.properties.s.ExcelLinkProperties;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

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
public class TestExcelHyperLink {

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
        email.setExcelFieldName("email");
        email.setValue("邮箱");
        email.setExcelStartCellIndex(1);
        //
        ExcelFieldProperties file = new ExcelFieldProperties();
        file.setExcelFieldName("file");
        file.setValue("文件");
        file.setExcelStartCellIndex(2);
        //
        ExcelFieldProperties none = new ExcelFieldProperties();
        none.setExcelFieldName("none");
        none.setValue("none");
        none.setExcelStartCellIndex(3);
        //
        ExcelFieldProperties document = new ExcelFieldProperties();
        document.setExcelFieldName("document");
        document.setValue("文档");
        document.setExcelStartCellIndex(4);
        //
        ExcelFontProperties emailFont = new ExcelFontProperties();
        emailFont.setFontName("Franklin");
        emailFont.setItalic(true);//倾斜
        emailFont.setBold(true);//加粗
        emailFont.setStrikeout(true);//删除线
        emailFont.setIsHeader(0);
        emailFont.setFontColor(IndexedColors.YELLOW1.getIndex());
        emailFont.setFieldName("email");

        ExcelFontProperties fileFont = new ExcelFontProperties();
        fileFont.setFontName("Franklin");
        fileFont.setItalic(true);//倾斜
        fileFont.setBold(false);//加粗
        fileFont.setStrikeout(true);//删除线
        fileFont.setIsHeader(0);
        fileFont.setFieldName("file");
        fileFont.setFontColor(IndexedColors.BLUE.getIndex());

        ExcelCellBaseProperties baseProperties = new ExcelCellBaseProperties();
        baseProperties.setFieldName("email");
        baseProperties.setIsHeader(0);
        baseProperties.setRightBorderColor(IndexedColors.RED.getIndex());
        baseProperties.setBorderRight(BorderStyle.DOUBLE);
        baseProperties.setFillForegroundColor(IndexedColors.RED.getIndex());
        baseProperties.setFillPattern(FillPatternType.NO_FILL);
        //

        conf.setSheetName("sheet1");
        conf.getBases().add(baseProperties);
        conf.getFonts().add(emailFont);
        conf.getFonts().add(fileFont);
        conf.getCells().add(email);
        conf.getCells().add(file);
        conf.getCells().add(title);
        conf.getCells().add(none);
        conf.getCells().add(document);
        excelConfGenerate = new ExcelConfGenerateImpl(placeProperties,new XSSFWorkbook());
    }

    /**
     * 测试 excel Font
     * <p>
     * <p>
     * font_metrics.properties
     */
    @Test
    public void testHyperLink() {
        //
        ExcelLinkProperties urlLink = new ExcelLinkProperties();
        urlLink.setFieldName("title");
        urlLink.setLinkType(HyperlinkType.URL.name());
        urlLink.setIsHeader(0);
        urlLink.setLink("http://www.alibaba.com");
        //
        ExcelLinkProperties emailLink = new ExcelLinkProperties();
        emailLink.setFieldName("email");
        emailLink.setLinkType(HyperlinkType.EMAIL.name());
        emailLink.setIsHeader(0);
        emailLink.setLink("mailto:714037465@qq.com");
        //
        ExcelLinkProperties fileLink = new ExcelLinkProperties();
        fileLink.setFieldName("file");
        fileLink.setLinkType(HyperlinkType.FILE.name());
        fileLink.setIsHeader(0);
        fileLink.setLink("/Users/wangyadong/IdeaProjects/wydexcel/headerFooter.xlsx");
        //
        ExcelLinkProperties noneLink = new ExcelLinkProperties();
        noneLink.setFieldName("none");
        noneLink.setLinkType(HyperlinkType.NONE.name());
        noneLink.setIsHeader(0);
        noneLink.setLink("");
        //
        ExcelLinkProperties documentLink = new ExcelLinkProperties();
        noneLink.setFieldName("document");
        noneLink.setLinkType(HyperlinkType.DOCUMENT.name());
        noneLink.setIsHeader(0);
        noneLink.setLink("'sheet1'!A1");

        conf.getLinks().add(urlLink);
        conf.getLinks().add(emailLink);
        conf.getLinks().add(documentLink);
        conf.getLinks().add(noneLink);
        conf.getLinks().add(fileLink);
        generateExcel(excelConfGenerate.generate(conf.getSheetName()));
    }


    private void generateExcel(ExcelSheetGenerate generateSheet) {
        long start;
        System.out.println(start = System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "我说怎么回事呢" + i);
            map.put("document", "document" + i);
            map.put("file", "file" + i);
            map.put("email", "email" + i);
            map.put("none", "none" + i);
            generateSheet.generate(map);
        }
        excelConfGenerate.write("/Users/wangyadong/test_hyper_link_" + System.currentTimeMillis() + ".xlsx");
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(JSON.toJSONString(placeProperties));
    }
}
