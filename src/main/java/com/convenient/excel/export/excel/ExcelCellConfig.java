package com.convenient.excel.export.excel;


import lombok.Data;

@Data
public class ExcelCellConfig extends CommonField {

    private long excelCellId;
    private String cellFormula;
    private String hyperlink;
    private String cellComment;


}
