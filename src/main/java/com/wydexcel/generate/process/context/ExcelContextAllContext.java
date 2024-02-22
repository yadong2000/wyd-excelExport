package com.wydexcel.generate.process.context;

import com.alibaba.fastjson.JSONObject;
import com.wydexcel.generate.process.Process;
import com.wydexcel.generate.process.*;
import com.wydexcel.generate.process.custom.CustomProcess;
import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;
import com.wydexcel.generate.properties.ExcelPositionProperties;
import com.wydexcel.generate.properties.s.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExcelContextAllContext {

    protected final Process<ExcelFontProperties, Font> cellFontProcess = new WydExcelCellFontProcessImpl();
    protected final Process<ExcelCellBaseProperties, CellStyle> baseProcess = new WydExcelCellBaseProcessImpl();
    protected final Process<ExcelLinkProperties, Hyperlink> linkerProcess = new WydExcelCellLinkerProcessImpl();
    protected final Process<ExcelFormatProperties, Short> formatProcess = new WydExcelCellFormatProcessImpl();
    protected final Process<ExcelCommentProperties, Comment> cellCommentProcess = new WydExcelCellCommentProcessImpl();
    protected final Process<ExcelRichTextCollectionProperties, RichTextString> richTextProcess = new WydExcelRichTextProcessImpl();
    protected final Process<ExcelPositionProperties, CellRangeAddress> mergeProcess = new WydExcelCellMergeProcessImpl();

    public final String PROCESSTYPE_BASE = "base";
    public final String PROCESSTYPE_FONT = "font";
    public final String PROCESSTYPE_LINK = "link";
    public final String PROCESSTYPE_DATAFORMAT = "dataFormat";
    public final String PROCESSTYPE_COMMENT = "comment";
    public final String PROCESSTYPE_RICHTEXT = "richText";
    public final String PROCESSTYPE_MERGE = "merge";

    private final Map<String, ExcelWorkPlaceProperties> propertiesMap = new HashMap<>();
    private final static ExcelContextAllContext allContext = new ExcelContextAllContext();

    public static ExcelContextAllContext getInstance() {
        return allContext;
    }


    private final Map<String, Process> processMap = new HashMap<>();

    public Map<String, Process> getProcessMap() {
        return processMap;
    }

    private ExcelContextAllContext() {
        processMap.put(PROCESSTYPE_FONT, cellFontProcess);
        processMap.put(PROCESSTYPE_BASE, baseProcess);
        processMap.put(PROCESSTYPE_LINK, linkerProcess);
        processMap.put(PROCESSTYPE_DATAFORMAT, formatProcess);
        processMap.put(PROCESSTYPE_COMMENT, cellCommentProcess);
        processMap.put(PROCESSTYPE_RICHTEXT, richTextProcess);
        processMap.put(PROCESSTYPE_MERGE, mergeProcess);
    }

    private Process getProcessByType(String type) {
        return processMap.get(type);
    }


    private final Map<String, ExcelCellBase> usingMap = new HashMap<>();
    private final Map<Class, Field[]> classMap = new ConcurrentHashMap<>();
    private final Map<String, Field[]> classNameMap = new ConcurrentHashMap<>();

    public Map<Class, Field[]> getClassMap() {
        return classMap;
    }

    public Field[] putClassMap(Class clazz) {
        return classMap.computeIfAbsent(clazz, Class::getDeclaredFields);
    }

    public Field[] putClassMap(String clazz) {
        Field[] fields = classNameMap.get(clazz);
        if (null == fields) {
            try {
                Class aClass = Class.forName(clazz);
                fields = aClass.getDeclaredFields();
                classNameMap.put(clazz, fields);
                classNameMap.put(aClass.getSimpleName(), fields);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return fields;
    }


    public Map<String, ExcelCellBase> getUsingMap() {
        return usingMap;
    }

    public void setUsingMap(ExcelCellBase excelBase) {
        if (excelBase.getId() != null && !excelBase.getId().equals("")) {
            if (excelBase.getIsHeader() == 0) {
                usingMap.put(excelBase.getId() + "1", excelBase);
                usingMap.put(excelBase.getId() + "2", excelBase);
                return;
            }
            usingMap.put(excelBase.getId() + excelBase.getIsHeader(), excelBase);
        }
    }


    /**
     * 整体解析
     *
     * @param excelProperties
     * @return
     */
    public ExcelContextAllContext parse(String excelProperties) {
        ExcelWorkPlaceProperties excelWorkPlaceProperties = JSONObject.parseObject(excelProperties, ExcelWorkPlaceProperties.class);
        parse(excelWorkPlaceProperties);
        return this;
    }

    /**
     * 部分解析
     *
     * @param placeProperties
     * @return
     */
    public ExcelContextAllContext parse(ExcelWorkPlaceProperties placeProperties) {
        if (null == placeProperties || null == placeProperties.getId() || "".equals(placeProperties.getId())
                || null == placeProperties.getMap() || placeProperties.getMap().isEmpty()) {
            throw new IllegalArgumentException("excelProperties  is null or excelProperties's id is null or excelProperties's map is null");
        }
        ExcelWorkPlaceProperties oldPlaceProperties = propertiesMap.get(placeProperties.getId());
        if (null != oldPlaceProperties) {
            // 为了多次赋值 将新的值覆盖到原来的map中
            Map<String, ExcelAbstractSheetProperties> map = placeProperties.getMap();
            oldPlaceProperties.getMap().forEach(map::put);
            return this;
        }
        propertiesMap.put(placeProperties.getId(), placeProperties);
        return this;
    }


    /**
     * 部分解析
     *
     * @param clazz
     * @return
     */
    private ExcelParserLambdaBuilder parse(Class clazz) {
        if (null == clazz) {
            throw new IllegalArgumentException("excelProperties  is null or excelProperties's id is null or excelProperties's map is null");
        }
        return new ExcelParserLambdaBuilder<>(clazz);
    }

    private ExcelContext excelContext;

    public ExcelContext parseCompleteById(String id) {
        return excelContext = new ExcelContext(propertiesMap.get(id));
    }

    public ExcelContext parseCompleteById(String id, List<CustomProcess> customProcessList) {
        return excelContext = new ExcelContext(propertiesMap.get(id), customProcessList);
    }

//    public ExcelContext parseComplete(List<CustomProcess> customProcessList) {
//        return excelContext = new ExcelContext(excelWorkPlaceProperties, customProcessList);
//    }
//
//    public ExcelContext parseComplete() {
//        return excelContext = new ExcelContext(excelWorkPlaceProperties);
//    }

//    public ExcelContext parseLambdaComplete() {
//        return null;
//    }

    public void close() {
        excelContext.colse();
        excelContext = null;
    }



}
