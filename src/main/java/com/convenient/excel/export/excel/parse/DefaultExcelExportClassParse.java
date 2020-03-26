package com.convenient.excel.export.excel.parse;

import com.convenient.excel.export.annotation.ExcelListField;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultExcelExportClassParse implements ExcelExportClassParse {

    private ClassFile classFile;

    private ClassPool pool;

    private CtClass ctClass;


    public final Map<String, Integer> fieldIndexMap = new LinkedHashMap<>();

    public final Map<String, AnnotationsAttribute> attributeMap = new HashMap<>();

    public final Map<String, FieldInfo> fieldListMap = new HashMap<>();

    private AnnotationsAttribute attributeClass;

    public DefaultExcelExportClassParse() {
        pool = ClassPool.getDefault();
    }

    @Override
    public <T> T getListFieldAnnotation(Class clazz, String annotationName) throws NotFoundException {
        return null;
    }

    @Override
    public <T> T getClassAnnotation(Class clazz, String annotationName, String key) throws NotFoundException {
        if (clazz == null || StringUtils.isBlank(key) || StringUtils.isBlank(annotationName)) {
            return null;
        }
        AnnotationsAttribute attribute = getAttribute(clazz.getName());
        if (attribute != null) {
            Annotation annotation = attribute.getAnnotation(annotationName);
            return getValue(annotation.getMemberValue(key), key);
        }
        return null;
    }

    @Override
    public <T> T getAnnotation(Class clazz, String fieldName, String annotationName, String key) {
        if (clazz == null || StringUtils.isBlank(key) || StringUtils.isBlank(annotationName)
                || StringUtils.isBlank(fieldName)) {
            return null;
        }
        AnnotationsAttribute attribute = attributeMap.get(fieldName);
        if (attribute != null) {
            Annotation annotation = attribute.getAnnotation(annotationName);
            if (annotation != null)
                return getValue(annotation.getMemberValue(key), key);
        }
        return null;
    }

    private <T> T getValue(MemberValue memberValue, String key) {
        if (memberValue == null) {
            return null;
        }
        if (memberValue instanceof IntegerMemberValue) {
            IntegerMemberValue integerMemberValue = (IntegerMemberValue) memberValue;
            Integer value = integerMemberValue.getValue();
            return (T) value;
        } else if (memberValue instanceof ShortMemberValue) {
            ShortMemberValue integerMemberValue = (ShortMemberValue) memberValue;
            Short value = integerMemberValue.getValue();
            return (T) value;
        } else if (memberValue instanceof BooleanMemberValue) {
            BooleanMemberValue integerMemberValue = (BooleanMemberValue) memberValue;
            Boolean value = integerMemberValue.getValue();
            return (T) value;
        } else if (memberValue instanceof StringMemberValue) {
            StringMemberValue integerMemberValue = (StringMemberValue) memberValue;
            String value = integerMemberValue.getValue();
            return (T) value;
        } else if (memberValue instanceof DoubleMemberValue) {
            DoubleMemberValue integerMemberValue = (DoubleMemberValue) memberValue;
            Double value = integerMemberValue.getValue();
            return (T) value;
        } else if (memberValue instanceof LongMemberValue) {
            LongMemberValue integerMemberValue = (LongMemberValue) memberValue;
            Long value = integerMemberValue.getValue();
            return (T) value;
        } else if (memberValue instanceof ArrayMemberValue) {
            ArrayMemberValue integerMemberValue = (ArrayMemberValue) memberValue;
            T[] value = (T[]) integerMemberValue.getValue();
            return (T) value;
        } else if (memberValue instanceof EnumMemberValue) {
            EnumMemberValue integerMemberValue = (EnumMemberValue) memberValue;
            String value = integerMemberValue.getValue();
            if (key.equals("verticalAlignment")) {
                return (T) VerticalAlignment.valueOf(value);
            } else if (key.equals("horizontalAlignment")) {
                return (T) HorizontalAlignment.valueOf(value);
            }
            return (T) value;
        } else if (memberValue instanceof FloatMemberValue) {
            FloatMemberValue integerMemberValue = (FloatMemberValue) memberValue;
            Float value = integerMemberValue.getValue();
            return (T) value;
        }
        return null;
    }

    public AnnotationsAttribute getAttribute(String className) throws NotFoundException {
        ClassFile classFile = javassistPool(className);
        if (attributeClass == null) {
            attributeClass = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
        }
        return attributeClass;
    }

    public ClassFile javassistPool(String className) throws NotFoundException {
        if (classFile == null)
            classFile = javassistCtClass(className).getClassFile();
        return classFile;
    }

    public CtClass javassistCtClass(String className) throws NotFoundException {
        if (pool == null)
            pool = ClassPool.getDefault();
        if (ctClass == null) {
            ctClass = pool.get(className);
        }

        return ctClass;
    }

    public CtClass getCtClass(String className) throws NotFoundException {
        if (ctClass == null) {
            this.ctClass = javassistCtClass(className);
        }
        return this.ctClass;
    }

    public void getFields() {
        CtField[] declaredFields = ctClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            CtField declaredField = declaredFields[i];
            AnnotationsAttribute attribute = getAnnotationsAttribute(declaredField);
            if (attribute == null) {
                continue;
            }
            fieldIndexMap.put(declaredField.getName(), i + 1);
            Annotation annotation = attribute.getAnnotation(ExcelListField.class.getName());
            attributeMap.put(declaredField.getName(), attribute);
            if (annotation != null) {
                fieldListMap.put(declaredField.getName(), declaredField.getFieldInfo());
            }
        }
    }

    public Map<String, Integer> getFieldIndexMap() {
        return fieldIndexMap;
    }

    public Map<String, FieldInfo> getFieldListMap() {
        return fieldListMap;
    }

    public ClassPool getPool() {
        return pool;
    }


    public AnnotationsAttribute getAnnotationsAttribute(CtField declaredField) {
        AnnotationsAttribute attribute = (AnnotationsAttribute) declaredField.getFieldInfo()
                .getAttribute(AnnotationsAttribute.visibleTag);
        return attribute;
    }

    public Map<String, AnnotationsAttribute> getAttributeMap() {
        return attributeMap;
    }


    public <T> T getListField(String fieldName, Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = obj.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        Object o = declaredField.get(obj);
        return (T) o;

    }

}
