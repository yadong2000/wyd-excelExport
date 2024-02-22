package com.wydexcel.generate.process.custom;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface CustomProcess<T> {

    String cellName();

    String sheetName();

    void otherInfo(Cell cell);

    CustomProcess processData(T t);

    void processLogic();

    void workBook(Workbook workbook);

    void spreadSheet(Sheet sheet);


}
