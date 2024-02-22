package com.wydexcel.generate.properties.generate;


import com.wydexcel.generate.process.*;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.process.custom.CustomProcess;
import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.ExcelPositionProperties;
import com.wydexcel.generate.properties.s.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExcelRowGenerateRow extends ExcelCommonRowProcess {


    private ExcelArgument argument;

    private Row row;
    private ExcelAbstractSheetProperties conf;
    private Map<String, ExcelFieldProperties> fieldMap;
    private WydExcelDynamicRowValueProcessImpl.DynamicExcelRowProcess dynamicExcelRowProcess;
    private WydExcelCellBaseProcessImpl cellBaseProcess;
    private WydExcelCellFontProcessImpl fontProcess;
    private WydExcelCellFormatProcessImpl formatProcess;
    private final Map<String, Short> formatMap = new HashMap<>();
    private final Map<String, Font> fontMap = new HashMap<>();
    private final Map<String, CellStyle> cellStyleMap = new HashMap<>();

    public ExcelRowGenerateRow(ExcelAbstractSheetProperties conf, Sheet sheet
            , WydExcelDynamicRowValueProcessImpl.DynamicExcelRowProcess dynamicExcelRowProcess, ExcelArgument argument, WydExcelCellBaseProcessImpl cellBaseProcess
            , WydExcelCellFontProcessImpl fontProcess, WydExcelCellFormatProcessImpl formatProcess) {
        super(sheet, dynamicExcelRowProcess);
        this.fieldMap = dynamicExcelRowProcess.getFieldList();
        this.conf = conf;
        row = currentRow();
        this.argument = argument;
        this.cellBaseProcess = cellBaseProcess;
        this.fontProcess = fontProcess;
        this.formatProcess = formatProcess;
    }


    public void completeCurrentRow() {
        innerDynamicExcelRowProcess.incrementIndex();
    }

    public void mergeGenerate(int jumpSize, String key, Object value, Integer firstCol, Integer lastCol) {
        if (null == key || key.isEmpty()) {
            return;
        }
        Map<String, ExcelFieldProperties> fieldList = innerDynamicExcelRowProcess.getFieldList();
        int mergeStartIndex = innerDynamicExcelRowProcess.getMergeStartIndexWith();
        int mergeEndIndex = innerDynamicExcelRowProcess.getMereEndIndex(jumpSize);// 合并的范围

        ExcelFieldProperties properties = fieldList.get(key);
        if (null == properties) {
            return;
        }
        ExcelPositionProperties positionProperties = (ExcelPositionProperties) this.argument.getExcelPropertity("merge", getKey(key, true));
        if (null != positionProperties) {
            positionProperties.setFirstRow(mergeStartIndex);
            positionProperties.setLastRow(mergeEndIndex);
            positionProperties.setFirstCol(firstCol);
            if (null == firstCol) {
                positionProperties.setFirstCol(properties.getExcelStartCellIndex());
            }
            positionProperties.setLastCol(lastCol);
            if (null == lastCol) {
                positionProperties.setLastCol(properties.getExcelStartCellIndex());
            }
        }
        mergeGenerate(key, value, positionProperties);
    }


    public Row currentRow() {
        return row = getRow(innerDynamicExcelRowProcess.getEndIndex());
    }


    public void setDynamicExcelRowProcess(WydExcelDynamicRowValueProcessImpl.DynamicExcelRowProcess innerDynamicExcelRowProcess) {
        this.dynamicExcelRowProcess = innerDynamicExcelRowProcess;
        this.fieldMap = this.dynamicExcelRowProcess.getFieldList();
    }

    public ExcelRowGenerateRow generateCell(String fieldName, Double value, CustomProcess customProcess) {
        ExcelFieldProperties fieldProperties = getFieldProperties(fieldName);
        Cell cell = getCell(currentRow(), fieldProperties.getExcelStartCellIndex());
        cell.setCellValue(value);
        customProcess.otherInfo(cell);
        CellStyle cellStyle = putFieldCellStyle(fieldName, true);
        cell.setCellStyle(cellStyle);
        customProcess.processLogic();
        return this;
    }
    public ExcelRowGenerateRow generateCell(String fieldName, Double value) {
        ExcelFieldProperties fieldProperties = getFieldProperties(fieldName);
        Cell cell = getCell(currentRow(), fieldProperties.getExcelStartCellIndex());
        cell.setCellValue(value);
        CellStyle cellStyle = putFieldCellStyle(fieldName, true);
        cell.setCellStyle(cellStyle);
        return this;
    }

    public ExcelRowGenerateRow mergeGenerate(String key, Object value, ExcelPositionProperties positionProperties) {
        WydExcelCellMergeProcessImpl cellMergeProcess = (WydExcelCellMergeProcessImpl) ExcelContextAllContext.getInstance()
                .getProcessMap().get(ExcelContextAllContext.getInstance().PROCESSTYPE_MERGE);
        cellMergeProcess.excelPropertySet(null, positionProperties, true);
        if (null == value || value.equals("")) {
            return this;
        }
        generateCell(key, value);
        return this;
    }

    public ExcelFieldProperties getFieldProperties(String propertyKey) {
        ExcelFieldProperties properties = fieldMap.get(propertyKey);
        if (null == properties) {
            throw new IllegalArgumentException("propertyKey " + propertyKey + " \t has not found");
        }
        return properties;
    }

    private CellStyle putFieldCellStyle(String key, boolean isBody) {
        String propertyKey = getKey(key, isBody);
        CellStyle cellStyle = cellStyleMap.computeIfAbsent(propertyKey, k -> cellBaseProcess.process(key, (ExcelCellBaseProperties) argument.getExcelPropertity(cellBaseProcess.getType(), propertyKey), isBody));

        ExcelCellBase excelFormatPropertity = argument.getExcelPropertity(formatProcess.getType(), propertyKey);
        if (null != excelFormatPropertity) {
            Short process = formatMap.computeIfAbsent(propertyKey, k -> formatProcess.process((ExcelFormatProperties) excelFormatPropertity, isBody));
            cellStyle.setDataFormat(process);
        }
        ExcelCellBase excelPropertity = argument.getExcelPropertity(fontProcess.getType(), propertyKey);
        if (null != excelPropertity) {
            Font font = fontMap.computeIfAbsent(propertyKey, k -> fontProcess.process((ExcelFontProperties) excelPropertity, isBody));
            cellStyle.setFont(font);
        }
        return cellStyle;
    }


    public ExcelRowGenerateRow generateCell(String fieldName, Object value) {

        Cell cell = getCell(currentRow(), getFieldProperties(fieldName));

        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) value);
        } else if (value instanceof LocalDate) {
            cell.setCellValue((LocalDate) value);
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(putFieldCellStyle(fieldName, true));
        return this;
    }


    public ExcelRowGenerateRow generateRich(String fieldName, String value) {
        boolean isBody = true;
        ExcelFieldProperties properties = fieldMap.get(fieldName);
        Cell cell = getCell(row, properties.getExcelStartCellIndex());
        ExcelRichTextCollectionProperties property = this.conf.getExcelRichTextCollectionProperties(fieldName, isBody, value);
        WydExcelRichTextProcessImpl excelRichTextProcess = (WydExcelRichTextProcessImpl) ExcelContextAllContext.getInstance()
                .getProcessMap().get(ExcelContextAllContext.getInstance().PROCESSTYPE_RICHTEXT);
        XSSFRichTextString process = excelRichTextProcess.process(property, isBody);
        cell.setCellValue(process);
        return this;
    }

    public ExcelRowGenerateRow generateCell(String key, String value, boolean isBody) {
        Cell cell = getCell(currentRow(), getFieldProperties(key));
        cell.setCellValue(value);
        CellStyle cellStyle = putFieldCellStyle(key, isBody);
        cell.setCellStyle(cellStyle);
        return this;
    }


    public ExcelRowGenerateRow generateCell(String fieldName, String value) {
        ExcelFieldProperties fieldProperties = getFieldProperties(fieldName);
        Cell cell = getCell(currentRow(), fieldProperties);
        cell.setCellValue(value);
        cell.setCellStyle(putFieldCellStyle(fieldName, true));
        return this;
    }


    public ExcelRowGenerateRow generateCell(String fieldName, LocalDate value) {
        ExcelFieldProperties fieldProperties = getFieldProperties(fieldName);
        Cell cell = getCell(currentRow(), fieldProperties);
        cell.setCellValue(value);
        cell.setCellStyle(putFieldCellStyle(fieldName, true));
        return this;
    }


    public ExcelRowGenerateRow generateCell(String fieldName, LocalDateTime value) {
        ExcelFieldProperties fieldProperties = getFieldProperties(fieldName);
        Cell cell = getCell(currentRow(), fieldProperties);
        cell.setCellValue(value);
        cell.setCellStyle(putFieldCellStyle(fieldName, true));
        return this;
    }


    public ExcelRowGenerateRow generateCell(String fieldName, Date value) {
        ExcelFieldProperties fieldProperties = getFieldProperties(fieldName);
        Cell cell = getCell(currentRow(), fieldProperties.getExcelStartCellIndex());
        cell.setCellValue(value);
        cell.setCellStyle(putFieldCellStyle(fieldName, true));
        return this;
    }

    public ExcelRowGenerateRow generateCell(String fieldName, Calendar value) {
        ExcelFieldProperties fieldProperties = getFieldProperties(fieldName);
        Cell cell = getCell(currentRow(), fieldProperties.getExcelStartCellIndex());
        cell.setCellValue(value);
        cell.setCellStyle(putFieldCellStyle(fieldName, true));
        return this;
    }


}

