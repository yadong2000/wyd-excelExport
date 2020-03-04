package com.convenient.excel.export.util;

import com.convenient.excel.export.annotation.ExcelExportHead;
import com.convenient.excel.export.annotation.ExcelListField;
import com.convenient.excel.export.annotation.ListField;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelListFieldUtils {

    public static class ListField {
        public ListField(/*int startIndex,*/ int cellDistance, int startRow, int endRow) {
            this.cellDistance = cellDistance;
            this.startRow = startRow;
            this.endRow = endRow;

        }

        private final int cellDistance;
        private final int startRow;
        private final int endRow;


        public int getCellDistance() {
            return cellDistance;
        }


        public int getStartRow() {
            return startRow;
        }

        public int getEndRow() {
            return endRow;
        }
    }


//    public static Map<String, ListField> setListField(CtField ctField, List listField) throws CannotCompileException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
//
//        Map<String, ListField> map = new HashMap<>();
//        FieldInfo fieldInfo = ctField.getFieldInfo();
//        if (fieldInfo == null) {
//            return map;
//        }
//        AnnotationsAttribute attribute = (AnnotationsAttribute) fieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
//        if (attribute == null || fieldInfo == null) {
//            return map;
//        }
//        Annotation value2 = attribute.getAnnotation(ExcelListField.class.getName());
//        if (value2 == null) {
//            return map;
//        }
//        for (int i = 0; i < listField.size(); i++) {
//            Object o = listField.get(i);
//
//        }
//        return setListField(null);
//    }

//    public static Map<String, ListField> setListField(String className, ExcelGetClassUtils utils) throws NotFoundException {
//        Map<String, ListField> map = new HashMap<>();
//        Annotation annotation = utils.getAnnotation(className, ListField.class.getName());
//        if (annotation == null) {
//            return map;
//        }
//        ArrayMemberValue value = (ArrayMemberValue) annotation.getMemberValue("value");
//        return setListField(value);
//    }


    public static ListField getListField(Annotation value2) {
        IntegerMemberValue cellDistance = (IntegerMemberValue) value2.getMemberValue("cellDistance");
        IntegerMemberValue startRow = (IntegerMemberValue) value2.getMemberValue("startRow");
        IntegerMemberValue endRow = (IntegerMemberValue) value2.getMemberValue("endRow");
        ListField field = new ListField(cellDistance.getValue(), startRow.getValue(), endRow.getValue());
        return field;
    }
}
