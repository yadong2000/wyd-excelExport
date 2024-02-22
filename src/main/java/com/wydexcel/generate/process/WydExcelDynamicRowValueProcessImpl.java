package com.wydexcel.generate.process;


import com.wydexcel.generate.properties.ExcelFieldProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WydExcelDynamicRowValueProcessImpl {
    public DynamicExcelRowProcess dynamicExcelRowProcess = null;

    private TreeMap<Integer, DynamicExcelRowProcess> treeMap = new TreeMap();


    public void put(ExcelFieldProperties excelFieldProperties) {
        DynamicExcelRowProcess dynamicExcelRowProcess = treeMap.get(excelFieldProperties.getExcelStartRowIndex());
        if (null == dynamicExcelRowProcess) {
            dynamicExcelRowProcess = new DynamicExcelRowProcess(excelFieldProperties.getExcelStartRowIndex());
            treeMap.put(excelFieldProperties.getExcelStartRowIndex(), dynamicExcelRowProcess);
            dynamicExcelRowProcess.getFieldList().put(excelFieldProperties.getExcelFieldName(), excelFieldProperties);
            return;
        }
        dynamicExcelRowProcess.getFieldList().put(excelFieldProperties.getExcelFieldName(), excelFieldProperties);

    }

    public void init() {
        DynamicExcelRowProcess tLLL = null;
        for (Map.Entry<Integer, DynamicExcelRowProcess> entry : treeMap.entrySet()) {
            Integer k = entry.getKey();
            DynamicExcelRowProcess v = entry.getValue();
            if (null == v) {
                continue;
            }
            if (null == dynamicExcelRowProcess) {
                tLLL = v;
                dynamicExcelRowProcess = tLLL;
                continue;
            }
            tLLL.next = v;
            v.prev = tLLL;
            tLLL = tLLL.next;
        }


    }


    public static class DynamicExcelRowProcess {

        public DynamicExcelRowProcess() {
        }

        public DynamicExcelRowProcess(int endIndex) {
            this.endIndex = endIndex;
            this.startIndex = endIndex;
        }

        public DynamicExcelRowProcess next;
        public DynamicExcelRowProcess prev;
        private final Map<String, ExcelFieldProperties> fieldList = new HashMap<>();
        private int startIndex;
        private int endIndex;
        private int mergeEndIndex;
        private int mergeStartIndex;

        public Map<String, ExcelFieldProperties> getFieldList() {
            return fieldList;
        }

        public int getStartIndex() {
            return startIndex;
        }


        public int getEndIndex() {
            return endIndex;
        }


        public void incrementIndex() {
            this.startIndex = this.endIndex;
            this.endIndex = this.endIndex + 1;
        }

        public int incrementIndex(int index) {
            this.endIndex = this.endIndex + index;
            return this.endIndex;
        }


        public int getMergeStartIndex() {
            return mergeStartIndex;
        }

        public int getMergeStartIndexWith() {
            this.mergeEndIndex = this.endIndex;
            return mergeStartIndex = this.endIndex;
        }


        public int getMereEndIndex(int index) {
            this.mergeStartIndex = this.mergeEndIndex;
            this.mergeEndIndex = this.mergeEndIndex + index-1;
            return this.mergeEndIndex;
        }
    }

}
