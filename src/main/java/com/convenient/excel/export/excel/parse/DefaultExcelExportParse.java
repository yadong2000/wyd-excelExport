package com.convenient.excel.export.excel.parse;


import com.convenient.excel.export.StationDiagramOutput;
import com.convenient.excel.export.annotation.*;
import com.convenient.excel.export.excel.*;
import com.convenient.excel.export.generation.ExcelExportGenerate;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.FieldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.IntStream;

public class DefaultExcelExportParse implements ExcelExportPropertyParse {

    // private final Map<String, Class> HEADMAP = new HashMap<>();
    private final Map<String, ExcelExportProperty> PROPERTYMAP = new LinkedHashMap<>();

    private final Map<String, ExcelExportProperty> LIST_PROPERTYMAP = new LinkedHashMap<>();
    private final List<Map<String, String>> LIST_TRANTOR = new ArrayList<>();

    private final Map<String, ExcelExportProperty> HAS_VALUE_PROPERTYMAP = new LinkedHashMap<>();

    // private final Map<String, List<ExcelExportProperty>> rowCellMap = new HashMap<>();

    private ExcelExportClassParse classParse;

    private ExcelRuleCenter ruleCenter;

    public DefaultExcelExportParse() {
        this.classParse = new DefaultExcelExportClassParse();
        this.ruleCenter = new DefaultExcelRuleCenter();
    }

    private String sheetName;

    @Override
    public Map<String, ExcelExportProperty> parseHead(Class clazz) throws NotFoundException {
        if (Objects.isNull(clazz) || Objects.isNull(classParse)) {
            throw new IllegalArgumentException("class or classParse is not allow null");
        }
        DefaultExcelExportClassParse parse = (DefaultExcelExportClassParse) classParse;
        parse.getCtClass(clazz.getName());
        parse.getFields();
        sheetName = classParse.getClassAnnotation(clazz, ExcelISheetFiled.class.getName(), "name");
        Map<String, Integer> fieldIndexMap = parse.getFieldIndexMap();
        Iterator<Map.Entry<String, Integer>> iterator = fieldIndexMap.entrySet().iterator();
        // int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            ExcelExportProperty property = setListFieldExcelExportProperty(next.getKey(), clazz,
                    setExcelExportProperty(next.getKey(), clazz, 1));
            if (property.getHasListField() != null && property.getHasListField()) {
                LIST_PROPERTYMAP.put(next.getKey(), property);
            }
            property.setFieldName(next.getKey());
            PROPERTYMAP.put(next.getKey(), property);

        }

        return PROPERTYMAP;
    }

    @Override
    public Map<String, ExcelExportProperty> parseListField(List list)
            throws NotFoundException, NoSuchFieldException, IllegalAccessException {
        if (list == null || list.isEmpty()) {
            return Collections.emptyMap();
        }
        DefaultExcelExportClassParse parse = (DefaultExcelExportClassParse) classParse;
        Map<String, FieldInfo> fieldListMap = parse.getFieldListMap();
        ExcelExportProperty property = null;
        for (Map.Entry<String, FieldInfo> entry : fieldListMap.entrySet()) {
            String k = entry.getKey();
            property = LIST_PROPERTYMAP.get(k);
        }
        if (!StringUtils.isEmpty(property.getIncludeField())) {
            HAS_VALUE_PROPERTYMAP.put(property.getIncludeField(), PROPERTYMAP.get(property.getIncludeField()));
            PROPERTYMAP.remove(property.getIncludeField());
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            CtField[] subfields = parse.getPool().get(obj.getClass().getName()).getDeclaredFields();
            for (int i1 = 0; i1 < subfields.length; i1++) {
                CtField subfield = subfields[i1];
                parse.getAnnotationsAttribute(subfield);
                Class<?> aClass = obj.getClass();
                Field declaredField = aClass.getDeclaredField(subfield.getName());
                declaredField.setAccessible(true);
                String title = (String) declaredField.get(obj);
                if (title == null || title.trim().equals("")) {
                    continue;
                }
                parse.getAttributeMap().put(title, parse.getAnnotationsAttribute(subfield));
                ExcelExportProperty property1 = setExcelExportProperty(title, aClass, 1);
                property1.setFieldName(subfield.getName());
                if (property1.getHidden() != null && property1.getHidden()) {
                    continue;
                }
                property.setTitle(title);

                HAS_VALUE_PROPERTYMAP.put(title, property1);
            }
        }

        Map<String, ExcelExportProperty> propertyMap = ruleCenter.computeRule(PROPERTYMAP, HAS_VALUE_PROPERTYMAP,
                property);
        return propertyMap;
    }

    private ExcelExportProperty setExcelExportProperty(String key, Class clazz, int type) {
        ExcelExportProperty property = new ExcelExportProperty();
        property.setRowHight(classParse.getAnnotation(clazz, key, ExcelIStyleFiled.class.getName(), "rowHight"));
        property.setHorizontalAlignment(
                classParse.getAnnotation(clazz, key, ExcelIStyleFiled.class.getName(), "horizontalAlignment"));
        property.setVerticalAlignment(
                (classParse.getAnnotation(clazz, key, ExcelIStyleFiled.class.getName(), "verticalAlignment")));
        property.setWrapText(classParse.getAnnotation(clazz, key, ExcelIStyleFiled.class.getName(), "wrapText"));
        property.setColumnWidth(classParse.getAnnotation(clazz, key, ExcelIStyleFiled.class.getName(), "columnWidth"));// 乘以256
        property.setDataFormat(
                classParse.getAnnotation(clazz, key, ExcelIDataFormatFiled.class.getName(), "dataFormat"));
        property.setStartRow(classParse.getAnnotation(clazz, key, ExcelImportFiled.class.getName(), "startRow"));
        property.setEndRow(classParse.getAnnotation(clazz, key, ExcelImportFiled.class.getName(), "endRow"));
        property.setStartCell(classParse.getAnnotation(clazz, key, ExcelImportFiled.class.getName(), "startCell"));
        property.setEndCell(classParse.getAnnotation(clazz, key, ExcelImportFiled.class.getName(), "endCell"));
        property.setTitle(classParse.getAnnotation(clazz, key, ExcelImportFiled.class.getName(), "title"));
        property.setExclue(classParse.getAnnotation(clazz, key, ExcelImportFiled.class.getName(), "exclue"));
        property.setHasValue(classParse.getAnnotation(clazz, key, ExcelImportFiled.class.getName(), "hasValue"));
        property.setIsKey(classParse.getAnnotation(clazz, key, ExcelImportFiled.class.getName(), "isKey"));
        property.setHidden(classParse.getAnnotation(clazz, key, ExcelImportFiled.class.getName(), "hidden"));
        property.setType(type);// 表头
        return property;
    }

    private ExcelExportProperty setListFieldExcelExportProperty(String key, Class clazz, ExcelExportProperty property) {
        DefaultExcelExportClassParse parse = (DefaultExcelExportClassParse) classParse;
        Map<String, FieldInfo> fieldListMap = parse.getFieldListMap();
        FieldInfo fieldInfo = fieldListMap.get(key);
        if (Objects.isNull(fieldInfo)) {
            return property;
        }
        property.setCellDistance(classParse.getAnnotation(clazz, key, ExcelListField.class.getName(), "cellDistance"));
        property.setIncludeField(classParse.getAnnotation(clazz, key, ExcelListField.class.getName(), "includeField"));
        property.setAfterField(classParse.getAnnotation(clazz, key, ExcelListField.class.getName(), "afterField"));
        property.setBeforeField(classParse.getAnnotation(clazz, key, ExcelListField.class.getName(), "beforeField"));
        property.setTitleStartRow(
                classParse.getAnnotation(clazz, key, ExcelListField.class.getName(), "titleStartRow"));// 乘以256
        property.setTitleEndRow(classParse.getAnnotation(clazz, key, ExcelListField.class.getName(), "titleEndRow"));
        property.setStartRow(classParse.getAnnotation(clazz, key, ExcelListField.class.getName(), "startRow"));
        property.setEndRow(classParse.getAnnotation(clazz, key, ExcelListField.class.getName(), "endRow"));
        property.setTotalTitle(classParse.getAnnotation(clazz, key, ExcelListField.class.getName(), "totalTitle"));
        property.setHasListField(true);
        return property;
    }

    @Override
    public ExcelExportProperty parseBody(Object clazz) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        return null;
    }

    public void setRuleCenter(ExcelRuleCenter ruleCenter) {
        this.ruleCenter = ruleCenter;
    }

    public void setClassParse(ExcelExportClassParse classParse) {
        this.classParse = classParse;
    }

    public ExcelExportClassParse getClassParse() {
        return classParse;
    }

    public String getSheetName() {
        return sheetName;
    }

    public ExcelRuleCenter getRuleCenter() {
        return ruleCenter;
    }

    public Map<String, ExcelExportProperty> getHAS_VALUE_PROPERTYMAP() {
        return HAS_VALUE_PROPERTYMAP;
    }

    public static void main(String[] args) throws NotFoundException, NoSuchFieldException, IllegalAccessException, IOException, IOException {
        List<StationDiagramOutput.SubLostPower> lostPowerList = new ArrayList<>();
        StationDiagramOutput.SubLostPower subLostPower = new StationDiagramOutput.SubLostPower();
        subLostPower.setName("封面");
        subLostPower.setPower("23432");
        lostPowerList.add(subLostPower);
        StationDiagramOutput.SubLostPower subLostPower1 = new StationDiagramOutput.SubLostPower();
        subLostPower1.setName("asd大");
        subLostPower1.setPower("990");
        lostPowerList.add(subLostPower1);
        DefaultExcelExportClassParse classParse = new DefaultExcelExportClassParse();
        DefaultExcelExportParse excelExportParse = new DefaultExcelExportParse();
        excelExportParse.setClassParse(classParse);
        excelExportParse.setRuleCenter(new DefaultExcelRuleCenter());
        excelExportParse.parseHead(StationDiagramOutput.class);
        List<StationDiagramOutput> outputList = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            StationDiagramOutput output = new StationDiagramOutput();
            output.setDate("2020-03" + i);
            output.setStationName("王亚东");
            List<StationDiagramOutput.SubLostPower> lostPowerList2 = new ArrayList<>();

            StationDiagramOutput.SubLostPower subLostPower2 = new StationDiagramOutput.SubLostPower();
            subLostPower2.setName("asd大");
            subLostPower2.setPower(new Random().nextDouble() + "");
            StationDiagramOutput.SubLostPower subLostPower3 = new StationDiagramOutput.SubLostPower();
            subLostPower3.setName("封面");
            subLostPower3.setPower(new Random().nextDouble() + "");
            lostPowerList2.add(subLostPower2);
            lostPowerList2.add(subLostPower3);


            output.setLostList(lostPowerList2);
            output.setInternetLostEH(new Random().nextDouble() + "");
            output.setBuyPower(new Random().nextDouble() + "");
            output.setCarbonDioxide(new Random().nextInt(i + 100) + "");
            output.setComPlantPower(new Random().nextInt(i + 100) + "");
            output.setComplantPowerRate(new Random().nextInt(i + 100) + "");
            output.setComPR(new Random().nextInt(i + 100) + "");
            output.setDayPlantUsePower(new Random().nextInt(i + 100) + "");
            output.setDayPowerMaxTime(new Random().nextInt(i + 100) + "");
            output.setDayPowerMaxTime(new Random().nextInt(i + 100) + "");
            output.setEquivalentHours(new Random().nextInt(i + 100) + "");
            output.setGenIntegrated(new Random().nextDouble() + "");
            output.setTotal(new Random().nextDouble() + "");
            System.out.println("row is " + i + " 时间是-->" + output.getDate() + " --》"
                    + lostPowerList2 + "totalLost " + output.getTotal());
            outputList.add(output);
        });
        // Map<String, ExcelExportProperty> propertyMap =
        // excelExportParse.parseListField(lostPowerList);
        //
        ExcelExportGenerate excelExportGenerate = new ExcelExportGenerate(new DefaultExcelExportParse()
                , null);
        ExcelExportGenerate generate = excelExportGenerate.generateHead(StationDiagramOutput.class, lostPowerList);

        generate.generateBody(outputList);
        Workbook workBook = generate.getWorkBook();
        workBook.write(new FileOutputStream(new File("C:\\dev\\" + "eee.xlsx")));
        System.out.println("执行完成");

    }

}
