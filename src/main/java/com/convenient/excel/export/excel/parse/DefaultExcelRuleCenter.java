package com.convenient.excel.export.excel.parse;


import com.convenient.excel.export.excel.ExcelExportProperty;

import java.util.*;

public class DefaultExcelRuleCenter implements ExcelRuleCenter {

    private final List<ExcelExportProperty> noListFile = new ArrayList<>();

    @Override
    public Map<String, ExcelExportProperty> computeRule(Map<String, ExcelExportProperty> map,
                                                        Map<String, ExcelExportProperty> listFieldmap, ExcelExportProperty property) {
        final Map<String, ExcelExportProperty> allMap = new HashMap<>();
        ExcelExportProperty afterProperty = map.get(property.getAfterField());
        Integer cellDistance = property.getCellDistance();
        int afterlistFieldDistance = cellDistance * listFieldmap.size();// 在动态计算之后偏移的位置
        Map<String, ExcelExportProperty> tail = new LinkedHashMap<>();
        for (Map.Entry<String, ExcelExportProperty> entry : map.entrySet()) {
            String k = entry.getKey();
            ExcelExportProperty v = entry.getValue();
            if (v.getHasListField()) {
                continue;
            }
            Integer endCell = v.getEndCell();
            if (endCell > afterProperty.getEndCell()) {
                tail.put(k, v);
            } else {
                noListFile.add(v);
                allMap.put(k, v);
            }
        }
        map.clear();
        if (listFieldmap != null && !listFieldmap.isEmpty()) {

            int start = afterProperty.getEndCell();
            int totalstart = afterProperty.getEndCell() + 1;
            int totalend = afterProperty.getEndCell() + afterlistFieldDistance;
            int size = noListFile.size();
            for (Map.Entry<String, ExcelExportProperty> entry : listFieldmap.entrySet()) {
                ExcelExportProperty v = entry.getValue();
                v.setStartCell(start + 1);
                v.setEndCell(start + cellDistance);
                start = v.getEndCell();
                noListFile.add(v);
                if (!property.getIncludeField().equals(entry.getKey())) {
                    v.setTitle(entry.getKey());
                }
                allMap.put(entry.getKey(), v);
            }
            ExcelExportProperty excelExportProperty = noListFile.get(size + 1);
            ExcelExportProperty property1 = new ExcelExportProperty();
            property1.setIncludeField(excelExportProperty.getIncludeField());
            property1.setVerticalAlignment(excelExportProperty.getVerticalAlignment());
            property1.setHorizontalAlignment(excelExportProperty.getHorizontalAlignment());
            property1.setColumnWidth(excelExportProperty.getColumnWidth());
            property1.setDataFormat(excelExportProperty.getDataFormat());
            property1.setExclue(excelExportProperty.getExclue());
            property1.setHasListField(excelExportProperty.getHasListField());
            property1.setHasValue(excelExportProperty.getHasValue());
            property1.setRowHight(excelExportProperty.getRowHight());
            property1.setType(excelExportProperty.getType());
            property1.setWrapText(excelExportProperty.getWrapText());
            property1.setTitle(property.getTotalTitle());
            property1.setStartRow(property.getTitleStartRow());
            property1.setEndRow(property.getTitleEndRow());
            property1.setStartCell(totalstart);
            property1.setEndCell(totalend);
            noListFile.add(property1);
            allMap.put(property1.getTitle(), property1);
        }

        int start = afterProperty.getEndCell() + afterlistFieldDistance + 1;
        int end = 0;
        if (tail != null && !tail.isEmpty()) {
            for (Map.Entry<String, ExcelExportProperty> entry : tail.entrySet()) {
                ExcelExportProperty v = entry.getValue();
                if (v.getHasListField()) {
                    continue;
                }
                noListFile.add(v);
                allMap.put(entry.getKey(), v);
                int position = v.getEndCell() - v.getStartCell();
                String rowKey = v.getStartRow() + "-" + v.getEndRow();
                Integer integer = rowMap.get(rowKey);
                if (integer == null) {
                    rowMap.put(rowKey, start);
                }
                integer = rowMap.get(rowKey);
                v.setStartCell(integer);
                v.setEndCell(end = integer + position);
                rowMap.put(rowKey, end + 1);
            }
        }
        return allMap;
    }

    private final Map<String, Integer> rowMap = new HashMap<>();

}
