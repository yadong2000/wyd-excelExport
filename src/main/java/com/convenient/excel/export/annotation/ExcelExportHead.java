package com.convenient.excel.export.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表头设置，通常来讲表头和列表部分的单元格是一致的，但是表头可能会有一些复杂的单元格合并，比如说第一行和第二行合并
 * 但是对于列表来说大部分都是列与列的合并，对于行和行的合并比较少，所以ExcelExportHead注解适用于这种情况即：
 * 表头和列表的数据除了行与行的合并之外，单元格的位置都是一致的
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelExportHead {
    //标题
    String title();

    //起始行
    int startRow();

    //结束行
    int endRow();

    // 开始单元格
    int startCell();

    //结束单元格
    int endCell();

    //是否排序单元格合并
    boolean exclue() default false;


}
