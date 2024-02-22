package com.wydexcel.generate.process;

import com.wydexcel.generate.properties.ExcelFieldProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class ExcelCommonRowProcess {

    protected WydExcelDynamicRowValueProcessImpl.DynamicExcelRowProcess innerDynamicExcelRowProcess = null;

    protected final Sheet sheet;


    public ExcelCommonRowProcess(Sheet sheet, WydExcelDynamicRowValueProcessImpl.DynamicExcelRowProcess innerDynamicExcelRowProcess) {
        this.sheet = sheet;
        this.innerDynamicExcelRowProcess = innerDynamicExcelRowProcess;
    }


    private Map<Integer, Integer> rowMap = new HashMap<>();

    public String getKey(String excelFieldName, boolean isBody) {
        return excelFieldName + (isBody ? "2" : "1");
    }

    public Row getRow(int index) {
        Integer row = rowMap.get(index);
        if (null == row) {
            Row createRow = sheet.createRow(index);
            rowMap.put(index, index);
            return createRow;
        }
        return sheet.getRow(index);
    }


    public Cell getCell(Row row, int cellIndex) {
        return row.createCell(cellIndex);
    }

    public Cell getCell(Row row, ExcelFieldProperties properties) {
        return row.createCell(properties.getExcelStartCellIndex());
    }


    public WydExcelDynamicRowValueProcessImpl.DynamicExcelRowProcess getInnerDynamicExcelRowProcess() {
        return innerDynamicExcelRowProcess;
    }





}
