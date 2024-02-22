package com.wydexcel.generate.properties.generate;


import com.wydexcel.generate.process.*;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;

public class ExcelSheetGenerate extends ExcelCommonProcess {
    private ExcelRowGenerateRow rowGenerate;

    private final Sheet sheet;
    private final int sheetNum;
    private final ExcelAbstractSheetProperties conf;
    private Map<String, ExcelFieldProperties> fieldMap;
    private ExcelArgument argument;
    private final WydExcelCellBaseProcessImpl cellBaseProcess;
//    private final Map<String, Font> fontMap = new HashMap<>();
//    private Map<String, DataFormat> map = new HashMap<>();
//    private Map<String, Short> dataMap = new HashMap<>();
//    private Map<String, CellStyle> cellStyleMap = new HashMap<>();

    public ExcelSheetGenerate(ExcelAbstractSheetProperties conf, Workbook workbook, int sheetNum) {
        super(workbook);
        this.conf = conf;
        argument = new ExcelArgument();
        sheet = getSheet(conf.getSheetName());
        argument = this.conf.getExcelArgument();
        this.conf.build();
        cellBaseProcess = (WydExcelCellBaseProcessImpl) ExcelContextAllContext.getInstance().getProcessMap().get(ExcelContextAllContext.getInstance().PROCESSTYPE_BASE);
        cellBaseProcess.setSheetConf(argument);
        cellBaseProcess.setWorkBook(workbook);
        create();
        cellBaseProcess.setSheet(sheet);
        this.sheetNum = sheetNum;
    }

    public ExcelSheetGenerate(ExcelAbstractSheetProperties conf, Workbook workbook) {
        this(conf, workbook, 0);
    }

    public void setSheetName(String sheetName) {
        this.workbook.setSheetName(sheetNum, sheetName);
    }

    //动态行，动态表头

    /**
     * 切换下一行处理器
     */
    public void nextRowProcess() {
        innerDynamicExcelRowProcess = dynamicRowProcess.dynamicExcelRowProcess.next;
        if (null != innerDynamicExcelRowProcess) {
            fieldMap = innerDynamicExcelRowProcess.getFieldList();
            rowGenerate.setDynamicExcelRowProcess(innerDynamicExcelRowProcess);
        }
    }

    //动态行，动态表头
    private void create() {
        sheetProcess.afterExportProcess(sheet, conf);
        for (ExcelFieldProperties headCell : conf.getCells()) {
            dynamicRowProcess.put(headCell);
        }
        dynamicRowProcess.init();
        innerDynamicExcelRowProcess = dynamicRowProcess.dynamicExcelRowProcess;
        WydExcelDynamicRowValueProcessImpl.DynamicExcelRowProcess dynamicExcelRowProcess = dynamicRowProcess.dynamicExcelRowProcess;
        if (dynamicExcelRowProcess != null) {
            for (Map.Entry<String, ExcelFieldProperties> entry : dynamicExcelRowProcess.getFieldList().entrySet()) {
                ExcelFieldProperties headCell = entry.getValue();
                if (rowGenerate == null) {
                    WydExcelCellFontProcessImpl fontProcess = (WydExcelCellFontProcessImpl) ExcelContextAllContext.getInstance().getProcessMap().get(ExcelContextAllContext.getInstance().PROCESSTYPE_FONT);
                    WydExcelCellFormatProcessImpl formatProcess = (WydExcelCellFormatProcessImpl) ExcelContextAllContext.getInstance().getProcessMap().get(ExcelContextAllContext.getInstance().PROCESSTYPE_DATAFORMAT);
                    formatProcess.setDataFormat(workbook.createDataFormat());
                    rowGenerate = new ExcelRowGenerateRow(conf, sheet, innerDynamicExcelRowProcess, argument, cellBaseProcess, fontProcess, formatProcess);
                }
                rowGenerate.generateCell(entry.getKey(), headCell.getValue(), false);
            }
            dynamicExcelRowProcess.incrementIndex();
        }
        if (null != innerDynamicExcelRowProcess) {
            fieldMap = innerDynamicExcelRowProcess.getFieldList();
        }
    }


    /**
     * push 对象到方法中，
     * 方法会生成map
     *
     * @param value 对象信息 任意类型
     */
    public void generate(Object value) {
        if (null == value || innerDynamicExcelRowProcess == null) {
            return;
        }
        Field[] fields = ExcelContextAllContext.getInstance().putClassMap(value.getClass());
//        innerDynamicExcelRowProcess.getFieldList().forEach();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object fValue = field.get(value);
                String name = field.getName();
                if (null != fValue) {
                    if (value instanceof String) {
                        rowGenerate.generateCell(name, (String) fValue);
                    } else if (value instanceof Double) {
                        rowGenerate.generateCell(name, (Double) fValue);
                    } else if (value instanceof Calendar) {
                        rowGenerate.generateCell(name, (Calendar) fValue);
                    } else if (value instanceof LocalDateTime) {
                        rowGenerate.generateCell(name, (LocalDateTime) fValue);
                    } else if (value instanceof LocalDate) {
                        rowGenerate.generateCell(name, (LocalDate) fValue);
                    } else {
                        rowGenerate.generateCell(name, fValue.toString());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        rowGenerate.completeCurrentRow();
    }

    public ExcelRowGenerateRow getRowGenerator() {
        rowGenerate.currentRow();
        return rowGenerate;
    }


    public void generate(Map<String, Object> map) {
        //此处conf 表示的是 对于 sheet的配置 ,这个地方不能每次都调用，应该单独抽取出去
        //此处conf 表示的是 对于 sheet的配置 ,这个地方不能每次都调用，应该单独抽取出去
        if (null == map || map.isEmpty() || null == innerDynamicExcelRowProcess) {
            return;
        }
        for (Map.Entry<String, Object> e : map.entrySet()) {
            Object value = e.getValue();
            String key = e.getKey();
            if (value instanceof String) {
                rowGenerate.generateCell(key, (String) value);
            } else if (value instanceof Double) {
                rowGenerate.generateCell(key, (Double) value);
            } else if (value instanceof Calendar) {
                rowGenerate.generateCell(key, (Calendar) value);
            } else if (value instanceof LocalDateTime) {
                rowGenerate.generateCell(key, (LocalDateTime) value);
            } else if (value instanceof LocalDate) {
                rowGenerate.generateCell(key, (LocalDate) value);
            } else {
                rowGenerate.generateCell(key, value.toString());
            }
        }
        rowGenerate.completeCurrentRow();
    }


    public void processSheet() {
        sheetProcess.afterExportProcessSheet(sheet, conf);
    }

    public ExcelArgument getArgument() {
        return argument;
    }
}

