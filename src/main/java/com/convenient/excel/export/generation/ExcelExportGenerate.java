package com.convenient.excel.export.generation;


import com.convenient.excel.export.annotation.*;
import com.convenient.excel.export.constant.ExcelExportDemo;
import com.convenient.excel.export.util.*;
import com.convenient.excel.export.constant.ExcelVersionEnum;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.*;
import org.apache.commons.lang3.StringUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.convenient.excel.export.util.ExcelConvenientFileUtil.CONVENIENT_EXPORT_NAME;
import static com.convenient.excel.export.util.ExcelConvenientFileUtil.CONVENIENT_EXPORT_PATH;
import static com.convenient.excel.export.util.ExcelConvenientFileUtil.addNum;
import static com.convenient.excel.export.util.ExcelHeadUtil.mergeSheet;


public class ExcelExportGenerate<T> {

    private File file;
    private ExcelGetClassUtils utils;
    private String className;
    private Workbook workBook;
    private DataFormat dataFormat;
    private Sheet sheet;
    private String suffix;
    private Long id;
    private CtClass ctClass;
    private AtomicInteger startIndex = new AtomicInteger(0);
    private AtomicInteger endIndex = new AtomicInteger(0);
    private final Map<String, String> annotrationMap = new HashMap();
    private final Map<String, List> multpleMap = new HashMap();
    private int excellIndex = 0;
    private int excellIndexFlag = 0;
    private int titleIndex = 1;
    private List<List> fieldList = new ArrayList<>();


    public ExcelExportGenerate(ExcelGetClassUtils utils) {
        this.utils = utils;
    }

    public ExcelExportGenerate() {
        this.utils = new ExcelGetClassUtils();
        generateXssfWorkook();
    }

    public ExcelExportGenerate(ExcelVersionEnum versionEnum) {
        this.utils = new ExcelGetClassUtils();
        if (versionEnum.equals(ExcelVersionEnum.HSSF)) {
            generateHssfWorkook();
        }

    }


    public ExcelExportGenerate generateXssfWorkook() {
        this.workBook = new XSSFWorkbook();
        this.suffix = ".xlsx";
        return this;
    }

    public ExcelExportGenerate generateHssfWorkook() {
        this.workBook = new HSSFWorkbook();
        this.suffix = ".xls";
        return this;
    }

    public ExcelExportGenerate updateAnnotation(String key, String value, String type) {
        annotrationMap.put(key, value);
        return this;
    }

    public ExcelListFieldUtils.ListField getListField(Annotation value2) {
        IntegerMemberValue cellDistance = (IntegerMemberValue) value2.getMemberValue("cellDistance");
        IntegerMemberValue startRow = (IntegerMemberValue) value2.getMemberValue("startRow");
        IntegerMemberValue endRow = (IntegerMemberValue) value2.getMemberValue("endRow");
        ExcelListFieldUtils.ListField field = new ExcelListFieldUtils.ListField(cellDistance.getValue(), startRow.getValue(), endRow.getValue());
        return field;
    }


    public ExcelExportGenerate setClassPool(Class clazz) {
        this.utils.setClassPool(clazz);
        return this;
    }


    /**
     * @param className TestExcelVo.class.getName()
     * @throws IOException
     * @throws NotFoundException
     */
    public ExcelExportGenerate generateHead(String className) throws
            NotFoundException, NoSuchFieldException, IllegalAccessException {
        if (StringUtils.isBlank(className)) {
            throw new IllegalArgumentException("class name is not empty");
        }
        this.className = className;
//        this.utils.getRowMap(getSheet(),null);
        //遍历字段属性
        CtField[] fields = getCtClass(className).getDeclaredFields();
        int index = 0;
        for (CtField field : fields) {
            List list = null;
            if (index <= fieldList.size() - 1) {
                list = fieldList.get(index);
            }

            Annotation hasListField = hasListField(field, list);
            if (null != hasListField) {
                ExcelListFieldUtils.ListField listField = getListField(hasListField);
                for (int i = 0; i < list.size(); i++) {
                    Object o = list.get(i);
                    CtField[] subfields = ClassPool.getDefault().get(o.getClass().getName()).getDeclaredFields();
                    for (int i1 = 0; i1 < subfields.length; i1++) {
                        set(subfields[i1], 1, null, null, listField);
                    }
                    titleIndex++;
                }
                listField = null;
                index++;
            } else {
                set(field, 1, null, null, null);
            }
        }
        return this;
    }

    public ExcelExportGenerate<T> generateBody(List<T> list) throws NotFoundException
            , IllegalAccessException, NoSuchFieldException {
        this.excellIndex = 0;
        this.excellIndexFlag = 0;
        this.titleIndex = 1;
        int size = list.size();
        this.dataFormat = this.workBook.createDataFormat();
        //创建row,在创建多行
        int startFillIndex = this.utils.getStartFillIndex();

        for (int i = 0; i < size; i++) {
            //这里要注意有多行的时候
            Row row = this.sheet.createRow(startFillIndex + i);
            startIndex.set(startFillIndex + i);
            endIndex.set(startFillIndex + i);


            T t = list.get(i);
            CtClass ctClass = this.utils.getCtClass(t.getClass().getName());
            CtField[] declaredFields = ctClass.getDeclaredFields();
            int index = 0;
            this.excellIndex = 0;
            this.excellIndexFlag=0;
            for (CtField field : declaredFields) {
//                CtField ctField = new CtField(this.ctClass);
                List listfield = null;
                Annotation hasListField = hasListField(field, listfield);
                if (null != hasListField) {
                    Field declaredField = t.getClass().getDeclaredField(field.getName());
                    declaredField.setAccessible(true);
                    listfield = (List) declaredField.get(t);
                    ExcelListFieldUtils.ListField listField = getListField(hasListField);
                    for (int j = 0; j < listfield.size(); j++) {
                        Object o = listfield.get(j);
                        CtField[] subfields = ClassPool.getDefault().get(o.getClass().getName()).getDeclaredFields();
                        for (int i1 = 0; i1 < subfields.length; i1++) {
                            set(subfields[i1], 0, row, (T) o, listField);
                        }
                        titleIndex++;
                    }
                    listField = null;
                    index++;
                } else {
                    set(field, 0, row, t, null);
                }

            }
        }
        return this;
    }

    /**
     * @param field 对应celld的字段
     * @param type  1=生成head 0=生成列表
     * @param row1  对应execl的行
     * @param clazz 对应cell字段的值 ，在generateHead为空
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NotFoundException
     */
    private void set(CtField field, int type, Row row1, T clazz, ExcelListFieldUtils.ListField listField) throws NoSuchFieldException, IllegalAccessException, NotFoundException {
        FieldInfo fieldInfo = field.getFieldInfo();
        AnnotationsAttribute attribute = (AnnotationsAttribute) fieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
        if (attribute == null || fieldInfo == null) {
            return;
        }
        //设置表头
        ExcelHeadUtil.ExcelPosition excelPosition = ExcelHeadUtil.setHead(attribute, type, listField, excellIndex, titleIndex);
        if (excelPosition == null) {
            return;
        }
        //初始化表头用
        Row row = ExcelHeadUtil.createRow(excelPosition, this, row1, getSheet());
        //合并单元格
        if (excelPosition.getType() == 0) {
            excelPosition.setStartRow(this.startIndex.get());
            excelPosition.setEndRow(this.endIndex.get() + excelPosition.getExclue());
        }
        excellIndexFlag = excellIndex;
        excellIndex = excelPosition.getEndCell();
        if (excellIndex < excellIndexFlag) {
            int i = excelPosition.getEndCell() - excelPosition.getStartCell();
            excelPosition.setStartCell(excellIndexFlag + 1);
            excelPosition.setEndCell(excellIndexFlag + i);
            this.excellIndex = excelPosition.getEndCell();
        }
        mergeSheet(getSheet(), excelPosition, row.getRowNum());
        //初始化cellValue的值
        String titleValue = (String) ExcelHeadUtil.setCellValue(excelPosition, clazz, field);

        //文本替换
        if (type == 1) {
            String annotationValue = this.annotrationMap.get(titleValue);
            if (StringUtils.isNotBlank(annotationValue)) {
                titleValue = annotationValue;
                excelPosition.setTitle(annotationValue);
            }
        }
        //创建单元格,一行有很多个单元格
        Cell cell = row.createCell(excelPosition.getStartCell());
        //单元格样式设置
        CellStyle xssfCellStyle = updateExcelStyle(cell, row, attribute, excelPosition);
        //设置单元格值
        cell.setCellValue(titleValue);
        //设置单元格 格式化
        ExcelSetDataFormatUtils.setDataFormat(xssfCellStyle, this, excelPosition, cell, attribute);
    }


    private CellStyle updateExcelStyle(Cell cell, Row row, AnnotationsAttribute attribute, ExcelHeadUtil.ExcelPosition excelPosition) {
        Annotation style = attribute.getAnnotation(ExcelIHeadStyle.class.getName());
        if (excelPosition.getType() == 0) {
            Annotation styleBody = attribute.getAnnotation(ExcelIBodyStyle.class.getName());
            if (styleBody != null) {
                style = styleBody;
            }
        }
        CellStyle cellStyle = this.workBook.createCellStyle();
        if (style == null) return cellStyle;

        ShortMemberValue rowHight = (ShortMemberValue) style.getMemberValue("rowHight");
        if (rowHight != null) {
            row.setHeight(rowHight.getValue());
        }
        EnumMemberValue horizontalAlignment = (EnumMemberValue) style.getMemberValue("horizontalAlignment");
        if (horizontalAlignment == null) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        } else {
            cellStyle.setAlignment(HorizontalAlignment.valueOf(horizontalAlignment.getValue()));
        }
        EnumMemberValue verticalAlignment = (EnumMemberValue) style.getMemberValue("verticalAlignment");
        if (verticalAlignment == null) {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        } else {
            cellStyle.setVerticalAlignment(VerticalAlignment.valueOf(verticalAlignment.getValue()));
        }

        BooleanMemberValue booleanMemberValue = (BooleanMemberValue) style.getMemberValue("wrapText");
        if (booleanMemberValue == null) {
            cellStyle.setWrapText(true);
        } else {
            cellStyle.setWrapText(booleanMemberValue.getValue());
        }
        IntegerMemberValue columnWidth = (IntegerMemberValue) style.getMemberValue("columnWidth");
        if (columnWidth != null) {
            //设置列宽度
            int columnIndex = cell.getColumnIndex();
            sheet.setColumnWidth(columnIndex, columnWidth.getValue() * 256);
            sheet.autoSizeColumn(columnIndex, true);
        }
        cell.setCellStyle(cellStyle);
        return cellStyle;
    }

    public ExcelExportGenerate generateFile(String path, String name) throws IOException {
        this.file = ExcelConvenientFileUtil.fileNoExsitsMake(this.file, path);
        this.file = new File(path + name + this.suffix);
        getWorkBook().write(new FileOutputStream(this.file));
        return this;
    }

    public ExcelExportGenerate generateFile(String pathname) throws IOException {
        this.file = new File(pathname);
        getWorkBook().write(new FileOutputStream(this.file));
        return this;
    }

    public ExcelExportGenerate generateFile() throws IOException {
        ExcelConvenientFileUtil.fileNoExsitsMake(this.file, CONVENIENT_EXPORT_PATH);
        this.file = new File(CONVENIENT_EXPORT_PATH + CONVENIENT_EXPORT_NAME + "_" + this.id + this.suffix);
        getWorkBook().write(new FileOutputStream(this.file));
        return this;
    }


    public ExcelGetClassUtils getUtils() {
        return utils;
    }

    public void setUtils(ExcelGetClassUtils utils) {
        this.utils = utils;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Workbook getWorkBook() {
        return workBook;
    }

    public void setWorkBook(XSSFWorkbook workBook) {
        this.workBook = workBook;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public Sheet getSheet() throws NotFoundException {
        if (sheet == null) {
            return this.sheet = ExcelSheetUtils.setSheet(workBook, utils.getAnnotation(className, ExcelISheet.class.getName()), this);
        }
        return sheet;
    }

    public CtClass getCtClass(String className) throws NotFoundException {
        if (this.ctClass == null) {
            CtClass ct = utils.getCtClass(className);
            this.ctClass = ct;
        }
        return ctClass;
    }


    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public static void main(String[] args) throws IOException, NotFoundException, InvocationTargetException, NoSuchMethodException, CannotCompileException, InstantiationException, IllegalAccessException, NoSuchFieldException {

    }

    public ExcelExportGenerate generateFileName() throws IOException {
        Long aLong = addNum();
        this.id = aLong;
        return this;
    }


    public ExcelExportGenerate generateListField(List pvList) {
        fieldList.add(pvList);
        return this;
    }


    public Annotation hasListField(CtField ctField, List list) {
//        if (CollectionUtils.isEmpty(list)) {
//            return null;
//        }
        FieldInfo fieldInfo = ctField.getFieldInfo();
        if (fieldInfo == null) {
            return null;
        }
        AnnotationsAttribute attribute = (AnnotationsAttribute) fieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
        if (attribute == null || fieldInfo == null) {
            return null;
        }
        Annotation value2 = attribute.getAnnotation(ExcelListField.class.getName());
        if (value2 == null) {
            return null;
        }

        return value2;
    }


}
