package com.wydexcel.generate;

import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.s.ExcelCellBaseProperties;
import com.wydexcel.generate.properties.s.ExcelFontProperties;
import com.wydexcel.generate.properties.s.ExcelFormatProperties;
import org.apache.poi.ss.usermodel.*;

public class Base {

    public Base() {
        ExcelCellBaseProperties titleProperties = new ExcelCellBaseProperties();
        titleProperties.setId("common_for_title_base");
        titleProperties.setAlignment(HorizontalAlignment.CENTER);
        titleProperties.setVerticalAlignment(VerticalAlignment.CENTER);
        titleProperties.setLeftBorderColor((short) 10);
        titleProperties.setRightBorderColor((short) 10);
        ExcelContextAllContext.getInstance().setUsingMap(titleProperties);
        //
        ExcelCellBaseProperties descProperties = new ExcelCellBaseProperties();
        descProperties.setId("common_for_desc_base");
        descProperties.setIsHeader(1);
        descProperties.setRightBorderColor(IndexedColors.RED.getIndex());
        descProperties.setBorderRight(BorderStyle.DOUBLE);
        descProperties.setFillForegroundColor(IndexedColors.RED.getIndex());
        descProperties.setFillPattern(FillPatternType.BRICKS);
        ExcelContextAllContext.getInstance().setUsingMap(descProperties);
        //
        ExcelFontProperties titleFont = new ExcelFontProperties();
        titleFont.setFontName("Franklin");
        titleFont.setId("title_font_id");
        titleFont.setItalic(true);//倾斜
        titleFont.setBold(true);//加粗
        titleFont.setStrikeout(true);//删除线
        titleFont.setIsHeader(0);
        titleFont.setFontColor((short) 12);
        ExcelContextAllContext.getInstance().setUsingMap(titleFont);
        //
        ExcelFontProperties descFont = new ExcelFontProperties();
        descFont.setFontName("Franklin");
        descFont.setItalic(false);//倾斜
        descFont.setBold(false);//加粗
        descFont.setStrikeout(false);//删除线
        descFont.setIsHeader(0);
        descFont.setId("desc_font_id");
        descFont.setFieldName("desc");
        descFont.setFontColor((short) 24);
        ExcelContextAllContext.getInstance().setUsingMap(descFont);
        //
        ExcelFormatProperties dateDataFormat = new ExcelFormatProperties();
        dateDataFormat.setDataformat("yyyy-MM-dd");
        dateDataFormat.setId("yyyy-MM-dd");
        dateDataFormat.setIsHeader(2);
        ExcelContextAllContext.getInstance().setUsingMap(dateDataFormat);
        //
        ExcelFormatProperties moneyFormat = new ExcelFormatProperties();
        moneyFormat.setDataformat("#,##0.00");
        moneyFormat.setId("money-format");
        moneyFormat.setIsHeader(2);
        ExcelContextAllContext.getInstance().setUsingMap(moneyFormat);
        //
        ExcelFormatProperties dateTimeDataFormat = new ExcelFormatProperties();
        dateTimeDataFormat.setDataformat("yyyy-MM-dd HH:mm:ss");
        dateTimeDataFormat.setId("yyyy-MM-dd HH:mm:ss");
        dateTimeDataFormat.setIsHeader(2);
        ExcelContextAllContext.getInstance().setUsingMap(dateTimeDataFormat);
        //
        ExcelFormatProperties salaryDataFormat = new ExcelFormatProperties();
        salaryDataFormat.setDataformat("#,##0");
        salaryDataFormat.setId("salary-format");
        salaryDataFormat.setIsHeader(2);
        ExcelContextAllContext.getInstance().setUsingMap(salaryDataFormat);


        ExcelFormatProperties dateForLocalDateDataFormat = new ExcelFormatProperties();
        dateForLocalDateDataFormat.setDataformat("yyyy-MM-dd");
        dateForLocalDateDataFormat.setId("yyyy-MM-dd-dateForLocalDate");
        dateForLocalDateDataFormat.setIsHeader(2);
        ExcelContextAllContext.getInstance().setUsingMap(dateForLocalDateDataFormat);
        //
    }
}
