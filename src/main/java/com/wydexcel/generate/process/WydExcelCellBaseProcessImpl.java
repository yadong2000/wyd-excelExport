package com.wydexcel.generate.process;

import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.generate.ExcelArgument;
import com.wydexcel.generate.properties.s.ExcelCellBaseProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Workbook;

public class WydExcelCellBaseProcessImpl implements Process<ExcelCellBaseProperties, CellStyle> {

    private Workbook workbook;
    private ExcelArgument sheetConf;

    @Override
    public int order() {
        return -1;
    }

    @Override
    public void setWorkBook(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public void setSheetConf(ExcelArgument sheetConf) {
        this.sheetConf = sheetConf;
    }

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_BASE;
    }

    @Override
    public void excelPropertySet(Cell cell, String fieldName, boolean isBody) {
        try {

            ExcelCellBaseProperties property = (ExcelCellBaseProperties) sheetConf.getExcelPropertity("base", getKey(fieldName, isBody));
            if (null == property) {
                return;
            }
            excelPropertySet(cell, property, isBody);
        } catch (Exception e) {

        }
    }

    public CellStyle process(String fieldName, ExcelCellBaseProperties property, boolean isBody) {
        CellStyle cellStyle = workbook.createCellStyle();
        if(null==property){
            return cellStyle;
        }
//        String key = property.getFieldName() + property.getIsHeader() + isBody;

        if (null != property.getAlignment()) {
            cellStyle.setAlignment((property.getAlignment()));
        }
        if (null != property.getVerticalAlignment()) {
            cellStyle.setVerticalAlignment((property.getVerticalAlignment()));
        }
        cellStyle.setWrapText(property.isWrapTest());
        if (null != property.getBorderLeft()) {
            cellStyle.setBorderLeft((property.getBorderLeft()));
        }
        if (null != property.getBorderRight()) {
            cellStyle.setBorderRight((property.getBorderRight()));
        }
        if (null != property.getBorderTop()) {
            cellStyle.setBorderTop(property.getBorderTop());
        }
        if (null != property.getBorderLeft()) {
            cellStyle.setBorderBottom((property.getBorderLeft()));
        }
        if (null != property.getBottomBorderColor()) {
            cellStyle.setBottomBorderColor(property.getBottomBorderColor());
        }
        if (null != property.getTopBorderColor()) {
            cellStyle.setBottomBorderColor(property.getTopBorderColor());
        }
        if (null != property.getLeftBorderColor()) {
            cellStyle.setLeftBorderColor(property.getLeftBorderColor());
        }
        if (null != property.getRightBorderColor()) {
            cellStyle.setRightBorderColor(property.getRightBorderColor());
        }

        if (null != property.getFillBackgroundColor()) {
            cellStyle.setFillPattern(FillPatternType.NO_FILL);
            cellStyle.setFillBackgroundColor(property.getFillBackgroundColor());
        }
        if (null != property.getFillPattern()) {
            cellStyle.setFillPattern(property.getFillPattern());
        }
        if (null != property.getFillForeGroundColor()) {
            cellStyle.setFillForegroundColor(property.getFillForeGroundColor());
        }
        if (null != property.getFillForeGroundColor()) {
            cellStyle.setFillForegroundColor(property.getFillForeGroundColor());
        }
        if (null != property.getIndention()) {
            cellStyle.setIndention(property.getIndention());
        }
        if (null != property.getRotation()) {
            cellStyle.setRotation(property.getRotation());
        }
        if (null != property.getQuotePrefixed()) {
            cellStyle.setQuotePrefixed(property.getQuotePrefixed());
        }
        if (null != property.getHidden()) {
            cellStyle.setHidden(property.getHidden());
        }
        if (null != property.getShrinkToFit()) {
            cellStyle.setShrinkToFit(property.getShrinkToFit());
        }
        cellStyle.setLocked(property.isLocked());
        return cellStyle;
    }

    public CellStyle excelPropertySet(Cell cell, ExcelCellBaseProperties property, boolean isBody) {
        try {
            if (null == property) {
//                String key = cell.getColumnIndex() + "_null_" + isBody;
                CellStyle cellStyle = workbook.createCellStyle();
                return cellStyle;
            }
//            String key = property.getFieldName() + property.getIsHeader() + isBody;

            CellStyle cellStyle = workbook.createCellStyle();
            if (null != property.getAlignment()) {
                cellStyle.setAlignment((property.getAlignment()));
            }
            if (null != property.getVerticalAlignment()) {
                cellStyle.setVerticalAlignment((property.getVerticalAlignment()));
            }
            cellStyle.setWrapText(property.isWrapTest());
            if (null != property.getBorderLeft()) {
                cellStyle.setBorderLeft((property.getBorderLeft()));
            }
            if (null != property.getBorderRight()) {
                cellStyle.setBorderRight((property.getBorderRight()));
            }
            if (null != property.getBorderTop()) {
                cellStyle.setBorderTop(property.getBorderTop());
            }
            if (null != property.getBorderLeft()) {
                cellStyle.setBorderBottom((property.getBorderLeft()));
            }
            if (null != property.getBottomBorderColor()) {
                cellStyle.setBottomBorderColor(property.getBottomBorderColor());
            }
            if (null != property.getTopBorderColor()) {
                cellStyle.setBottomBorderColor(property.getTopBorderColor());
            }
            if (null != property.getLeftBorderColor()) {
                cellStyle.setLeftBorderColor(property.getLeftBorderColor());
            }
            if (null != property.getRightBorderColor()) {
                cellStyle.setRightBorderColor(property.getRightBorderColor());
            }

            if (null != property.getFillBackgroundColor()) {
                cellStyle.setFillPattern(FillPatternType.NO_FILL);
                cellStyle.setFillBackgroundColor(property.getFillBackgroundColor());
            }
            if (null != property.getFillPattern()) {
                cellStyle.setFillPattern(property.getFillPattern());
            }
            if (null != property.getFillForeGroundColor()) {
                cellStyle.setFillForegroundColor(property.getFillForeGroundColor());
            }
            if (null != property.getFillForeGroundColor()) {
                cellStyle.setFillForegroundColor(property.getFillForeGroundColor());
            }
            if (null != property.getIndention()) {
                cellStyle.setIndention(property.getIndention());
            }
            if (null != property.getRotation()) {
                cellStyle.setRotation(property.getRotation());
            }
            if (null != property.getQuotePrefixed()) {
                cellStyle.setQuotePrefixed(property.getQuotePrefixed());
            }
            if (null != property.getHidden()) {
                cellStyle.setHidden(property.getHidden());
            }
            if (null != property.getShrinkToFit()) {
                cellStyle.setShrinkToFit(property.getShrinkToFit());
            }
            cellStyle.setLocked(property.isLocked());
            return cellStyle;
        } catch (Exception e) {

        }
        return null;
    }
}
