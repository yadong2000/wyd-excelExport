package com.wydexcel.generate.properties;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelRowConfProperties {
    private Short height;
    private String rowName;
    private boolean zeroHeight;
    private Map<String, ExcelFieldProperties> cellsMap = new HashMap<>();

    private List<ExcelFieldProperties> cells = new ArrayList<>();

    public Map<String, ExcelFieldProperties> rowFieldMap() {
        for (ExcelFieldProperties k : cells) {
            cellsMap.put(k.getValue(), k);
            cellsMap.put(k.getExcelFieldName(), k);
        }
        return cellsMap;
    }


    private int hashCode;
    private int startRow;
    private List<Map<String, String>> dataMaps = new ArrayList<>();

    @Override
    public int hashCode() {

        for (ExcelFieldProperties cell : cells) {
            hashCode += cell.hashCode();
        }
        return hashCode;
    }

    public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public boolean isZeroHeight() {
        return zeroHeight;
    }

    public void setZeroHeight(boolean zeroHeight) {
        this.zeroHeight = zeroHeight;
    }

    public Map<String, ExcelFieldProperties> getCellsMap() {
        return cellsMap;
    }

    public void setCellsMap(Map<String, ExcelFieldProperties> cellsMap) {
        this.cellsMap = cellsMap;
    }

    public List<ExcelFieldProperties> getCells() {
        return cells;
    }

    public void setCells(List<ExcelFieldProperties> cells) {
        this.cells = cells;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcelRowConfProperties that = (ExcelRowConfProperties) o;
        return hashCode == that.hashCode;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartRow() {
        return startRow;
    }

    public List<Map<String, String>> getDataMaps() {
        return dataMaps;
    }

    public void setDataMaps(List<Map<String, String>> dataMaps) {
        this.dataMaps = dataMaps;
    }
}
