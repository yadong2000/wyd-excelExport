package com.convenient.excel.export.generation;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;

public class ExcelExportGenerate {


//    private Workbook workBook;
//    private Sheet sheet;
//    private int rowHeadNum;
//
//
//    private Map<Integer, Row> rowMap = new HashMap<>();
//
//    public ExcelExportGenerate() {
//        workBook = new XSSFWorkbook();
//    }
//
//
//    public void fillContext(Row row, ExcelExportProperty property) {
//        Cell cell = putCellIfAbsent(row, property.getStartCell());
//        CellStyle cellStyle = putCellExcelStyle(cell, property);
//        putRowExcelStyle(row, property);
//        cellStyle = setDataFormatIfAbsent(property, cellStyle);
//        cell.setCellStyle(cellStyle);
//        if (property.getValue() instanceof Double) {
//            cell.setCellValue((Double) property.getValue());
//        } else if (property.getValue() instanceof String) {
//            if (property.getDataFormat() != null && !property.getDataFormat().trim().equals("")) {
//                if (property.getValue() != null) {
//                    cell.setCellValue(new Double((String) property.getValue()));
//                }
//            } else {
//                cell.setCellValue((String) property.getValue());
//            }
//        } else if (property.getValue() instanceof Integer) {
//            cell.setCellValue((Integer) property.getValue());
//        } else if (property.getValue() instanceof Boolean) {
//            cell.setCellValue((Boolean) property.getValue());
//        } else if (property.getDataFormat() != null && !property.getDataFormat().trim().equals("")) {
//            if (property.getValue() != null) {
//                cell.setCellValue(new Double((String) property.getValue()));
//            }
//        }
//        mergeCell(property);
//    }
//
//    public Sheet setSheet(String sheetName) {
//        if (StringUtils.isNotBlank(sheetName)) {
//            return this.sheet = workBook.createSheet(sheetName);
//        }
//        Sheet sheet1 = workBook.createSheet("sheet1");
//        return this.sheet = sheet1;
//    }
//
//    public Row putRowIfAbsent(Integer index) {
//
//        Row row = this.rowMap.get(index);
//        if (row == null) {
//            row = this.sheet.createRow(index);
//            this.rowHeadNum = index;
//            this.rowMap.put(index, row);
//        }
//        return row;
//    }
//
//    public void mergeCell(ExcelExportProperty property) {
//        if (property.getEndCell().intValue() == property.getStartCell().intValue()
//                && property.getStartRow().intValue() == property.getEndRow().intValue()) {
//            return;
//        }
//        if (property.getType().equals(1)) {
//            sheet.addMergedRegion(new CellRangeAddress(property.getStartRow(), property.getEndRow(),
//                    property.getStartCell(), property.getEndCell()));
//        }
//
//    }
//
//    public Cell putCellIfAbsent(Row row, Integer index) {
//        return row.createCell(index);
//    }
//
//    public CellStyle createCellStyle() {
//        return this.workBook.createCellStyle();
//    }
//
//    private CellStyle putCellExcelStyle(Cell cell, ExcelExportProperty property) {
//        CellStyle cellStyle = createCellStyle();
//        if (property.getHorizontalAlignment() != null) {
//            cellStyle.setAlignment(property.getHorizontalAlignment());
//        }
//        if (property.getVerticalAlignment() != null) {
//            cellStyle.setVerticalAlignment(property.getVerticalAlignment());
//        }
//        if (property.getWrapText() != null) {
//            cellStyle.setWrapText(property.getWrapText());
//        }
//        if (property.getColumnWidth() != null) {
//            sheet.setColumnWidth(property.getColumnWidth(), property.getColumnWidth() * 256);
//            sheet.autoSizeColumn(property.getColumnWidth(), true);
//        }
//        return cellStyle;
//    }
//
//    private Row putRowExcelStyle(Row row, ExcelExportProperty property) {
//        if (property.getRowHight() != null) {
//            row.setHeight(property.getRowHight());
//        }
//        return row;
//    }
//
//    public Short putDataFormat(ExcelExportProperty property) {
//        if (property.getDataFormat() != null) {
//            DataFormat dataFormat = this.workBook.createDataFormat();
//            short format = dataFormat.getFormat(property.getDataFormat());
//            return format;
//        }
//        return null;
//    }
//
//    public CellStyle setDataFormatIfAbsent(ExcelExportProperty property, CellStyle cellStyle) {
//        Short dataFormat = putDataFormat(property);
//        if (dataFormat != null) {
//            cellStyle.setDataFormat(dataFormat);
//        }
//        return cellStyle;
//    }
//
//    public Workbook getWorkBook() {
//        return workBook;
//    }
//
//
//    public int getRowHeadNum(int rowNum) {
//        if (rowNum > this.rowHeadNum) {
//            this.rowHeadNum = rowNum;
//        }
//        return rowHeadNum;
//    }

    public static void main(String[] args) {

    }


}
