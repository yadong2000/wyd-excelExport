package com.convenient.excel.export.annotation;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注在
 */
@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelIStyleFiled {

	/**
	 * 水平居中
	 */
	HorizontalAlignment horizontalAlignment();

	/**
	 * 垂直居中
	 */
	VerticalAlignment verticalAlignment();

	/**
	 * 字体名字
	 */
	String fontName();

	/**
	 * 水平大小
	 */
	short fontHeightInPoints();

	/**
	 * 单元格合并
	 */
	// CellRangeAddress cellRangeAddress();

	/**
	 * 行高度,不是这
	 */
	short rowHight();

	/**
	 * 单元格的宽度
	 */
	int columnWidth();

	/**
	 * 是否自动换行
	 */
	boolean wrapText();

}
