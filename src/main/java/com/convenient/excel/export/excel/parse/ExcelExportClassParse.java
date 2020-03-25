package com.convenient.excel.export.excel.parse;

import javassist.NotFoundException;

public interface ExcelExportClassParse {

	<T> T getAnnotation(Class clazz, String fieldName, String annotationName, String key);

	<T> T getClassAnnotation(Class clazz, String annotationName, String key) throws NotFoundException;

	<T> T getListFieldAnnotation(Class clazz, String annotationName) throws NotFoundException;

}
