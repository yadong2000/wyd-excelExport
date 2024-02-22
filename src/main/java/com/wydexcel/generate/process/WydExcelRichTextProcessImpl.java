package com.wydexcel.generate.process;


import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.generate.ExcelArgument;
import com.wydexcel.generate.properties.s.ExcelFontProperties;
import com.wydexcel.generate.properties.s.ExcelRichTextCollectionProperties;
import com.wydexcel.generate.properties.s.ExcelRichTextProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class WydExcelRichTextProcessImpl implements Process<ExcelRichTextCollectionProperties, RichTextString> {
    ExcelArgument sheetProperties;

    @Override
    public void setSheetConf(ExcelArgument sheetConf) {
        sheetProperties = sheetConf;
    }

    private WydExcelCellFontProcessImpl fontProcess = new WydExcelCellFontProcessImpl();

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_RICHTEXT;
    }

    public void excelPropertySet(Cell cell, String key, boolean isBody) {
        ExcelRichTextCollectionProperties properties = (ExcelRichTextCollectionProperties) sheetProperties.getExcelPropertity("richText", getKey(key, isBody));
        if (null != properties) {
            cell.setCellValue(process(properties, isBody));
        }
    }

    @Override
    public int order() {
        return 3;
    }

    public RichTextString excelPropertySet(Cell cell, ExcelRichTextCollectionProperties properties, boolean isBody) {
        if (null == properties) {
            return null;
        }
        XSSFRichTextString rt = process(properties, isBody);
        cell.setCellValue(rt);
        return rt;
    }

    public XSSFRichTextString process(ExcelRichTextCollectionProperties properties, boolean isBody) {
        if (validate(properties, isBody)) {
            return null;
        }
        XSSFRichTextString rt = new XSSFRichTextString(properties.getText());
        for (ExcelRichTextProperties richTextProperties : properties.getRichTextProperties()) {
            ExcelFontProperties fontProperties = richTextProperties.getFont();
            if (null != fontProperties) {
                fontProperties.setFieldName(properties.getFieldName() + "_rich_" + fontProperties.hashCode() + isBody);
                Font font = fontProcess.process(fontProperties, isBody);
                if (null != font) {
                    if (null != richTextProperties.getStartIndex() && null != richTextProperties.getEndIndex()) {
                        rt.applyFont(richTextProperties.getStartIndex(), richTextProperties.getEndIndex(), font);
                    } else {
                        rt.applyFont(font);
                    }
                }
            }

        }
        return rt;
    }

}