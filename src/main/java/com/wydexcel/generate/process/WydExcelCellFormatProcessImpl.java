package com.wydexcel.generate.process;

import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.s.ExcelFormatProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;

public class WydExcelCellFormatProcessImpl implements Process<ExcelFormatProperties, Short> {
    private DataFormat dataFormat;

    @Override
    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_DATAFORMAT;
    }

    @Override
    public int order() {
        return 1;
    }


    @Override
    public Short excelPropertySet(Cell cell, ExcelFormatProperties property, boolean isBody) {
        return dataFormat.getFormat(property.getDataformat());
    }

    public Short process(ExcelFormatProperties property, boolean isBody) {
        return dataFormat.getFormat(property.getDataformat());
    }
}
