package com.convenient.excel.export.util;

import com.convenient.excel.export.generation.ExcelExportGenerate;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelSheetUtils {


    public static Sheet setSheet(Workbook workbook, Annotation annotation, ExcelExportGenerate generate) {
        StringMemberValue sheetName = (StringMemberValue) annotation.getMemberValue("name");
        Sheet sheet = workbook.createSheet(sheetName.getValue());
        generate.setSheet(sheet);
        return sheet;
    }

}
