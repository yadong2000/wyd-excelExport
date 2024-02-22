package com.wydexcel.generate.properties;

import com.wydexcel.generate.properties.s.ExcelCellBase;
import com.wydexcel.generate.properties.s.ExcelCellBaseProperties;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelPositionProperties extends ExcelCellBase {
    // excel唯一标识
    @Deprecated
    private String key = "";
    //excel 标题
    @Deprecated
    private String name = key;


    private Integer firstRow;
    private Integer lastRow;
    private Integer firstCol;
    private Integer lastCol;
    private ExcelCellBaseProperties base;

    @Override
    public String getType() {
        return  "merge";
    }

    public CellRangeAddress getCellRangeAddress() {
        return new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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


    public ExcelPositionProperties copy() {
        ExcelPositionProperties excelPositionProperties = new ExcelPositionProperties();
        excelPositionProperties.setFieldName(super.getFieldName());
        excelPositionProperties.setFirstCol(getFirstCol());
        excelPositionProperties.setFirstRow(getFirstRow());
        excelPositionProperties.setLastCol(getLastCol());
        excelPositionProperties.setLastRow(getLastRow());
        excelPositionProperties.setId(getId());
        excelPositionProperties.setIsHeader(getIsHeader());
        excelPositionProperties.setType(getType());
        return excelPositionProperties;
    }

    public ExcelCellBaseProperties getBase() {
        return base;
    }

    public void setBase(ExcelCellBaseProperties base) {
        this.base = base;
    }
}