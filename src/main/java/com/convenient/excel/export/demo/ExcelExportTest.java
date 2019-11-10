package com.convenient.excel.export.demo;

import com.convenient.excel.export.annotation.*;


@ExcelISheetFiled(name = "sheet2")
public class ExcelExportTest {

    @ExcelIStyleFiled(verticalAlignment = VerticalAlignment.CENTER,
            horizontalAlignment = HorizontalAlignment.CENTER
            , fontName = "宋体",
            columnWidth = 20,
            fontHeightInPoints = 100,
            rowHight = 200,
            wrapText = true)
    @ExcelImportFiled(title = "头部", startRow = 0, endRow = 2, startCell = 0, endCell = 3)
    private String head;
    @ExcelIStyleFiled(verticalAlignment = VerticalAlignment.CENTER,
            horizontalAlignment = HorizontalAlignment.CENTER
            , fontName = "宋体",
            columnWidth = 20,
            fontHeightInPoints = 100,
            rowHight = 200,
            wrapText = true)
    @ExcelImportFiled(title = "眼睛", startRow = 0, endRow = 1, startCell = 4, endCell = 6)
    private String eye;
    @ExcelIStyleFiled(verticalAlignment = VerticalAlignment.CENTER,
            horizontalAlignment = HorizontalAlignment.CENTER
            , fontName = "宋体",
            columnWidth = 20,
            fontHeightInPoints = 100,
            rowHight = 200,
            wrapText = true)
    @ExcelImportFiled(title = "鼻子", startRow = 0, endRow = 1, startCell = 7, endCell = 8)
    private String norse;
    @ExcelIStyleFiled(verticalAlignment = VerticalAlignment.CENTER,
            horizontalAlignment = HorizontalAlignment.CENTER
            , fontName = "宋体",
            columnWidth = 20,
            fontHeightInPoints = 100,
            rowHight = 200,
            wrapText = true)
    @ExcelImportFiled(title = "嘴", startRow = 0, endRow = 1, startCell = 9, endCell = 11)
    private String month;


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getNorse() {
        return norse;
    }

    public void setNorse(String norse) {
        this.norse = norse;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
