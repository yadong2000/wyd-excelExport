package com.convenient.excel.export.generation;

import com.convenient.excel.export.excel.ExcelExportProperty;
import com.convenient.excel.export.excel.relate.ExcelExportRelated;
import com.convenient.excel.export.excel.parse.DefaultExcelExportClassParse;
import com.convenient.excel.export.excel.parse.DefaultExcelExportParse;
import com.convenient.excel.export.excel.parse.ExcelExportPropertyParse;
import javassist.NotFoundException;
import javassist.bytecode.FieldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelExportGenerate {


    private Workbook workBook;


    private Sheet sheet;
    private int rowHeadNum;

    private final ExcelExportPropertyParse propertyParse;
    private Map<String, ExcelExportProperty> propertyMap;
    private final ExcelExportRelated related;

    private Map<Integer, Row> rowMap = new HashMap<>();

    public ExcelExportGenerate(ExcelExportPropertyParse propertyParse, ExcelExportRelated related) {
        workBook = new XSSFWorkbook();
        this.propertyParse = propertyParse;
        this.related = related;
    }

    public ExcelExportGenerate generateHead(Class clazz, List list)
            throws NotFoundException, NoSuchFieldException, IllegalAccessException {
        propertyParse.parseHead(clazz);
        this.propertyMap = propertyParse.parseListField(list);
        DefaultExcelExportParse parse = (DefaultExcelExportParse) propertyParse;
        setSheet(parse.getSheetName());
        for (Map.Entry<String, ExcelExportProperty> entry : propertyMap.entrySet()) {
            if (entry.getKey() == null || entry.getKey().trim().equals("")) {
                continue;
            }
            ExcelExportProperty property = entry.getValue();
            if (property.getHidden() != null && property.getHidden()) {
                continue;
            }
            Row row = putRowIfAbsent(property.getStartRow());
            Row endrow = putRowIfAbsent(property.getEndRow());
            getRowHeadNum(row.getRowNum());
            getRowHeadNum(endrow.getRowNum());
            Cell cell = putCellIfAbsent(row, property.getStartCell());
            CellStyle cellStyle = putCellExcelStyle(cell, property);
            putRowExcelStyle(row, property);
            putRowExcelStyle(endrow, property);
            cell.setCellValue(property.getTitle());
            cell.setCellStyle(cellStyle);
            mergeCell(property);
        }
        return this;
    }

    public ExcelExportGenerate generateBody(List list) {
        String errorName = "";
        String errorField = "";
        try {
            DefaultExcelExportParse propertyParse = (DefaultExcelExportParse) this.propertyParse;
            DefaultExcelExportClassParse classParse = (DefaultExcelExportClassParse) propertyParse.getClassParse();
            Map<String, FieldInfo> fieldListMap = classParse.getFieldListMap();
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (obj == null) {
                    continue;
                }
                Row row = putRowIfAbsent(this.rowHeadNum + 1);
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                for (int i1 = 0; i1 < declaredFields.length; i1++) {
                    Field field = declaredFields[i1];
                    String fieldName = field.getName();
                    errorField = fieldName;
                    field.setAccessible(true);
                    ExcelExportProperty property = this.propertyMap.get(fieldName);
                    if (property == null) {
                        if (fieldListMap.get(fieldName) == null) {
                            continue;
                        } else {
                            List list1 = classParse.getListField(fieldName, obj);
                            if (related != null) {
                                this.related.relate(list1, this, row);
                            }

                        }
                        continue;
                    }
                    if (property.getHasValue() != null && !property.getHasValue()) {
                        continue;
                    }
                    property.setStartRow(this.rowHeadNum);
                    property.setEndRow(this.rowHeadNum);
                    Object value = field.get(obj);
                    property.setValue(value);
                    property.setType(2);
                    fillContext(row, property);
                }


            }
        } catch (Exception e) {
            System.err.println(" 报错的字段是->" + errorField + " 值 是" + errorName);
            e.printStackTrace();
        }

        return this;
    }


    public void fillContext(Row row, ExcelExportProperty property) {
        Cell cell = putCellIfAbsent(row, property.getStartCell());
        CellStyle cellStyle = putCellExcelStyle(cell, property);
        putRowExcelStyle(row, property);
        cellStyle = setDataFormatIfAbsent(property, cellStyle);
        cell.setCellStyle(cellStyle);
        if (property.getValue() instanceof Double) {
            cell.setCellValue((Double) property.getValue());
        } else if (property.getValue() instanceof String) {
            if (property.getDataFormat() != null && !property.getDataFormat().trim().equals("")) {
                if (property.getValue() != null) {
                    cell.setCellValue(new Double((String) property.getValue()));
                }
            } else {
                cell.setCellValue((String) property.getValue());
            }
        } else if (property.getValue() instanceof Integer) {
            cell.setCellValue((Integer) property.getValue());
        } else if (property.getValue() instanceof Boolean) {
            cell.setCellValue((Boolean) property.getValue());
        } else if (property.getDataFormat() != null && !property.getDataFormat().trim().equals("")) {
            if (property.getValue() != null) {
                cell.setCellValue(new Double((String) property.getValue()));
            }
        }
        mergeCell(property);
    }

    public Sheet setSheet(String sheetName) {
        if (StringUtils.isNotBlank(sheetName)) {
            return this.sheet = workBook.createSheet(sheetName);
        }
        Sheet sheet1 = workBook.createSheet("sheet1");
        return this.sheet = sheet1;
    }

    public Row putRowIfAbsent(Integer index) {

        Row row = this.rowMap.get(index);
        if (row == null) {
            row = this.sheet.createRow(index);
            this.rowHeadNum = index;
            this.rowMap.put(index, row);
        }
        return row;
    }

    public void mergeCell(ExcelExportProperty property) {
        if (property.getEndCell().intValue() == property.getStartCell().intValue()
                && property.getStartRow().intValue() == property.getEndRow().intValue()) {
            return;
        }
        if (property.getType().equals(1)) {
            sheet.addMergedRegion(new CellRangeAddress(property.getStartRow(), property.getEndRow(),
                    property.getStartCell(), property.getEndCell()));
        }

    }

    public Cell putCellIfAbsent(Row row, Integer index) {
        return row.createCell(index);
    }

    public CellStyle createCellStyle() {
        return this.workBook.createCellStyle();
    }

    private CellStyle putCellExcelStyle(Cell cell, ExcelExportProperty property) {
        CellStyle cellStyle = createCellStyle();
        if (property.getHorizontalAlignment() != null) {
            cellStyle.setAlignment(property.getHorizontalAlignment());
        }
        if (property.getVerticalAlignment() != null) {
            cellStyle.setVerticalAlignment(property.getVerticalAlignment());
        }
        if (property.getWrapText() != null) {
            cellStyle.setWrapText(property.getWrapText());
        }
        if (property.getColumnWidth() != null) {
            sheet.setColumnWidth(property.getColumnWidth(), property.getColumnWidth() * 256);
            sheet.autoSizeColumn(property.getColumnWidth(), true);
        }
        return cellStyle;
    }

    private Row putRowExcelStyle(Row row, ExcelExportProperty property) {
        if (property.getRowHight() != null) {
            row.setHeight(property.getRowHight());
        }
        return row;
    }

    public Short putDataFormat(ExcelExportProperty property) {
        if (property.getDataFormat() != null) {
            DataFormat dataFormat = this.workBook.createDataFormat();
            short format = dataFormat.getFormat(property.getDataFormat());
            return format;
        }
        return null;
    }

    public CellStyle setDataFormatIfAbsent(ExcelExportProperty property, CellStyle cellStyle) {
        Short dataFormat = putDataFormat(property);
        if (dataFormat != null) {
            cellStyle.setDataFormat(dataFormat);
        }
        return cellStyle;
    }

    public Workbook getWorkBook() {
        return workBook;
    }



    public int getRowHeadNum(int rowNum) {
        if (rowNum > this.rowHeadNum) {
            this.rowHeadNum = rowNum;
        }
        return rowHeadNum;
    }

//    public static void main(String[] args) {
//        System.out.println(checkChineseName("sda"));
//        System.out.println(checkChineseName("汉字"));
//    }


}
