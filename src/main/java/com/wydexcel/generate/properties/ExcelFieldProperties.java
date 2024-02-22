package com.wydexcel.generate.properties;

public class ExcelFieldProperties {

    private String excelFieldName;//这里excel对应的json串名字` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    private String value;// 这个是excel 标题名字 ` varchar(100) DEFAULT NULL,
    private int excelStartRowIndex;//` int(10) NOT NULL,
    private int excelEndRowIndex;//` int(10) NOT NULL,
    private int excelStartCellIndex;//` int(10) NOT NULL,
    private int excelEndCellIndex;//` int(10) NOT NULL,
    private transient String defaultValue;//` varchar(100) DEFAULT NULL,
    private String using;//

    public ExcelFieldProperties(String excelFieldName, String value, int excelStartRowIndex, int excelStartCellIndex, int excelEndRowIndex, int excelEndCellIndex, String using) {
        this.excelFieldName = excelFieldName;
        this.value = value;
        this.excelStartRowIndex = excelStartRowIndex;
        this.excelStartCellIndex = excelStartCellIndex;
        this.excelEndRowIndex = excelEndRowIndex;
        this.excelEndCellIndex = excelEndCellIndex;
        this.using = using;
    }
    public ExcelFieldProperties(String excelFieldName, String value, int excelStartRowIndex, int excelStartCellIndex, String using) {
        this(excelFieldName, value, excelStartRowIndex, excelStartCellIndex, excelStartRowIndex,excelStartCellIndex,using);
    }

    public ExcelFieldProperties(String excelFieldName, String value, int excelStartRowIndex, int excelStartCellIndex) {
        this(excelFieldName, value, excelStartRowIndex, excelStartCellIndex, "");
    }

    public ExcelFieldProperties() {

    }


    public String getExcelFieldName() {
        return excelFieldName;
    }

    public void setExcelFieldName(String excelFieldName) {
        this.excelFieldName = excelFieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getExcelStartRowIndex() {
        return excelStartRowIndex;
    }

    public void setExcelStartRowIndex(int excelStartRowIndex) {
        this.excelStartRowIndex = excelStartRowIndex;
    }

    public int getExcelStartCellIndex() {
        return excelStartCellIndex;
    }

    public void setExcelStartCellIndex(int excelStartCellIndex) {
        this.excelStartCellIndex = excelStartCellIndex;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode() * 31 + this.getExcelStartCellIndex();
    }

    public String getUsing() {
        return using == null ? "" : using;
    }

    public void setUsing(String using) {
        this.using = using;
    }


    public int getExcelEndRowIndex() {
        return excelEndRowIndex;
    }

    public void setExcelEndRowIndex(int excelEndRowIndex) {
        this.excelEndRowIndex = excelEndRowIndex;
    }

    public int getExcelEndCellIndex() {
        return excelEndCellIndex;
    }

    public void setExcelEndCellIndex(int excelEndCellIndex) {
        this.excelEndCellIndex = excelEndCellIndex;
    }
}
