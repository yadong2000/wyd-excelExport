package com.convenient.excel.beans.param;

import com.convenient.excel.beans.entity.ExcelField;
import lombok.Data;

@Data
public class ExcelFieldParameter extends ExcelField {

    private long excelCellstyleId;
    private Boolean shrinkToFit;
    private Short fillForeGroundColor;
    private Short fillPattern;
    private Short bottomBorderColor;
    private Short topBorderColor;
    private Short rightBorderColor;
    private Short leftBorderColor;
    private Short borderTop;
    private Short borderRight;
    private Short borderLeft;
    private Short indention;
    private Short rotation;
    private String verticalAlignment;
    private Boolean wrapTest;
    private String horizontalAlignment;
    private Boolean quotePrefixed;
    private Boolean locked;
    private Boolean hidden;
    private Short dataFormat;
    private Long fontId;

}
