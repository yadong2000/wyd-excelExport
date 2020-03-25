package com.convenient.excel.export.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelListField {

	int cellDistance();

	int startRow();

	int endRow();

	String includeField() default "";

	String afterField() default "";

	String beforeField() default "";

	String totalTitle() default "";

	int titleStartRow() default 0;

	int titleEndRow() default 0;

	// String title();

}
