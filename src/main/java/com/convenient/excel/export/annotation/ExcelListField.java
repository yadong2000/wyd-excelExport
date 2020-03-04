package com.convenient.excel.export.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;


@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ListField.class)
public @interface ExcelListField {


    int cellDistance();

    int startRow();

    int endRow();

//    String title();
}
