package com.wydexcel.generate.properties.s;

public class ExcelRichTextProperties  {


    private String text;//要冻结的列数
    private Integer startIndex;//要冻结的行数
    private Integer endIndex;//要冻结的列数 右边的第一例行号
    private ExcelFontProperties  font;//要冻结的行数 下边的第一行行号



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public ExcelFontProperties getFont() {
        return font;
    }

    public void setFont(ExcelFontProperties font) {
        this.font = font;
    }
}
