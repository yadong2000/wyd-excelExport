package com.convenient.excel.export.annotation;

import java.lang.annotation.*;

/**
 *
 *  */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListField {
    //
    ExcelListField[] value() ;
}
