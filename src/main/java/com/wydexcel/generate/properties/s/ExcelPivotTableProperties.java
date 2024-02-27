package com.wydexcel.generate.properties.s;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class ExcelPivotTableProperties {

    private String sourceFrom;
    private String sourceTo;
    private String position;


    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public String getSourceTo() {
        return sourceTo;
    }

    public void setSourceTo(String sourceTo) {
        this.sourceTo = sourceTo;
    }
}
