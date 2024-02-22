package com.wydexcel.generate.process;

import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.s.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.List;
import java.util.Map;


public class DefaultWydExcelXSSFSheetProcess implements WydExcelSheetProcess {

    @Override
    public int order() {
        return 1;
    }

    @Override
    public Object excelPropertySet(Cell cell, ExcelCellBase excelBase, boolean isBody) {
        return null;
    }

    @Override
    public ExcelAbstractSheetProperties importProcess(Sheet sheet, ExcelAbstractSheetProperties conf) {
        return conf;
    }

    @Override
    public void afterExportProcessSheet(Sheet sheet, ExcelAbstractSheetProperties conf) {
//        sheet.setDefaultColumnWidth(conf.getColumnWidth() * 256); //单独设置有问题
//        sheet.autoSizeColumn(cell.getColumnIndex());
        List<ExcelCellBaseProperties> baseList = conf.getBases();
        if (sheet instanceof SXSSFSheet) {
            SXSSFSheet sxssfSheet = (SXSSFSheet) sheet;
            sxssfSheet.trackAllColumnsForAutoSizing();
        }
        for (ExcelCellBaseProperties basis : baseList) {
            if (null == basis) {
                continue;
            }
            if (null != basis.getAutoSizeColumn() && basis.getAutoSizeColumn()) {
                ExcelFieldProperties excelFieldProperties = conf.excelFileMap.get(basis.getFieldName());
                if (null == excelFieldProperties) {
                    continue;
                }
                sheet.autoSizeColumn(excelFieldProperties.getExcelStartCellIndex());
            }
        }
        //设置sheet页默认选中
        if (null != conf.getSelected()) {
            sheet.setSelected(conf.getSelected());
            Workbook workbook = sheet.getWorkbook();
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                if (workbook.getSheetAt(i).getSheetName().equals(sheet.getSheetName())) {
                    workbook.setActiveSheet(i);
                    break;
                }
            }
        }
        //设置冻结行数
        for (ExcelFreezePaneProperties freeze : conf.getFreezes()) {
            if (null == freeze.getFirstCellNum() && null == freeze.getFirstRowNum()) {
                sheet.createFreezePane(freeze.getCellNum(), freeze.getRowNum());
                continue;
            }
            if (null == freeze.getActivePane()) {
                sheet.createFreezePane(freeze.getCellNum(), freeze.getRowNum(), freeze.getFirstCellNum(), freeze.getFirstRowNum());
                continue;
            }
            sheet.createSplitPane(freeze.getCellNum(), freeze.getRowNum(), freeze.getFirstCellNum(), freeze.getFirstRowNum(), freeze.getActivePane());

        }

        ExcelPrintSetupProperties printSetupProperties = conf.getPrintSetup();
        if (null != printSetupProperties) {
            PrintSetup printSetup = sheet.getPrintSetup();
            if (null != printSetupProperties.getCopies()) {
                printSetup.setCopies(printSetupProperties.getCopies());
            }
            if (null != printSetupProperties.getDraft()) {
                printSetup.setDraft(printSetupProperties.getDraft());
            }
            if (null != printSetupProperties.getFitHeight()) {
                printSetup.setFitHeight(printSetupProperties.getFitHeight());
            }
            if (null != printSetupProperties.getFitWidth()) {
                printSetup.setFitWidth(printSetupProperties.getFitWidth());
            }
            if (null != printSetupProperties.getFooterMargin()) {
                printSetup.setFooterMargin(printSetupProperties.getFooterMargin());
            }
            if (null != printSetupProperties.getHeaderMargin()) {
                printSetup.setHeaderMargin(printSetupProperties.getHeaderMargin());
            }
            if (null != printSetupProperties.gethResolution()) {
                printSetup.setHResolution(printSetupProperties.gethResolution());
            }
            if (null != printSetupProperties.getLandscape()) {
                printSetup.setLandscape(printSetupProperties.getLandscape());
            }
            if (null != printSetupProperties.getLeftToRight()) {
                printSetup.setLeftToRight(printSetupProperties.getLeftToRight());
            }
            if (null != printSetupProperties.getNoColor()) {
                printSetup.setNoColor(printSetupProperties.getNoColor());
            }
            if (null != printSetupProperties.getNoOrientation()) {
                printSetup.setNoOrientation(printSetupProperties.getNoOrientation());
            }
            if (null != printSetupProperties.getNotes()) {
                printSetup.setNotes(printSetupProperties.getNotes());
            }
            if (null != printSetupProperties.getPageStart()) {
                printSetup.setPageStart(printSetupProperties.getPageStart());
            }
            if (null != printSetupProperties.getPaperSize()) {
                printSetup.setPaperSize(printSetupProperties.getPaperSize());
            }
            if (null != printSetupProperties.getScale()) {
                printSetup.setScale(printSetupProperties.getScale());
            }
            if (null != printSetupProperties.getUsePage()) {
                printSetup.setUsePage(printSetupProperties.getUsePage());
            }
            if (null != printSetupProperties.getValidSettings()) {
                printSetup.setValidSettings(printSetupProperties.getValidSettings());
            }
            if (null != printSetupProperties.getvResolution()) {
                printSetup.setVResolution(printSetupProperties.getvResolution());
            }


        }

        for (ExcelHeaderOrFooterProperties excelHeaderOrFooterProperties : conf.getHeaderOrFootesr()) {
            if (excelHeaderOrFooterProperties.getType().equals("header")) {
                Footer footer = sheet.getFooter();
                footer.setRight(excelHeaderOrFooterProperties.getRight());
                footer.setLeft(excelHeaderOrFooterProperties.getLeft());
                footer.setCenter(excelHeaderOrFooterProperties.getCenter());
                continue;
            }
            Header header = sheet.getHeader();
            header.setRight(excelHeaderOrFooterProperties.getRight());
            header.setLeft(excelHeaderOrFooterProperties.getLeft());
            header.setCenter(excelHeaderOrFooterProperties.getCenter());
        }

        Map<Integer, Integer> columnWidthMap = conf.getColumnWidthMap();
        columnWidthMap.forEach((k, v) -> {
            sheet.setColumnWidth(k, v * 256);
        });
    }

    @Override
    public void afterExportProcess(Sheet sheet, ExcelAbstractSheetProperties conf) {


    }
}
