package com.wydexcel.generate.properties.s;

import com.wydexcel.generate.properties.s.validate.ExcelDataValidationBaseProperties;

public class ExcelDataValidationProperties {

    private Integer firstRow;
    private Integer lastRow;
    private Integer firstCol;
    private Integer lastCol;
    private ExcelDataValidationBaseProperties properties;

    public Integer getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(Integer firstRow) {
        this.firstRow = firstRow;
    }

    public Integer getLastRow() {
        return lastRow;
    }

    public void setLastRow(Integer lastRow) {
        this.lastRow = lastRow;
    }

    public Integer getFirstCol() {
        return firstCol;
    }

    public void setFirstCol(Integer firstCol) {
        this.firstCol = firstCol;
    }

    public Integer getLastCol() {
        return lastCol;
    }

    public void setLastCol(Integer lastCol) {
        this.lastCol = lastCol;
    }

    public ExcelDataValidationBaseProperties getProperties() {
        return properties;
    }

    public void setProperties(ExcelDataValidationBaseProperties properties) {
        this.properties = properties;
    }
}
