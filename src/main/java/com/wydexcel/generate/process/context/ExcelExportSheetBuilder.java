package com.wydexcel.generate.process.context;


import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.s.ExcelCellBase;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;


public class ExcelExportSheetBuilder {


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ExcelAbstractSheetProperties abstractSheetProperties;

        private Builder() {
            abstractSheetProperties = new ExcelAbstractSheetProperties();
        }

        public Builder name(String name) {
            abstractSheetProperties.setSheetName(name);
            return this;
        }

        public Builder num(Integer num) {
            abstractSheetProperties.setSheetNum(num);
            return this;
        }

        public Builder field(ExcelFieldProperties properties) {
            abstractSheetProperties.getCells().add(properties);
            String using = properties.getUsing();
            Arrays.stream(using.split(",")).forEach(i->{

            });
            return this;
        }

        public Builder fieldAndProperties(ExcelFieldProperties properties, List<ExcelCellBase> excelBases) {
            abstractSheetProperties.getCells().add(properties);
            for (ExcelCellBase excelBase : excelBases) {
                excelBase.setFieldName(properties.getExcelFieldName());
                abstractSheetProperties.getBaseProperties().add(excelBase);
            }
            return this;
        }


        public Builder field(Supplier<ExcelFieldProperties> properties) {
            if (null == properties) {
                throw new IllegalArgumentException("");
            }
            abstractSheetProperties.getCells().add(properties.get());
            return this;
        }

        public Builder select() {
            abstractSheetProperties.setSelected(true);
            return this;
        }

        public Builder property(Supplier<ExcelCellBase> excelBaseSupplier) {

            if (null == excelBaseSupplier) {
                throw new IllegalArgumentException("");
            }
            ExcelCellBase excelBase = excelBaseSupplier.get();
            return getBuilder(excelBase);
        }

        private Builder getBuilder(ExcelCellBase excelBase) {
            if (null == excelBase) {
                throw new IllegalArgumentException("");
            }
            if (excelBase.getId() != null && !excelBase.getId().equals("")) {
                excelBase.baseValidate(true);
                ExcelContextAllContext.getInstance().setUsingMap(excelBase);
            }
            if (excelBase.getFieldName() != null && !excelBase.getFieldName().equals("")) {
                abstractSheetProperties.getBaseProperties().add(excelBase);
            }

            return this;
        }

        public Builder property(ExcelCellBase excelBase) {
            return getBuilder(excelBase);
        }

        public ExcelAbstractSheetProperties build() {
            abstractSheetProperties.buildForList();
            return abstractSheetProperties;
        }


    }


}
