package com.wydexcel.generate.properties.s;

import com.wydexcel.generate.process.WydExcelCellBaseProcessImpl;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
@Builder
@NoArgsConstructor
public class ExcelCellBaseProperties extends ExcelCellBase<WydExcelCellBaseProcessImpl> {
    //    private String fieldName;//` varchar(50) NOT NULL,ExcelBaseDto
    private String hyperlink;
    private String cellComment;
    private String colorRgb;
    private byte[] rgb;
    private Short fillForegroundColor;
    ///
    private Boolean shrinkToFit;
    private Short fillForeGroundColor;
    private Short fillBackgroundColor;
    private FillPatternType fillPattern;
    //
    private Short bottomBorderColor;
    private Short topBorderColor;
    private Short rightBorderColor;
    private Short leftBorderColor;
    //边框
    private BorderStyle borderTop;
    private BorderStyle borderRight;
    private BorderStyle borderLeft;
    private BorderStyle borderBottom;
    private Short indention;
    private Short rotation;
    private VerticalAlignment verticalAlignment;
    private boolean wrapTest;
    private HorizontalAlignment alignment;
    private Boolean quotePrefixed;
    private boolean locked;
    private Boolean hidden;
    private Boolean autoSizeColumn;
    private Short dataFormat;

    private Integer columnWidth;

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_BASE;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public String getCellComment() {
        return cellComment;
    }

    public void setCellComment(String cellComment) {
        this.cellComment = cellComment;
    }

    public String getColorRgb() {
        return colorRgb;
    }

    public void setColorRgb(String colorRgb) {
        this.colorRgb = colorRgb;
    }

    public byte[] getRgb() {
        return rgb;
    }

    public void setRgb(byte[] rgb) {
        this.rgb = rgb;
    }

    public Short getFillForegroundColor() {
        return fillForegroundColor;
    }

    public void setFillForegroundColor(Short fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor;
    }


    public Boolean getShrinkToFit() {
        return shrinkToFit;
    }

    public void setShrinkToFit(Boolean shrinkToFit) {
        this.shrinkToFit = shrinkToFit;
    }

    public Short getFillForeGroundColor() {
        return fillForeGroundColor;
    }

    public void setFillForeGroundColor(Short fillForeGroundColor) {
        this.fillForeGroundColor = fillForeGroundColor;
    }

    public Short getFillBackgroundColor() {
        return fillBackgroundColor;
    }

    public void setFillBackgroundColor(Short fillBackgroundColor) {
        this.fillBackgroundColor = fillBackgroundColor;
    }

    public FillPatternType getFillPattern() {
        return fillPattern;
    }

    public void setFillPattern(FillPatternType fillPattern) {
        this.fillPattern = fillPattern;
    }

    public Short getBottomBorderColor() {
        return bottomBorderColor;
    }

    public void setBottomBorderColor(Short bottomBorderColor) {
        this.bottomBorderColor = bottomBorderColor;
    }

    public Short getTopBorderColor() {
        return topBorderColor;
    }

    public void setTopBorderColor(Short topBorderColor) {
        this.topBorderColor = topBorderColor;
    }

    public Short getRightBorderColor() {
        return rightBorderColor;
    }

    public void setRightBorderColor(Short rightBorderColor) {
        this.rightBorderColor = rightBorderColor;
    }

    public Short getLeftBorderColor() {
        return leftBorderColor;
    }

    public void setLeftBorderColor(Short leftBorderColor) {
        this.leftBorderColor = leftBorderColor;
    }

    public BorderStyle getBorderTop() {
        return borderTop;
    }

    public void setBorderTop(BorderStyle borderTop) {
        this.borderTop = borderTop;
    }

    public BorderStyle getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(BorderStyle borderRight) {
        this.borderRight = borderRight;
    }

    public BorderStyle getBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(BorderStyle borderLeft) {
        this.borderLeft = borderLeft;
    }

    public BorderStyle getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(BorderStyle borderBottom) {
        this.borderBottom = borderBottom;
    }

    public Short getIndention() {
        return indention;
    }

    public void setIndention(Short indention) {
        this.indention = indention;
    }

    public Short getRotation() {
        return rotation;
    }

    public void setRotation(Short rotation) {
        this.rotation = rotation;
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public boolean isWrapTest() {
        return wrapTest;
    }

    public void setWrapTest(boolean wrapTest) {
        this.wrapTest = wrapTest;
    }

    public HorizontalAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
    }

    public Boolean getQuotePrefixed() {
        return quotePrefixed;
    }

    public void setQuotePrefixed(Boolean quotePrefixed) {
        this.quotePrefixed = quotePrefixed;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Short getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(Short dataFormat) {
        this.dataFormat = dataFormat;
    }

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }


    public Boolean getAutoSizeColumn() {
        return autoSizeColumn;
    }

    public void setAutoSizeColumn(Boolean autoSizeColumn) {
        this.autoSizeColumn = autoSizeColumn;
    }


}
