package com.wydexcel.generate.process;

import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import org.apache.poi.ss.usermodel.Sheet;

public interface WydExcelSheetProcess extends Process {
    ExcelAbstractSheetProperties importProcess(Sheet sheetAt, ExcelAbstractSheetProperties excelAbstractSheetProperties);

    void afterExportProcess(Sheet sheetAt, ExcelAbstractSheetProperties excelAbstractSheetProperties);

    void afterExportProcessSheet(Sheet sheet, ExcelAbstractSheetProperties conf);
}