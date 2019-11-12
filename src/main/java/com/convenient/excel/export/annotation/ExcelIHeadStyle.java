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
public @interface ExcelIHeadStyle {
    /**
     * 水平居中
     */
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.CENTER;

    /**
     * 垂直居中
     */
    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;



    /**
     * 水平大小
     */
    short fontHeightInPoints() default -1;


    /**
     * 行高度,不是这
     */
    short rowHight() default -1;

    /**
     * 单元格的宽度
     */
    int columnWidth() default -1;

    /**
     * 是否自动换行
     */
    boolean wrapText() default true;

}
