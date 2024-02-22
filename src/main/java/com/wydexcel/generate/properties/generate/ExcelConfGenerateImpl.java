package com.wydexcel.generate.properties.generate;


import com.wydexcel.generate.process.custom.CustomProcess;
import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class ExcelConfGenerateImpl {


    private final ExcelWorkPlaceProperties placeProperties;
    private final Map<String, ExcelSheetGenerate> generateImplMap = new HashMap<>();
    private final Workbook workbook;
    private final List<CustomProcess> customProcessList;

    public ExcelConfGenerateImpl(ExcelWorkPlaceProperties placeProperties, Workbook workbook) {
        this.placeProperties = placeProperties;
        this.workbook = workbook;
        this.customProcessList = Collections.emptyList();
        build();
    }

    public ExcelConfGenerateImpl(ExcelWorkPlaceProperties placeProperties, Workbook workbook, List<CustomProcess> customProcessList) {
        this.placeProperties = placeProperties;
        this.workbook = workbook;
        this.customProcessList = customProcessList == null ? Collections.emptyList() : customProcessList;
        build();
    }

    public Map<String, ExcelSheetGenerate> getGenerateImplMap() {
        return generateImplMap;
    }

    public ExcelWorkPlaceProperties getPlaceProperties() {
        return placeProperties;
    }

    private List<ExcelAbstractSheetProperties> sheetProperties = new ArrayList<>();

    public ExcelSheetGenerate getDefaultExcelSheetGenerate() {
        return generateImplMap.get(sheetProperties.get(0).getSheetName());
    }

    public boolean build() {
        for (Map.Entry<String, ExcelAbstractSheetProperties> entry : placeProperties.getMap().entrySet()) {
            String k = entry.getKey();
            ExcelAbstractSheetProperties value = entry.getValue();
            value.build();
            value.setWorkbookId(placeProperties.getId());
            sheetProperties.add(value);
            value.setSheetName(k);
        }
        sheetProperties.sort(Comparator.comparingInt(ExcelAbstractSheetProperties::getSheetNum));
        int sheetNum = 0;
        for (ExcelAbstractSheetProperties sheetProperty : sheetProperties) {
            for (String sheetName : sheetProperty.getSheetName().split(",")) {
                if (null != generateImplMap.get(sheetName)) {
                    continue;
                }
                ExcelSheetGenerate excelGenerate = new ExcelSheetGenerate(sheetProperty, workbook, sheetNum);
                generateImplMap.put(sheetName, excelGenerate);
                sheetProperty.setSheetNum(sheetNum);
                List<ExcelFieldProperties> cells = sheetProperty.getCells();
                for (ExcelFieldProperties cell : cells) {
                    ExcelArgument argument = excelGenerate.getArgument();
                }
                sheetNum++;
            }
        }
        for (CustomProcess customProcess : this.customProcessList) {
            ExcelSheetGenerate excelSheetGenerate = generateImplMap.get(customProcess.sheetName());
            if (null != excelSheetGenerate) {
                customProcess.workBook(workbook);
                customProcess.spreadSheet(excelSheetGenerate.getSheet(customProcess.sheetName()));
            }
        }
        return true;
    }

    public ExcelSheetGenerate generate(String sheetName, Map<String, Object> map) {
        ExcelSheetGenerate excelGenerate = generateImplMap.get(sheetName);
        if (null == excelGenerate) {
            throw new IllegalArgumentException("");
        }
        excelGenerate.generate(map);

        return excelGenerate;
    }

    public ExcelSheetGenerate generate(String sheetName) {
        ExcelSheetGenerate excelGenerate = generateImplMap.get(sheetName);
        if (null == excelGenerate) {
            throw new IllegalArgumentException("");
        }
        return excelGenerate;
    }

    public void write(OutputStream outputStream) throws IOException {
        getGenerateImplMap().forEach((k, v) -> {
            v.processSheet();
        });
        this.workbook.write(outputStream);
    }

    public boolean write(String path) {
        try {
            write(new FileOutputStream(path));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

