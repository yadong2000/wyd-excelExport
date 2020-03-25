package com.convenient.excel.export.excel.relate;

import com.convenient.excel.export.excel.ExcelExportProperty;
import com.convenient.excel.export.generation.ExcelExportGenerate;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public interface ExcelExportRelated {
    ExcelExportProperty relate(List list1, ExcelExportGenerate generate, Row row) throws IllegalAccessException;
}
