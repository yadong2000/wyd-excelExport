package com.wydexcel.generate.properties.s;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class ExcelFreezePaneProperties {


    private Integer cellNum;//要冻结的列数
    private Integer rowNum;//要冻结的行数
    private Integer firstCellNum;//要冻结的列数 右边的第一例行号
    private Integer firstRowNum;//要冻结的行数 下边的第一行行号
    private Integer activePane;//分离式冻结


    public Integer getActivePane() {
        return activePane;
    }

    public void setActivePane(Integer activePane) {
        this.activePane = activePane;
    }

    public Integer getCellNum() {
        return cellNum;
    }

    public void setCellNum(Integer cellNum) {
        this.cellNum = cellNum;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getFirstCellNum() {
        return firstCellNum;
    }

    public void setFirstCellNum(Integer firstCellNum) {
        this.firstCellNum = firstCellNum;
    }

    public Integer getFirstRowNum() {
        return firstRowNum;
    }

    public void setFirstRowNum(Integer firstRowNum) {
        this.firstRowNum = firstRowNum;
    }
}
