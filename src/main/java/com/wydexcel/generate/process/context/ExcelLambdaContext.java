package com.wydexcel.generate.process.context;


import com.wydexcel.generate.common.WydExcelReflectUtil;
import com.wydexcel.generate.exception.ExcelParseException;
import com.wydexcel.generate.properties.generate.ExcelConfGenerateImpl;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;

import java.io.OutputStream;
import java.util.Map;
import java.util.function.Consumer;

public class ExcelLambdaContext<T, D extends ColumnFunction<T, Object>> implements ExcelProcess {
    private T t;
    private ExcelConfGenerateImpl impl;
    private Consumer<String> writeConsumer;
    private Consumer<OutputStream> writeStreamConsumer;
    private Consumer<String> colseConsumer;
    private ExcelProcess excelProcess;
    private ExcelWorkPlaceProperties excelWorkPlaceProperties;

    public ExcelLambdaContext(ExcelWorkPlaceProperties json, Class c) {
        this.excelWorkPlaceProperties = json;
        this.t = (T) c;
        ExcelInnerCommonProcess excelInnerCommonProcess = new ExcelInnerCommonProcess(json, this);
        excelProcess = excelInnerCommonProcess.process();
    }

    @Override
    public ExcelSheetGenerate getSheetGenerator() {
        Map<String, ExcelSheetGenerate> generateImplMap = impl.getGenerateImplMap();
        int size = generateImplMap.size();
        if (size == 0) {
            throw new ExcelParseException("expect one ExcelSheetGenerate,but found 0 please put ExcelSheetGenerate");
        } else if (size > 1) {
            throw new ExcelParseException("expect one ExcelSheetGenerate,but found " + impl.getGenerateImplMap().size() + " please using sheetName");
        }

        return impl.getDefaultExcelSheetGenerate();
    }

    @Override
    public ExcelSheetGenerate getSheetGenerator(String sheetName) {
        return impl.getGenerateImplMap().get(sheetName);
    }

    public ExcelLambdaContext(Class c) {
        this.t = (T) c;
    }

    public void setExcelWorkPlaceProperties(ExcelWorkPlaceProperties excelWorkPlaceProperties) {
        this.excelWorkPlaceProperties = excelWorkPlaceProperties;
        ExcelInnerCommonProcess excelInnerCommonProcess = new ExcelInnerCommonProcess(excelWorkPlaceProperties, this);
        excelProcess = excelInnerCommonProcess.process();
    }

    @Override
    public String getSuffix() {
        return impl.getPlaceProperties().getType();
    }

    public T getT() {
        return t;
    }

    public String getInstance() {
        return "";
    }

    public ExcelLambdaContext run(ColumnFunction<T, Object> columnFunction, Object value) {
        String fieldName = WydExcelReflectUtil.getFieldNameByLambda(columnFunction);
//        this.impl.doGenerate(fieldName, value,null);
        return this;
    }

    @Override
    public void writeConsumer(Consumer<String> consumer) {
        this.writeConsumer = consumer;
    }

    @Override
    public void writeStreamConsumer(Consumer<OutputStream> consumer) {
        this.writeStreamConsumer = consumer;
    }

    @Override
    public void closeConsumer(Consumer<String> consumer) {
        colseConsumer = consumer;
    }

    @Override
    public void write(String path) {
        writeConsumer.accept(path);
    }

    @Override
    public void write(OutputStream outputStream) {
        writeStreamConsumer.accept(outputStream);
    }

    @Override
    public void colse() {
        colseConsumer.accept("");
    }


    @Override
    public ExcelConfGenerateImpl setGenerate(ExcelConfGenerateImpl impl) {
        this.impl = impl;
        return this.impl;
    }

    @Override
    public void processSheet() {
        impl.getGenerateImplMap().forEach((k, v) -> {
            v.processSheet();
        });
    }


    //    public static void main(String[] args) {
//        ExcelLambdaContext<ExcelFieldProperties, ColumnFunction<ExcelFieldProperties, Object>> easyLambda = new ExcelLambdaContext(ExcelFieldProperties.class);
//        easyLambda.run(ExcelFieldProperties::getExcelFieldName, "测试纸");
//    }
}
