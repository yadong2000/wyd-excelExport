package com.wydexcel.generate.process;

import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.ExcelPositionProperties;
import com.wydexcel.generate.properties.generate.ExcelArgument;
import com.wydexcel.generate.properties.s.ExcelCellBaseProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class WydExcelCellMergeProcessImpl implements Process<ExcelPositionProperties, CellRangeAddress> {

    private Sheet sheet;
    private ExcelArgument sheetProperties;

    @Override
    public void setSheetConf(ExcelArgument sheetConf) {
        this.sheetProperties = sheetConf;
    }

    @Override
    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public void excelPropertySet(Cell cell, String key, boolean isBody) {
        try {
            ExcelPositionProperties properties = (ExcelPositionProperties) this.sheetProperties.getExcelPropertity("merge", getKey(key, isBody));
            excelPropertySet(cell,properties, isBody);
        } catch (Exception e) {

        }
    }

    @Override
    public int order() {
        return 3;
    }

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_MERGE;
    }

    public CellRangeAddress excelPropertySet(Cell cell, ExcelPositionProperties properties, boolean isBody) {
        if (null == properties) {
            return null;
        }
        CellRangeAddress cellAddresses = properties.getCellRangeAddress();
        ExcelCellBaseProperties baseProperties = properties.getBase();

        if (null != baseProperties) {
            RegionUtil.setBorderBottom(baseProperties.getBorderBottom(), cellAddresses, sheet);
            RegionUtil.setBorderLeft(baseProperties.getBorderLeft(), cellAddresses, sheet);
            RegionUtil.setBorderRight(baseProperties.getBorderRight(), cellAddresses, sheet);
            RegionUtil.setBorderTop(baseProperties.getBorderTop(), cellAddresses, sheet);
            //
            if (null != baseProperties.getBottomBorderColor()) {
                RegionUtil.setBottomBorderColor(baseProperties.getBottomBorderColor(), cellAddresses, sheet);
            }
            if (null != baseProperties.getLeftBorderColor()) {
                RegionUtil.setLeftBorderColor(baseProperties.getLeftBorderColor(), cellAddresses, sheet);
            }
            if (null != baseProperties.getRightBorderColor()) {
                RegionUtil.setRightBorderColor(baseProperties.getRightBorderColor(), cellAddresses, sheet);
            }
            if (null != baseProperties.getTopBorderColor()) {
                RegionUtil.setTopBorderColor(baseProperties.getTopBorderColor(), cellAddresses, sheet);
            }
        }
        sheet.addMergedRegion( cellAddresses);
        return cellAddresses;
    }
}
