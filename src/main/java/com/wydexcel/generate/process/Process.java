package com.wydexcel.generate.process;


import com.wydexcel.generate.properties.generate.ExcelArgument;
import com.wydexcel.generate.properties.s.ExcelCellBase;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.function.Consumer;

public interface Process<T extends ExcelCellBase, V> {

    default void setDataFormat(DataFormat dataFormat) {
    }

    default void excelPropertySet(Cell cell, String fileName, boolean isBody) {

    }

    default void excelPropertySet(Consumer<V> consumer, V v) {
        consumer.accept(v);
    }

    int order();

    V excelPropertySet(Cell cell, T t, boolean isBody);


    default String getKey(String excelFieldName, boolean isBody) {
        return excelFieldName + (isBody ? "2" : "1");
    }

    default String getType() {
        return "";
    }

    default void setWorkBook(Workbook workbook) {
    }

    default void setSheet(Sheet sheet) {
    }

    default void setSheetConf(ExcelArgument sheetConf) {
    }


    default boolean validate(ExcelCellBase property, boolean isBody) {
        if (null == property) {
            return true;
        }
        if (isBody && property.getIsHeader() == 1) {
            return true;
        }
        return !isBody && property.getIsHeader() == 2;
    }
}
