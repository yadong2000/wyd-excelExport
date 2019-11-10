package com.convenient.excel.export.util;

import com.convenient.excel.export.annotation.ExcelImportFiled;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.IntegerMemberValue;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ExcelSetDataFormatUtils {

    private ClassFile classFile;
    private ClassPool pool;
    private CtClass ctClass;

    /**
     * @param className TestExcelVo.class.getName()
     * @throws NotFoundException
     */
    public ClassFile javassistPool(String className) throws NotFoundException {
        if (classFile == null)
            classFile = javassistCtClass(className).getClassFile();
        return classFile;
    }

    public ExcelSetDataFormatUtils setClassPool(Class clazz) {
        if (pool == null)
            pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(clazz));

        return this;
    }

    public CtClass javassistCtClass(String className) throws NotFoundException {
        if (pool == null)
            pool = ClassPool.getDefault();
        if (ctClass == null) {
//            pool.appendClassPath(new LoaderClassPath(classLoader));
            ctClass = pool.get(className);
        }
        return ctClass;
    }

    /**
     * @param className      TestExcelVo.class.getName()
     * @param annotationName ExcelISheetFiled.class.getName()
     * @return
     * @throws NotFoundException
     */
    public Annotation getAnnotation(String className, String annotationName) throws NotFoundException {
        ClassFile classFile = javassistPool(className);
        AnnotationsAttribute classAttribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
        return classAttribute.getAnnotation(annotationName);
    }

    public Map<Integer, Row> getRowMap(Sheet sheet) {
        CtField[] fields = ctClass.getDeclaredFields();
        HSSFRow row = null;
        for (CtField f : fields) {
            FieldInfo fieldInfo = f.getFieldInfo();
            AnnotationsAttribute attribute = (AnnotationsAttribute) fieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
            if (attribute != null) {
                Annotation importFiled = attribute.getAnnotation(ExcelImportFiled.class.getName());
                IntegerMemberValue startRow = (IntegerMemberValue) importFiled.getMemberValue("startRow");
                IntegerMemberValue endRow = (IntegerMemberValue) importFiled.getMemberValue("endRow");
                Integer integer = startRow.getValue();
                putRow(integer, sheet);
                putRow(endRow.getValue(), sheet);
            }
        }
        return this.concurrentHashMap;
    }


    public void putRow(int rowNum, Sheet sheet) {
        if (getRow(rowNum) == null)
            concurrentHashMap.put(rowNum, sheet.createRow(rowNum));
    }

    public Row getRow(int rowNum) {
        return concurrentHashMap.get(rowNum);
    }

    private final Map<Integer, Row> concurrentHashMap = new ConcurrentHashMap();

    public int getStartFillIndex() {
        List<Integer> collect = concurrentHashMap.keySet().stream().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return collect.size();
        }
        collect.sort(((o1, o2) -> o2 - o1));
        return collect.size();
    }


}
