package com.wydexcel.generate.process.context;


import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;

public class ExcelExportBuilder {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ExcelWorkPlaceProperties workPlaceProperties;

        private Builder() {
            workPlaceProperties = new ExcelWorkPlaceProperties();
        }

        public Builder type(String type) {
            workPlaceProperties.setType(type);
            return this;
        }

        public Builder id(String id) {
            workPlaceProperties.setId(id);
            return this;
        }

        public Builder windowSize(Integer windowSize) {
            workPlaceProperties.setWindowSize(windowSize);
            return this;
        }

        public Builder sheet(ExcelAbstractSheetProperties sheet) {
            workPlaceProperties.getMap().put(sheet.getSheetName(), sheet);
            return this;
        }

        public ExcelWorkPlaceProperties build() {
            return workPlaceProperties;
        }
    }
    ///////////////////////////////////////////////////////


}
