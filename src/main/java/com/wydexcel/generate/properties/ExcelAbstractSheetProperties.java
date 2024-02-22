package com.wydexcel.generate.properties;


import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.generate.ExcelArgument;
import com.wydexcel.generate.properties.s.*;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class ExcelAbstractSheetProperties implements Serializable {
    private final transient ExcelArgument argument = new ExcelArgument();

    public final transient Map<String, ExcelCommentProperties> commentMap = new HashMap<>();
    public final transient Map<Integer, Integer> columnWidthMap = new HashMap<>();
    public final transient Map<String, ExcelFontProperties> fontMap = new HashMap<>();
    public final transient Map<String, ExcelPositionProperties> positionPropertiesMap = new HashMap<>();
    public final transient Map<String, ExcelCellBaseProperties> baseMap = new HashMap<>();
    public final transient Map<String, ExcelFieldProperties> excelFileMap = new HashMap<>();
    public final transient Map<String, ExcelLinkProperties> linksMap = new HashMap<>();
    public final transient Map<String, ExcelFormatProperties> dataFormatMap = new HashMap<>();
    public final transient Map<String, ExcelRichTextCollectionProperties> richTextMap = new HashMap<>();


    private String sheetName = "sheet";
    private Integer columnWidth;
    private Boolean selected;
    private Boolean autobreaks;
    private int sheetNum;
    private String workbookId;


    private List<ExcelFieldProperties> cells = new ArrayList<>();
    private List<ExcelCellBase> baseProperties = new ArrayList<>();
    private final List<ExcelFreezePaneProperties> freezes = new ArrayList<>();
    private final List<ExcelLinkProperties> links = new ArrayList<>();
    private final List<ExcelCellBaseProperties> bases = new ArrayList<>();
    private final List<ExcelPositionProperties> positionList = new ArrayList<>();
    private final List<ExcelFormatProperties> dataFormat = new ArrayList<>();
    private List<ExcelFontProperties> fonts = new ArrayList<>();
    private final List<ExcelCommentProperties> comments = new ArrayList<>();
    public List<ExcelRichTextCollectionProperties> richTexts = new ArrayList<>();
    private List<ExcelHeaderOrFooterProperties> headerOrFootesr = new ArrayList<>();
    private ExcelPrintSetupProperties printSetup;
    private final ExcelContextAllContext instance = ExcelContextAllContext.getInstance();


    private String getFieldType(String fieldName, String type) {
        return fieldName + type;
    }

    private String getFieldType(String fieldName, int type) {
        return fieldName + type;
    }

    private String getFieldHeaderType(String fieldName) {
        return getFieldType(fieldName, 1);
    }

    private String getFieldBodyType(String fieldName) {
        return getFieldType(fieldName, 2);
    }


    public ExcelPositionProperties getPositionProperties(String key, boolean isBody) {
        int index = 1;
        if (isBody) {
            index = 2;
        }
        return positionPropertiesMap.get(key + index);
    }

    public ExcelCellBaseProperties getBaseMap(String key, boolean isBody) {
        int index = 1;
        if (isBody) {
            index = 2;
        }
        return baseMap.get(key + index);
    }

    public ExcelRichTextCollectionProperties getExcelRichTextCollectionProperties(String key, boolean isBody, String value) {
        ExcelRichTextCollectionProperties excelRichTextCollectionProperties = richTextMap.get(key);
        if (isBody) {
            if (null != excelRichTextCollectionProperties) {
                ExcelRichTextCollectionProperties bobyProperties = new ExcelRichTextCollectionProperties();
                bobyProperties.setText(value);
                List<ExcelRichTextProperties> richTextProperties = bobyProperties.getRichTextProperties();
                bobyProperties.setIsHeader(2);
                bobyProperties.setFieldName(key);
                ExcelRichTextProperties excelRichTextProperties = new ExcelRichTextProperties();
                richTextProperties.add(excelRichTextProperties);
                richTextMap.put(key + isBody, bobyProperties);
                return bobyProperties;
            }

        }
        return excelRichTextCollectionProperties;
    }


    public ExcelRichTextCollectionProperties getExcelRichTextCollectionProperties(String key, boolean isBody) {
        ExcelRichTextCollectionProperties excelRichTextCollectionProperties = richTextMap.get(key);
        if (isBody) {
            if (null != excelRichTextCollectionProperties) {
                ExcelRichTextCollectionProperties bobyProperties = new ExcelRichTextCollectionProperties();
                List<ExcelRichTextProperties> richTextProperties = bobyProperties.getRichTextProperties();
                bobyProperties.setIsHeader(2);
                bobyProperties.setFieldName(key);
                ExcelRichTextProperties excelRichTextProperties = new ExcelRichTextProperties();
                richTextProperties.add(excelRichTextProperties);
                richTextMap.put(key + isBody, bobyProperties);
                return bobyProperties;
            }

        }
        return excelRichTextCollectionProperties;
    }

    public Map<Integer, Integer> getColumnWidthMap() {
        return columnWidthMap;
    }

    public List<ExcelRichTextCollectionProperties> getRichTexts() {
        return richTexts;
    }

    public List<ExcelFormatProperties> getDataFormat() {
        return dataFormat;
    }

    public List<ExcelFontProperties> getFonts() {
        return fonts;
    }

    public Boolean getSelected() {
        return selected;
    }

    public List<ExcelFreezePaneProperties> getFreezes() {
        return freezes;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setFonts(List<ExcelFontProperties> fonts) {
        this.fonts = fonts;
    }

    private void setBaseUsingMap(ExcelCellBase excelBase) {
        if (null == excelBase) {
            return;
        }
        boolean isUsing = null != excelBase.getId() && !"".equals(excelBase.getId());
        excelBase.baseValidate(isUsing);
        if (!isUsing) {
            return;
        }
        ExcelContextAllContext instance = ExcelContextAllContext.getInstance();
        Map<String, ExcelCellBase> usingMap = instance.getUsingMap();
        if (excelBase.getIsHeader() == 0) {
            usingMap.put(getFieldHeaderType(excelBase.getId()), excelBase);
            usingMap.put(getFieldBodyType(excelBase.getId()), excelBase);
            return;
        }
        usingMap.put(getFieldType(excelBase.getId(), excelBase.getIsHeader()), excelBase);
    }

    private void registerProperty(ExcelCellBase k) {
        argument.putExcelPropertity(k.getType(), getFieldHeaderType(k.getFieldName()), k);
        argument.putExcelPropertity(k.getType(), getFieldBodyType(k.getFieldName()), k);
        if (k.getType().equals(instance.PROCESSTYPE_BASE)) {
            ExcelCellBaseProperties cellBaseProperties = (ExcelCellBaseProperties) k;
            if (null != cellBaseProperties.getColumnWidth()) {
                Optional<ExcelFieldProperties> first = cells.stream().filter(cell -> cell.getExcelFieldName().equals(k.getFieldName())).findFirst();
                first.ifPresent(field -> columnWidthMap.put(field.getExcelStartCellIndex(),cellBaseProperties.getColumnWidth()));
            }
        }

    }

    public void buildForList() {

        for (ExcelCellBase baseProperty : baseProperties) {
            if (null == baseProperty) {
                continue;
            }
            baseProperty.baseValidate();
            setBaseUsingMap(baseProperty);
            registerProperty(baseProperty);
        }
        Map<String, ExcelCellBase> usingMap = instance.getUsingMap();
        for (ExcelFieldProperties k : cells) {
            setUsingMap(k.getUsing(), usingMap, (excelBass -> {
                excelBass.setFieldName(k.getExcelFieldName());
                if (excelBass.getType().equals("font")) {
                    registerProperty(excelBass);
                    return;
                }
                if (excelBass.getType().equals("dataFormat")) {
                    registerProperty(excelBass);
                    return;
                }

                if (excelBass.getType().equals("base")) {
                    registerProperty(excelBass);
                    return;
                }
                if (excelBass.getType().equals("merge")) {
                    ExcelPositionProperties temp = (ExcelPositionProperties) excelBass;
                    if (excelBass.getIsHeader() == 0) {
                        ExcelPositionProperties header = temp.copy();
                        header.setIsHeader(1);
                        ExcelPositionProperties body = temp.copy();
                        body.setIsHeader(2);
                        argument.putExcelPropertity(k.getExcelFieldName(), getFieldHeaderType(k.getExcelFieldName()), header);
                        argument.putExcelPropertity(k.getExcelFieldName(), getFieldBodyType(k.getExcelFieldName()), body);
                        return;
                    }
                    argument.putExcelPropertity(k.getExcelFieldName(), getFieldType(k.getExcelFieldName(), excelBass.getType()), temp.copy());
                }
            }));
            excelFileMap.put(k.getExcelFieldName(), k);
        }
    }

    public void build() {
        for (ExcelFontProperties k : fonts) {
            if (null == k) {
                continue;
            }
            k.baseValidate();
            setBaseUsingMap(k);
            registerProperty(k);

        }

        for (ExcelCellBaseProperties k : bases) {
            if (null == k) {
                continue;
            }

            k.baseValidate();
            setBaseUsingMap(k);
            if (null == k.getFieldName() || "".equals(k.getFieldName())) {
                continue;
            }
            if (null != k.getColumnWidth()) {
                Optional<ExcelFieldProperties> first = cells.stream().filter(cell -> cell.getExcelFieldName().equals(k.getFieldName())).findFirst();
                first.ifPresent(field -> columnWidthMap.put(field.getExcelStartCellIndex(), k.getColumnWidth()));
            }

            registerProperty(k);

        }

        for (ExcelLinkProperties k : links) {
            if (null != k) {
                k.baseValidate();
                setBaseUsingMap(k);
                registerProperty(k);
            }

        }

        for (ExcelFormatProperties k : dataFormat) {
            if (null == k) {
                continue;
            }
            k.baseValidate();
            setBaseUsingMap(k);
            registerProperty(k);

        }


        for (ExcelCommentProperties k : comments) {
            setBaseUsingMap(k);
            registerProperty(k);
            k.baseValidate();
        }

        for (ExcelRichTextCollectionProperties richText : richTexts) {
            richText.baseValidate();
            setBaseUsingMap(richText);
            registerProperty(richText);

        }

        for (ExcelPositionProperties positionProperties : positionList) {
            positionProperties.baseValidate();
            setBaseUsingMap(positionProperties);
            registerProperty(positionProperties);

        }
        //每一条数据 就是一行，需要计算开始 和结束位置 以及 对应的样式

        for (Map.Entry<String, ExcelRichTextCollectionProperties> entry : richTextMap.entrySet()) {
            int startIndex = 0;
            int endIndex = 0;
            StringBuilder text = new StringBuilder();
            ExcelRichTextCollectionProperties value = entry.getValue();
            for (ExcelRichTextProperties richTextProperties : value.getRichTextProperties()) {
                if (null == richTextProperties.getText() || "".equals(richTextProperties.getText())) {
                    continue;
                }
                endIndex = startIndex + richTextProperties.getText().length();
                richTextProperties.setStartIndex(startIndex);
                richTextProperties.setEndIndex(endIndex);
                text.append(richTextProperties.getText());
                startIndex = endIndex;
            }
            value.setText(text.toString());
        }

        for (ExcelFieldProperties k : cells) {
            setUsingMap(k.getUsing(), ExcelContextAllContext.getInstance().getUsingMap(), (excelBass -> {
                excelBass.setFieldName(k.getExcelFieldName());
                if (excelBass.getType().equals("font")) {
                    registerProperty(excelBass);
                    return;
                }
                if (excelBass.getType().equals("dataFormat")) {
                    registerProperty(excelBass);
                    return;
                }

                if (excelBass.getType().equals("base")) {
                    registerProperty(excelBass);
                    return;
                }
                if (excelBass.getType().equals("merge")) {
                    ExcelPositionProperties temp = (ExcelPositionProperties) excelBass;
                    if (excelBass.getIsHeader() == 0) {
                        ExcelPositionProperties header = temp.copy();
                        header.setIsHeader(1);
                        ExcelPositionProperties body = temp.copy();
                        body.setIsHeader(2);
                        argument.putExcelPropertity(k.getExcelFieldName(), getFieldHeaderType(k.getExcelFieldName()), header);
                        argument.putExcelPropertity(k.getExcelFieldName(), getFieldBodyType(k.getExcelFieldName()), body);
                        return;
                    }
                    argument.putExcelPropertity(k.getExcelFieldName(), getFieldType(k.getExcelFieldName(), excelBass.getType()), temp.copy());
                }
            }));
            excelFileMap.put(k.getExcelFieldName(), k);
        }


    }


    private void setUsingMap(String using, Map<String, ExcelCellBase> usingMap, Consumer<ExcelCellBase> biConsumer) {
        for (String id : using.split(",")) {
            String fieldHeaderType = getFieldType(id, 1);
            ExcelCellBase excelBase = usingMap.get(fieldHeaderType);
            if (null != excelBase && (null != excelBase.getType()) && !"".equals(excelBase.getType())) {
                biConsumer.accept(excelBase);
            }
            fieldHeaderType = getFieldType(id, 2);
            excelBase = usingMap.get(fieldHeaderType);
            if (null != excelBase && (null != excelBase.getType()) && !"".equals(excelBase.getType())) {
                biConsumer.accept(excelBase);
            }
        }
    }


    public List<ExcelCellBaseProperties> getBases() {
        return bases;
    }

    public List<ExcelLinkProperties> getLinks() {
        return links;
    }


    private List<ExcelPositionProperties> merges = new ArrayList<>();//单元格合并



    public List<ExcelCommentProperties> getComments() {
        return comments;
    }

    public String getSheetName() {
        return (null == sheetName || "".equals(sheetName.trim())) ? "sheet" : sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }


    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    public int getSheetNum() {
        return sheetNum;
    }

    public void setCells(List<ExcelFieldProperties> cells) {
        this.cells = cells;
    }

    public List<ExcelFieldProperties> getCells() {
        return cells;
    }

    public List<ExcelPositionProperties> getMerges() {
        return merges;
    }

    public void setMerges(List<ExcelPositionProperties> merges) {
        this.merges = merges;
    }

    public Boolean getAutobreaks() {
        return autobreaks;
    }

    public void setAutobreaks(Boolean autobreaks) {
        this.autobreaks = autobreaks;
    }

    public List<ExcelHeaderOrFooterProperties> getHeaderOrFootesr() {
        return headerOrFootesr;
    }

    public void setHeaderOrFootesr(List<ExcelHeaderOrFooterProperties> headerOrFootesr) {
        this.headerOrFootesr = headerOrFootesr;
    }

    public ExcelPrintSetupProperties getPrintSetup() {
        return printSetup;
    }

    public void setPrintSetup(ExcelPrintSetupProperties printSetup) {
        this.printSetup = printSetup;
    }

    public List<ExcelPositionProperties> getPositionList() {
        return positionList;
    }


    public void setWorkbookId(String workbookId) {
        this.workbookId = workbookId;
    }

    public String getWorkbookId() {
        return workbookId;
    }

    public List<ExcelCellBase> getBaseProperties() {
        return baseProperties;
    }

    public void setBaseProperties(List<ExcelCellBase> baseProperties) {
        this.baseProperties = baseProperties;
    }

    public ExcelArgument getExcelArgument() {
        return argument;
    }
}
