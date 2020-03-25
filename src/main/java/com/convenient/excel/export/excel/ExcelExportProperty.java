package com.convenient.excel.export.excel;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class ExcelExportProperty {

    private Integer type;// 1=head 2=body

    private String dataFormat;

    // 标题
    private String title;

    // 起始行
    private Integer startRow;

    // 结束行
    private Integer endRow;

    // 开始单元格
    private Integer startCell;

    // 结束单元格
    private Integer endCell;

    // sheetname
    private String sheetName;

    /**
     * 行高度,
     */
    private Short rowHight;

    /**
     * 单元格的宽度
     */
    private Integer columnWidth;

    /**
     * 是否自动换行
     */
    private Boolean wrapText;

    // 是否动态生成
    private Boolean hasListField = false;

    // 如果是动态生成则行间距是
    private Integer cellDistance;

    /**
     * 水平居中
     */
    private HorizontalAlignment horizontalAlignment;

    /**
     * 垂直居中
     */
    private VerticalAlignment verticalAlignment;

    // 是否合并单元格
    private Boolean exclue;

    private String includeField;

    private String afterField;

    private String beforeField;// () default "";

    private String totalTitle;// () default "";

    private Integer titleStartRow;// () default 0;

    private Integer titleEndRow;// () default 0;

    private Boolean hasValue = true;// () default 0;

    private Integer index;

    private Object value;

    private String fieldName;

    private String isKey;
    private Boolean hidden;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getEndRow() {
        return endRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    public Integer getStartCell() {
        return startCell;
    }

    public void setStartCell(Integer startCell) {
        this.startCell = startCell;
    }

    public Integer getEndCell() {
        return endCell;
    }

    public void setEndCell(Integer endCell) {
        this.endCell = endCell;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Short getRowHight() {
        return rowHight;
    }

    public void setRowHight(Short rowHight) {
        this.rowHight = rowHight;
    }

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public Boolean getWrapText() {
        return wrapText;
    }

    public void setWrapText(Boolean wrapText) {
        this.wrapText = wrapText;
    }

    public Boolean getHasListField() {
        return hasListField;
    }

    public void setHasListField(Boolean hasListField) {
        this.hasListField = hasListField;
    }

    public Integer getCellDistance() {
        return cellDistance;
    }

    public void setCellDistance(Integer cellDistance) {
        this.cellDistance = cellDistance;
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public Boolean getExclue() {
        return exclue;
    }

    public void setExclue(Boolean exclue) {
        this.exclue = exclue;
    }

    public String getIncludeField() {
        return includeField;
    }

    public void setIncludeField(String includeField) {
        this.includeField = includeField;
    }

    public String getAfterField() {
        return afterField;
    }

    public void setAfterField(String afterField) {
        this.afterField = afterField;
    }

    public String getBeforeField() {
        return beforeField;
    }

    public void setBeforeField(String beforeField) {
        this.beforeField = beforeField;
    }

    public String getTotalTitle() {
        return totalTitle;
    }

    public void setTotalTitle(String totalTitle) {
        this.totalTitle = totalTitle;
    }

    public Integer getTitleStartRow() {
        return titleStartRow;
    }

    public void setTitleStartRow(Integer titleStartRow) {
        this.titleStartRow = titleStartRow;
    }

    public Integer getTitleEndRow() {
        return titleEndRow;
    }

    public void setTitleEndRow(Integer titleEndRow) {
        this.titleEndRow = titleEndRow;
    }

    public Boolean getHasValue() {
        return hasValue;
    }

    public void setHasValue(Boolean hasValue) {
        this.hasValue = hasValue;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getIsKey() {
        return isKey;
    }

    public void setIsKey(String isKey) {
        this.isKey = isKey;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
