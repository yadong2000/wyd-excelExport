package com.convenient.excel.export.util;

import com.convenient.excel.export.annotation.ExcelExportBody;
import com.convenient.excel.export.annotation.ExcelExportHead;
import com.convenient.excel.export.annotation.ExcelListField;
import com.convenient.excel.export.generation.ExcelExportGenerate;
import javassist.CtField;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileAlreadyExistsException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class ExcelHeadUtil {

    /**
     * @param attribute
     * @param type      1:head 0:生成列表
     */
    public static ExcelPosition setHead(AnnotationsAttribute attribute, int type, ExcelListFieldUtils.ListField listField, int index, int titleIndex) {
        ExcelPosition excelPosition = new ExcelPosition();
        Annotation importFiled = attribute.getAnnotation(ExcelExportHead.class.getName());
        if (importFiled != null) {
            IntegerMemberValue exclue = (IntegerMemberValue) importFiled.getMemberValue("exclue");
            excelPosition.setLogicType(1);
            if (type == 0) {
                Annotation importFiledBody = attribute.getAnnotation(ExcelExportBody.class.getName());
                if (importFiledBody != null) {
                    importFiled = null;
                    importFiled = importFiledBody;
                }
                exclue = (IntegerMemberValue) importFiled.getMemberValue("exclue");
            }

            IntegerMemberValue startRow = (IntegerMemberValue) importFiled.getMemberValue("startRow");
            IntegerMemberValue endRow = (IntegerMemberValue) importFiled.getMemberValue("endRow");
            IntegerMemberValue startCell = (IntegerMemberValue) importFiled.getMemberValue("startCell");
            IntegerMemberValue endCell = (IntegerMemberValue) importFiled.getMemberValue("endCell");
            StringMemberValue title = (StringMemberValue) importFiled.getMemberValue("title");
            if (endCell != null) {
                excelPosition.setEndCell(endCell.getValue());
            }
            if (startCell != null) {
                excelPosition.setStartCell(startCell.getValue());
            }

            excelPosition.setEndRow(endRow.getValue());

            excelPosition.setStartRow(startRow.getValue());
            excelPosition.setTitle(title.getValue());
            if (exclue != null) {
                excelPosition.setExclue(exclue.getValue());
            }
            excelPosition.setType(type);

            if (listField != null) {
                excelPosition.setEndRow(listField.getEndRow());
                excelPosition.setStartRow(listField.getStartRow());
                excelPosition.setStartCell(index + 1);
                excelPosition.setEndCell(index+ listField.getCellDistance());
                excelPosition.setTitle(title.getValue() + titleIndex);
            }
        } else {
            return null;
        }
        return excelPosition;
    }


    /**
     * 当单元格为一行时，合并单元格
     *
     * @param sheet
     * @param excelPosition
     * @param rowNum
     */
    public static void mergeSheet(Sheet sheet, ExcelPosition excelPosition, int rowNum) {
        if (!(excelPosition.getStartCell().equals(excelPosition.getEndCell()) && excelPosition.getStartRow().equals(excelPosition.getEndRow()))) {
            sheet.addMergedRegion(new CellRangeAddress(excelPosition.getStartRow(), excelPosition.getEndRow(),
                    excelPosition.getStartCell(), excelPosition.getEndCell()));
        }
    }


    public static Object setCellValue(ExcelPosition excelPosition, Object clazz, CtField f) throws NoSuchFieldException, IllegalAccessException {
        if (excelPosition.getType() == 0) {
            Field declaredField = clazz.getClass().getDeclaredField(f.getName());
            declaredField.setAccessible(true);
            Object value = declaredField.get(clazz);
            return value;
        }
        return excelPosition.getTitle();
    }

    public static Row createRow(ExcelPosition excelPosition, ExcelExportGenerate generate, Row row, Sheet sheet) {
        if (excelPosition.getType() == 0) return row;
        return generate.getUtils().getRowMap(sheet, excelPosition).get(excelPosition.getStartRow());
    }


    public static class ExcelPosition {
        private Integer startRow;//= (IntegerMemberValue) importFiled.getMemberValue("startRow");
        private Integer endRow;//= (IntegerMemberValue) importFiled.getMemberValue("endRow");
        private Integer startCell;//= (IntegerMemberValue) importFiled.getMemberValue("startCell");
        private Integer endCell;//= (IntegerMemberValue) importFiled.getMemberValue("endCell");
        private String title;// = (StringMemberValue) importFiled.getMemberValue("title");
        private int exclue;//= (BooleanMemberValue) importFiled.getMemberValue("exclue");
        private Row row;//= (BooleanMemberValue) importFiled.getMemberValue("exclue");
        private Integer type;
        private int logicType;
        private String id;


        public Integer getLogicType() {
            return logicType;
        }

        public void setLogicType(Integer logicType) {
            this.logicType = logicType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getStartRow() {

            return startRow;
        }

        public void setStartRow(Integer startRow) {
            this.startRow = startRow;
        }

        public Integer getEndRow() {
            return endRow;
        }

        public void setEndRow(Integer endRow) {
            this.endRow = endRow;
        }

        public Integer getStartCell() {
            return startCell;
        }

        public void setStartCell(Integer startCell) {
            this.startCell = startCell;
        }

        public Integer getEndCell() {
            return endCell;
        }

        public void setEndCell(Integer endCell) {
            this.endCell = endCell;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getExclue() {
            return exclue;
        }

        public void setExclue(int exclue) {
            this.exclue = exclue;
        }

        public Row getRow() {
            return row;
        }

        public void setRow(Row row) {
            this.row = row;
        }
    }

}
