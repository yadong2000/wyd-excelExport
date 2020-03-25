package com.convenient.excel.export.excel.parse;

import com.convenient.excel.export.excel.ExcelExportProperty;
import javassist.NotFoundException;

import java.util.List;
import java.util.Map;

public interface ExcelExportPropertyParse {

	Map<String, ExcelExportProperty> parseHead(Class clazz) throws NotFoundException;

	Map<String, ExcelExportProperty> parseListField(List list)
			throws NotFoundException, NoSuchFieldException, IllegalAccessException;

	ExcelExportProperty parseBody(Object object);

}
