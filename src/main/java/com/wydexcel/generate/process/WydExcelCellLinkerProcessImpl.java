package com.wydexcel.generate.process;

import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.generate.ExcelArgument;
import com.wydexcel.generate.properties.s.ExcelLinkProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

public class WydExcelCellLinkerProcessImpl implements Process<ExcelLinkProperties, Hyperlink> {

    private Map<String, Hyperlink> map = new HashMap<>();
    private Workbook workbook;
    private CreationHelper creationHelper;
    ExcelArgument sheetProperties;

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_LINK;
    }

    @Override
    public void setSheetConf(ExcelArgument sheetConf) {
        sheetProperties = sheetConf;
    }

    @Override
    public void setWorkBook(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public void excelPropertySet(Cell cell, String fieldName, boolean isBody) {
        ExcelLinkProperties property = (ExcelLinkProperties) sheetProperties.getExcelPropertity("link", getKey(fieldName, isBody));
        if (null == property) {
            return;
        }
        excelPropertySet(cell, property, isBody);
    }


    @Override
    public Hyperlink excelPropertySet(Cell cell, ExcelLinkProperties property, boolean isBody) {
        if (null == property) {
            return null;
        }
        if (null == creationHelper) {
            creationHelper = workbook.getCreationHelper();
        }
        Hyperlink hyperlink = creationHelper.createHyperlink(property.getHyperlinkType());
        hyperlink.setAddress(property.getLink());
        return hyperlink;
    }
}
