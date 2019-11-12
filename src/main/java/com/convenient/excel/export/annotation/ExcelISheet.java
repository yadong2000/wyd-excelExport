package com.convenient.excel.export.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当导出多sheet的时候，有几种情况，一种是每个sheet的格式都不一致，所以我们需要在不同类上标注这些属于不同的sheet,但是如果每个sheet也的数据是一致的
 */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelISheet {
    //
    String  name() default "sheet1";


    String namespace() default "";


}
