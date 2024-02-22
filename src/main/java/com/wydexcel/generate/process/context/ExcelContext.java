package com.wydexcel.generate.process.context;

import com.wydexcel.generate.exception.ExcelParseException;
import com.wydexcel.generate.process.custom.CustomProcess;
import com.wydexcel.generate.properties.generate.ExcelConfGenerateImpl;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ExcelContext {

    public ExcelContext(ExcelWorkPlaceProperties properties) {
        ExcelInnerCommonProcess excelInnerCommonProcess = new ExcelInnerCommonProcess(properties, excelProcess);
        excelProcess = excelInnerCommonProcess.process();
    }

    public ExcelContext(ExcelWorkPlaceProperties properties, List<CustomProcess> customProcessList) {
        ExcelInnerCommonProcess excelInnerCommonProcess = new ExcelInnerCommonProcess(properties, excelProcess,customProcessList);
        excelProcess = excelInnerCommonProcess.process();
    }


    ExcelProcess excelProcess = new ExcelProcess() {
        private ExcelConfGenerateImpl impl;
        private Consumer<String> writeConsumer;
        private Consumer<OutputStream> writeStreamConsumer;
        private Consumer<String> colseConsumer;


        @Override
        public ExcelSheetGenerate getSheetGenerator(String sheetName) {
            return impl.getGenerateImplMap().get(sheetName);
        }


        @Override
        public void writeConsumer(Consumer<String> consumer) {
            this.writeConsumer = consumer;
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
        public void writeStreamConsumer(Consumer<OutputStream> consumer) {
            this.writeStreamConsumer = consumer;
        }

        @Override
        public void write(String path) {
            this.writeConsumer.accept(path);
        }

        @Override
        public void write(OutputStream outputStream) {
            this.writeStreamConsumer.accept(outputStream);
        }

        @Override
        public void closeConsumer(Consumer<String> consumer) {
            colseConsumer = consumer;
        }

        @Override
        public void colse() {
            colseConsumer.accept("");
            impl.getGenerateImplMap().clear();
        }

        @Override
        public void processSheet() {
            impl.getGenerateImplMap().forEach((k, v) -> {
                v.processSheet();
            });
//            SheetUtil.
        }

        @Override
        public String getSuffix() {
            return impl.getPlaceProperties().getType();
        }

        @Override
        public ExcelConfGenerateImpl setGenerate(ExcelConfGenerateImpl impl) {
            this.impl = impl;
            return this.impl;
        }
    };

    public ExcelSheetGenerate getSheetGenerator(String sheetName) {
        return excelProcess.getSheetGenerator(sheetName);
    }

    public ExcelSheetGenerate getSheetGenerator() {

        return excelProcess.getSheetGenerator();
    }


    public void write(String path) {
        excelProcess.processSheet();
        excelProcess.write(path + "." + excelProcess.getSuffix());
    }

    public void write(OutputStream outputStream) {
        excelProcess.processSheet();
        excelProcess.write(outputStream);
    }

    public void colse() {
        excelProcess.colse();
        excelProcess = null;
    }
}
