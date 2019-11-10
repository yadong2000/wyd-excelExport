package com.convenient.excel.export.util;

import com.convenient.excel.export.annotation.ExcelISheetFiled;
import com.convenient.excel.export.annotation.ExcelImportFiled;
import com.convenient.excel.export.generation.ExcelExportGenerate;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ExcelSheetUtils {


    public static Sheet setSheet(Workbook workbook, Annotation annotation, ExcelExportGenerate generate) {
        StringMemberValue sheetName = (StringMemberValue) annotation.getMemberValue("name");
        Sheet sheet = workbook.createSheet(sheetName.getValue());
        generate.setSheet(sheet);
        return sheet;
    }

}
