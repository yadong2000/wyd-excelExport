package com.wydexcel.generate.process.context;


import com.wydexcel.generate.properties.generate.ExcelConfGenerateImpl;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface ExcelProcess {

    default void run(Map<String, Object> data) {
    }

    default void runMerge(List<Map<String, Object>> data) {
    }

    default void run(Object data) {
    }

    default ExcelConfGenerateImpl setGenerate(ExcelConfGenerateImpl impl) {
        return null;
    }


    ExcelSheetGenerate getSheetGenerator(String sheetName);


    void writeConsumer(Consumer<String> consumer);

    void writeStreamConsumer(Consumer<OutputStream> consumer);

    void closeConsumer(Consumer<String> consumer);

    void write(String path);

    void write(OutputStream outputStream);

    void colse();


    void processSheet();

    String getSuffix();

    ExcelSheetGenerate getSheetGenerator();

}
