package com.convenient.excel.export.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注在
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelImportFiled {

    // 标题
    String title();

    // 起始行
    int startRow();

    // 结束行
    int endRow();

    // 开始单元格
    int startCell();

    // 结束单元格
    int endCell();

    boolean exclue() default false;

    boolean hasValue() default true;

    String isKey() default "";

    boolean hidden() default false;
}
