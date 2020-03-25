package com.convenient.excel.export.excel.parse;

import com.convenient.excel.export.excel.ExcelExportProperty;

import java.util.Map;

public interface ExcelRuleCenter {

	Map<String, ExcelExportProperty> computeRule(Map<String, ExcelExportProperty> map,
												 Map<String, ExcelExportProperty> listFieldmap, ExcelExportProperty property);

}
