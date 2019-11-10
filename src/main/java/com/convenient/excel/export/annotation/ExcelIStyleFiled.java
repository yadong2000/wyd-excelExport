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
public @interface ExcelIStyleFiled {
    /**
     * 水平居中
     */
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.CENTER;

    /**
     * 垂直居中
     */
    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;

    /**
     * 字体名字
     */
    String fontName() default "宋体";

    /**
     * 水平大小
     */
    short fontHeightInPoints();


    /**
     * 行高度,不是这
     */
    short rowHight() default 25;

    /**
     * 单元格的宽度
     */
    int columnWidth() default 25;

    /**
     * 是否自动换行
     */
    boolean wrapText() default true;

}
