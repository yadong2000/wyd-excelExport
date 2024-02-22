package com.wydexcel.generate.process.context;

import com.wydexcel.generate.process.custom.CustomProcess;
import com.wydexcel.generate.properties.generate.ExcelConfGenerateImpl;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

public class ExcelInnerCommonProcess {
    private ExcelConfGenerateImpl excelConfGenerate;
    private final ExcelProcess excelProcess;
    private ExcelWorkPlaceProperties workPlaceProperties;
    private Workbook workbook;
    private final List<CustomProcess> customProcessList;

    public ExcelInnerCommonProcess(ExcelWorkPlaceProperties workPlaceProperties, ExcelProcess excelProcess) {
        this.excelProcess = excelProcess;
        this.workPlaceProperties = workPlaceProperties;
        customProcessList = Collections.emptyList();
    }

    public ExcelInnerCommonProcess(ExcelWorkPlaceProperties workPlaceProperties, ExcelProcess excelProcess, List<CustomProcess> customProcessList) {
        this.excelProcess = excelProcess;
        this.workPlaceProperties = workPlaceProperties;
        this.customProcessList = customProcessList;
    }


    public ExcelProcess process() {
        try {
            switch (workPlaceProperties.getType()) {
                case ExcelWorkPlaceProperties.VERSION_LOW_MEMORY:
                    if (workPlaceProperties.getWindowSize() != null && workPlaceProperties.getWindowSize() > 0) {
                        workbook = new SXSSFWorkbook(workPlaceProperties.getWindowSize());
                    }
                    break;
                case ExcelWorkPlaceProperties.VERSION_2007:
                    workbook = new XSSFWorkbook();
                    break;
                case ExcelWorkPlaceProperties.VERSION_BEFORE_2007:
                    workbook = new HSSFWorkbook();
                    break;
            }

            excelConfGenerate = new ExcelConfGenerateImpl(workPlaceProperties, workbook ,this.customProcessList);
            ExcelWorkPlaceProperties placeProperties = excelConfGenerate.getPlaceProperties();
            if (null == placeProperties || null == placeProperties.getMap()) {
                return excelProcess;
            }
            excelProcess.setGenerate(excelConfGenerate);
            excelProcess.writeConsumer(this::write);
            excelProcess.writeStreamConsumer(this::write);
            excelProcess.closeConsumer(this::close);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelProcess;
    }

    public void write(String path) {
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void write(OutputStream outputStream) {
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(String path) {
        try {
            if (workbook instanceof SXSSFWorkbook) {
                SXSSFWorkbook sxssfWorkbook = (SXSSFWorkbook) this.workbook;
                sxssfWorkbook.dispose();
            }
            this.workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
