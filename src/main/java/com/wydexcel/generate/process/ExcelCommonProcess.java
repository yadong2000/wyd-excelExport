package com.wydexcel.generate.process;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

public class ExcelCommonProcess {

    protected WydExcelSheetProcess sheetProcess = new DefaultWydExcelXSSFSheetProcess();
    protected final WydExcelDynamicRowValueProcessImpl dynamicRowProcess = new WydExcelDynamicRowValueProcessImpl();
    protected WydExcelDynamicRowValueProcessImpl.DynamicExcelRowProcess innerDynamicExcelRowProcess = null;
    protected final Workbook workbook;




    public ExcelCommonProcess(Workbook workbook) {
        this.workbook = workbook;
    }


    private Map<String, Sheet> map = new HashMap<>();

    public Sheet getSheet(String sheetName) {
        Sheet sheet = map.get(sheetName);
        if (null == sheet) {
            sheet = workbook.createSheet(sheetName);
            map.put(sheetName, sheet);
        }

        return sheet;
    }









}
