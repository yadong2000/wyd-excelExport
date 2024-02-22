package com.wydexcel.generate.process;

import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.generate.ExcelArgument;
import com.wydexcel.generate.properties.s.ExcelFontProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Optional;
import java.util.function.Consumer;

public class WydExcelCellFontProcessImpl implements Process<ExcelFontProperties, Font> {


    private Workbook workbook;
    private ExcelArgument sheetConf;

    @Override
    public void setWorkBook(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public void setSheetConf(ExcelArgument sheetConf) {
        this.sheetConf = sheetConf;
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_FONT;
    }

    public Font process(ExcelFontProperties property, boolean isBody) {
        if(null==property){
            return null;
        }
        Font font = workbook.createFont();
        Optional.ofNullable(property.getFontHeight()).ifPresent(font::setFontHeight);
        Optional.of(property.isBold()).ifPresent(font::setBold);
        if (property.getRed() == null && null == property.getGreen() && null == property.getBlue()) {
            Optional.of(property.getFontColor()).ifPresent(font::setColor);
        } else {
            if (workbook instanceof XSSFWorkbook) {
                XSSFFont xssfFont = (XSSFFont) font;
                XSSFWorkbook workbook1 = (XSSFWorkbook) workbook;
                xssfFont.setColor(new XSSFColor(new java.awt.Color(property.zeroIfnull(property.getRed())
                        , property.zeroIfnull(property.getGreen()), property.zeroIfnull(property.getBlue()))
                        , workbook1.getStylesSource().getIndexedColors()));
            }
        }

        Optional.ofNullable(property.getFontName()).ifPresent(font::setFontName);
        Optional.of(property.isStrikeout()).ifPresent(font::setStrikeout);
        Optional.of(property.isItalic()).ifPresent(font::setItalic);
        if (null != property.getFontHeightPoints()) {
            font.setFontHeightInPoints(property.getFontHeightPoints());
        }
        if (null != property.getUnderLine()) {
            font.setUnderline(property.getUnderLine());
        }
        return font;
    }

    public Font excelPropertySet(Cell cell, ExcelFontProperties property, boolean isBody) {
        if (null == property) {
            return null;
        }
        return process(property, isBody);
    }

    @Override
    public void excelPropertySet(Cell cell, String fieldName, boolean isBody) {
        if (null == cell.getCellStyle()) {
            return;
        }
        ExcelFontProperties property = (ExcelFontProperties) this.sheetConf.getExcelPropertity(getType(), getKey(fieldName, isBody));
        if (null == property) {
            return;
        }
        excelPropertySet(cell, property, isBody);
    }

    @Override
    public void excelPropertySet(Consumer<Font> consumer, Font font) {
        consumer.accept(font);
    }
}
