package com.convenient.excel.generate;

import com.convenient.excel.beans.dto.ExcelFieldDTO;
import com.convenient.excel.beans.entity.ExcelSheet;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class ExcelExportGenerate {
    private Workbook workBook;
    private final Map<Integer, Row> rowMap = new HashMap<>();
    private final Map<Integer, Short> cellMap = new HashMap<>();
    private final Map<String, ExcelFieldDTO> NAME_MAP = new HashMap<>();

    public ExcelExportGenerate() {
        workBook = new XSSFWorkbook();
    }

    public Sheet generateSheet(ExcelSheet excelSheet) {
        Sheet sheet = workBook.createSheet(excelSheet.getExcelSheetName() == null ? "sheet" : excelSheet.getExcelSheetName());
        if (null != excelSheet.getColumnWidth()) {
            sheet.setDefaultColumnWidth(excelSheet.getColumnWidth() * 256);
        }
        if (null != excelSheet.getAutoSizeColumn() && excelSheet.getAutoSizeColumn().equals(1)) {
            sheet.autoSizeColumn(excelSheet.getColumnWidth(), true);
        }
        return sheet;
    }


    public ExcelExportGenerate generateHeadCell(Sheet sheet, List<ExcelFieldDTO> fieldDTOs) {
        fieldDTOs.forEach(fieldDTO -> {
            for (int i = fieldDTO.getExcelStartRowIndex(); i <= fieldDTO.getExcelEndRowIndex(); i++)
                if (rowMap.get(i) == null)
                    rowMap.put(i, sheet.createRow(i));
            Cell cell = putCellIfAbsent(rowMap.get(fieldDTO.getExcelStartRowIndex()), fieldDTO.getExcelStartCellIndex());
            CellStyle cellStyle = putCellExcelStyle(fieldDTO);
            putRowExcelStyle(rowMap.get(fieldDTO.getExcelStartRowIndex()), fieldDTO);
            putRowExcelStyle(rowMap.get(fieldDTO.getExcelEndRowIndex()), fieldDTO);
            setCell(cell, fieldDTO, cellStyle, sheet);
            mergeCell(fieldDTO, sheet);
            NAME_MAP.put(fieldDTO.getExcelFieldName(), fieldDTO);
        });
        return this;
    }

    public ExcelExportGenerate generateBodyRow(List list) {


        return this;
    }

    public ExcelExportGenerate generateBodyCell(ExcelFieldDTO fieldDTO) {
        return this;
    }


    public void mergeCell(ExcelFieldDTO property, Sheet sheet) {
        if (property.getExcelEndCellIndex() == property.getExcelStartCellIndex()
                && property.getExcelStartRowIndex() == property.getExcelEndRowIndex()) {
            return;
        }
        sheet.addMergedRegion(new CellRangeAddress(property.getExcelStartRowIndex(), property.getExcelEndRowIndex(),
                property.getExcelStartCellIndex(), property.getExcelEndCellIndex()));
    }

    public Cell putCellIfAbsent(Row row, Integer index) {
        return row.createCell(index);
    }

    public CellStyle createCellStyle() {
        return this.workBook.createCellStyle();
    }


    public void setCell(Cell cell, ExcelFieldDTO fieldDTO, CellStyle cellStyle, Sheet sheet) {

        cell.setCellValue(fieldDTO.getExcelFieldTitle());
        cell.setCellStyle(cellStyle);
        if (fieldDTO.getColumnWidth() != null) {
            Short aShort = cellMap.get(cell.getColumnIndex());
            if (aShort == null) {
                sheet.setColumnWidth(cell.getColumnIndex(), fieldDTO.getColumnWidth() * 256);
                cellMap.put(cell.getColumnIndex(), fieldDTO.getColumnWidth());
            }
        }
        if (fieldDTO.getHyperlink() != null) {
            CreationHelper creationHelper = workBook.getCreationHelper();
            XSSFHyperlink link = (XSSFHyperlink) creationHelper.createHyperlink(HyperlinkType.URL);
            link.setAddress(fieldDTO.getHyperlink());
            cell.setHyperlink(link);
        }
    }

    private CellStyle putCellExcelStyle(ExcelFieldDTO property) {
        CellStyle cellStyle = createCellStyle();
        if (property.getHorizontalAlignment() != null) {
            cellStyle.setAlignment(HorizontalAlignment.valueOf(property.getHorizontalAlignment()));
        }
        if (property.getVerticalAlignment() != null) {
            cellStyle.setVerticalAlignment(VerticalAlignment.valueOf(property.getVerticalAlignment()));
        }
        if (property.getWrapTest() != null) {
            cellStyle.setWrapText(Boolean.valueOf(property.getWrapTest().toString()));
        }

        if (property.getBorderLeft() != null) {
            cellStyle.setBorderLeft(BorderStyle.valueOf(property.getBorderLeft()));
        }
        if (property.getBorderRight() != null) {
            cellStyle.setBorderRight(BorderStyle.valueOf(property.getBorderRight()));
        }
        if (property.getBorderTop() != null) {
            cellStyle.setBorderTop(BorderStyle.valueOf(property.getBorderTop()));
        }
        if (property.getBorderBottom() != null) {
            cellStyle.setBorderBottom(BorderStyle.valueOf(property.getBorderLeft()));
        }

        if (property.getBottomBorderColor() != null) {
            cellStyle.setBottomBorderColor(property.getBottomBorderColor());
        }
        if (property.getTopBorderColor() != null) {
            cellStyle.setBottomBorderColor(property.getTopBorderColor());
        }
        if (property.getLeftBorderColor() != null) {
            cellStyle.setLeftBorderColor(property.getLeftBorderColor());
        }
        if (property.getRightBorderColor() != null) {
            cellStyle.setRightBorderColor(property.getRightBorderColor());
        }
        if (property.getFillPattern() != null) {
            cellStyle.setFillPattern(FillPatternType.valueOf(property.getFillPattern()));
        }
        if (property.getFillForeGroundColor() != null) {
            cellStyle.setFillForegroundColor(property.getFillForeGroundColor());
        }
        if (property.getFillBackgroundColor() != null) {
            cellStyle.setFillBackgroundColor(property.getFillBackgroundColor());
        }
        if (property.getLocked() != null) {
            cellStyle.setLocked(Boolean.valueOf(property.getLocked().toString()));
        }
        cellStyle.setFont(setFont(property));
        return cellStyle;
    }

    private Font setFont(ExcelFieldDTO property) {
        Font font = this.workBook.createFont();
        if (property.getFontHeight() != null) {
            font.setFontHeight(property.getFontHeight());
        }
        if (property.getBold() != null) {
            font.setBold(Boolean.valueOf(property.getBold().toString()));
        }
        if (property.getFontName() != null) {
            font.setFontName(property.getFontName());
        }
        if (property.getFontHeightPoints() != null) {
            font.setFontHeightInPoints(property.getFontHeightPoints());
        }
        if (property.getStrikeout() != null) {
            font.setStrikeout(Boolean.valueOf(property.getStrikeout().toString()));
        }
        if (property.getItalic() != null) {
            font.setItalic(Boolean.valueOf(property.getItalic().toString()));
        }
//        Font.COLOR_RED;
        if (property.getColor() != null) {
            font.setColor(property.getColor());
        }
        if (property.getUnderLine() != null) {
            font.setUnderline(property.getUnderLine());
        }
        return font;
    }

    public void fillContext(Row row, final ExcelFieldDTO property, Sheet sheet) {
        Cell cell = putCellIfAbsent(row, property.getExcelStartCellIndex());
        CellStyle cellStyle = putCellExcelStyle(property);
        putRowExcelStyle(row, property);
        cellStyle = setDataFormatIfAbsent(property, cellStyle);
        if (property.getDataFormat() != null && !property.getDataFormat().equals("")) {
            cell.setCellValue(new Double((String) property.getValue()));
        } else {
            if (property.getValue() == null) {
                cell.setCellValue(property.getDefaultValue());
            } else {
                cell.setCellValue(property.getValue().toString());
            }
        }
        cell.setCellStyle(cellStyle);
        mergeCell(property, sheet);
    }

    private Row putRowExcelStyle(Row row, ExcelFieldDTO property) {
//        if (property.getRowHight() != null) {
//            row.setHeight(property.getRowHight());
//        }
        return row;
    }

    public Short putDataFormat(ExcelFieldDTO property) {
        if (property.getDataFormat() != null) {
            DataFormat dataFormat = this.workBook.createDataFormat();
            short format = dataFormat.getFormat(String.valueOf(property.getDataFormat()));
            return format;
        }
        return null;
    }

    public CellStyle setDataFormatIfAbsent(ExcelFieldDTO property, CellStyle cellStyle) {
        Short dataFormat = putDataFormat(property);
        if (dataFormat != null) {
            cellStyle.setDataFormat(dataFormat);
        }
        return cellStyle;
    }


    public static void main(String[] args) {
        ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM);
    }

    public void write(OutputStream outputStream) throws IOException {
        this.workBook.write(outputStream);
    }

}
