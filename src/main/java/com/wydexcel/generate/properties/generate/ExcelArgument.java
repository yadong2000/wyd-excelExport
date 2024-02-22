package com.wydexcel.generate.properties.generate;

import com.wydexcel.generate.process.Process;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.s.ExcelCellBase;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.Map;

public class ExcelArgument {

    private final ExcelContextAllContext instance;



    public ExcelArgument() {
        instance = ExcelContextAllContext.getInstance();
    }

    String getKey(String excelFieldName, boolean isBody) {
        return excelFieldName + (isBody ? "2" : "1");
    }

    private final Map<String, ExcelCellBase> excelBassHashMap = new HashMap<>();


    public ExcelCellBase getExcelPropertity(String type, String fieldName) {
        return excelBassHashMap.get(type + "_" + fieldName);
    }

    public void putExcelPropertity(String type, String fieldName, ExcelCellBase excelBase) {
        Process process = instance.getProcessMap().get(type);
        if (process == null || (type == null || "".equals(type.trim())) ) {
            return;
        }
        excelBassHashMap.put(type + "_" + fieldName, excelBase);
    }




    public void generate(Cell cell, boolean isBody, ExcelFieldProperties properties) {
        String key = getKey(properties.getExcelFieldName(), isBody);
        for (Process process : map.get(key)) {
            process.excelPropertySet(cell, getExcelPropertity(process.getType(), key), isBody);
        }
    }

}
