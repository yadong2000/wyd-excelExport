package com.wydexcel.generate.properties.s;

import com.wydexcel.generate.process.context.ExcelContextAllContext;

public class ExcelFontProperties extends ExcelCellBase {

    private String fontName;//` varchar(50) NOT NULL,
    private Short fontHeight;//` smallint(10) DEFAULT NULL,
    private Short fontHeightPoints;//` smallint(10) DEFAULT NULL,
    private boolean italic;//` tinyint(1) DEFAULT NULL,
    private boolean strikeout;//` tinyint(1) DEFAULT NULL,
    //    private Short color;//` smallint(10) DEFAULT NULL,
    private Short typeOffset;//` smallint(10) DEFAULT NULL,
    private Byte underLine;//` tinyint(10) DEFAULT NULL,
    private Integer charset;//` int(10) DEFAULT NULL,
    private boolean bold;//` tinyint(1) DEFAULT NULL,
    private short fontColor;
    //默认 0 待办header和body同时失效  1 待办header  2表示body
    private int isHeader;

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_FONT;
    }

    private Integer red;
    private Integer green;
    private Integer blue;

    public Integer getRed() {
        return red;
    }

    public int zeroIfnull(Integer integer) {
        if (null == integer) return 0;
        return integer;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }


    public int getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(int isHeader) {
        this.isHeader = isHeader;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Short getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(Short fontHeight) {
        this.fontHeight = fontHeight;
    }

    public Short getFontHeightPoints() {
        return fontHeightPoints;
    }

    public void setFontHeightPoints(Short fontHeightPoints) {
        this.fontHeightPoints = fontHeightPoints;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isStrikeout() {
        return strikeout;
    }

    public void setStrikeout(boolean strikeout) {
        this.strikeout = strikeout;
    }

    public Short getTypeOffset() {
        return typeOffset;
    }

    public void setTypeOffset(Short typeOffset) {
        this.typeOffset = typeOffset;
    }

    public Byte getUnderLine() {
        return underLine;
    }

    public void setUnderLine(Byte underLine) {
        this.underLine = underLine;
    }

    public Integer getCharset() {
        return charset;
    }

    public void setCharset(Integer charset) {
        this.charset = charset;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public short getFontColor() {
        return fontColor;
    }

    public void setFontColor(short fontColor) {
        this.fontColor = fontColor;
    }



}
