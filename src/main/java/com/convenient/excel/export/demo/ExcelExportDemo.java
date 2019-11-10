package com.convenient.excel.export.demo;

import com.convenient.excel.export.annotation.*;


@ExcelISheetFiled(name = "sheet1")
public class ExcelExportDemo {

    @ExcelIStyleFiled(verticalAlignment = VerticalAlignment.CENTER,
            horizontalAlignment = HorizontalAlignment.CENTER
            , fontName = "宋体",
            columnWidth = 20,
            fontHeightInPoints = 100,
            rowHight = 200,
            wrapText = true)
    @ExcelImportFiled(title = "名字", startRow = 0, endRow = 1, startCell = 0, endCell = 10)
    private String name;
    @ExcelIStyleFiled(verticalAlignment = VerticalAlignment.CENTER,
            horizontalAlignment = HorizontalAlignment.CENTER
            , fontName = "宋体",
            columnWidth = 20,
            fontHeightInPoints = 100,
            rowHight = 200,
            wrapText = true)
    @ExcelImportFiled(title = "类型", startRow = 0, endRow = 1, startCell = 11, endCell = 15)
    private String type;
    @ExcelIStyleFiled(verticalAlignment = VerticalAlignment.CENTER,
            horizontalAlignment = HorizontalAlignment.CENTER
            , fontName = "宋体",
            columnWidth = 20,
            fontHeightInPoints = 100,
            rowHight = 200,
            wrapText = true)
    @ExcelImportFiled(title = "年龄", startRow = 0, endRow = 1, startCell = 16, endCell = 17)
    private String age;
    @ExcelIStyleFiled(verticalAlignment = VerticalAlignment.CENTER,
            horizontalAlignment = HorizontalAlignment.CENTER
            , fontName = "宋体",
            columnWidth = 20,
            fontHeightInPoints = 100,
            rowHight = 200,
            wrapText = true)
    @ExcelImportFiled(title = "地址", startRow = 0, endRow = 1, startCell = 18, endCell = 20)
    private String adrress;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAdrress() {
        return adrress;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }
}
