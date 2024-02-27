package com.wydexcel.generate.properties.s;


import com.wydexcel.generate.process.context.ExcelContextAllContext;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class ExcelFormatProperties extends ExcelCellBase {


    private String dataformat;

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_DATAFORMAT;
    }

    public String getDataformat() {
        return dataformat;
    }

    public void setDataformat(String dataformat) {
        this.dataformat = dataformat;
    }
}
