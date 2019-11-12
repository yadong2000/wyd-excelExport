package com.convenient.excel.export.util;


import com.convenient.excel.export.annotation.ExcelIDataFormatFiled;
import com.convenient.excel.export.generation.ExcelExportGenerate;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelSetDataFormatUtils {

    public static void setDataFormat(CellStyle cellStyle, ExcelExportGenerate generate, ExcelHeadUtil.ExcelPosition excelPosition, Cell cell, AnnotationsAttribute attribute) {
        Annotation dataformatFiled = attribute.getAnnotation(ExcelIDataFormatFiled.class.getName());
        Short format = null;
        if (dataformatFiled != null) {
            StringMemberValue dataFormatValue = (StringMemberValue) dataformatFiled.getMemberValue("dataFormat");
            if (dataFormatValue != null) {
                format = generate.getDataFormat().getFormat(dataFormatValue.getValue());
                if (format != null) {
                    if (StringUtils.isNotBlank(excelPosition.getTitle())) {
                        cell.setCellValue(new Double(excelPosition.getTitle()));
                        cellStyle.setDataFormat(format);
                    }
                }
            }

        }
    }


}
