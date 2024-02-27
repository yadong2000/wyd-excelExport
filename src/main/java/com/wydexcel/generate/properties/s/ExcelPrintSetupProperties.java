package com.wydexcel.generate.properties.s;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class ExcelPrintSetupProperties {


    private Short paperSize;

    private Short scale;

    private Short pageStart;

    private Short fitWidth;

    private Short fitHeight;

    private Boolean leftToRight;

    private Boolean landscape;

    private Boolean validSettings;

    private Boolean noColor;

    private Boolean draft;

    private Boolean notes;

    private Boolean noOrientation;

    private Boolean usePage;

    private Short hResolution;

    private Short vResolution;

    private Double headerMargin;

    private Double footerMargin;

    private Short copies;

    public Short getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(Short paperSize) {
        this.paperSize = paperSize;
    }

    public Short getScale() {
        return scale;
    }

    public void setScale(Short scale) {
        this.scale = scale;
    }

    public Short getPageStart() {
        return pageStart;
    }

    public void setPageStart(Short pageStart) {
        this.pageStart = pageStart;
    }

    public Short getFitWidth() {
        return fitWidth;
    }

    public void setFitWidth(Short fitWidth) {
        this.fitWidth = fitWidth;
    }

    public Short getFitHeight() {
        return fitHeight;
    }

    public void setFitHeight(Short fitHeight) {
        this.fitHeight = fitHeight;
    }

    public Boolean getLeftToRight() {
        return leftToRight;
    }

    public void setLeftToRight(Boolean leftToRight) {
        this.leftToRight = leftToRight;
    }

    public Boolean getLandscape() {
        return landscape;
    }

    public void setLandscape(Boolean landscape) {
        this.landscape = landscape;
    }

    public Boolean getValidSettings() {
        return validSettings;
    }

    public void setValidSettings(Boolean validSettings) {
        this.validSettings = validSettings;
    }

    public Boolean getNoColor() {
        return noColor;
    }

    public void setNoColor(Boolean noColor) {
        this.noColor = noColor;
    }

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public Boolean getNotes() {
        return notes;
    }

    public void setNotes(Boolean notes) {
        this.notes = notes;
    }

    public Boolean getNoOrientation() {
        return noOrientation;
    }

    public void setNoOrientation(Boolean noOrientation) {
        this.noOrientation = noOrientation;
    }

    public Boolean getUsePage() {
        return usePage;
    }

    public void setUsePage(Boolean usePage) {
        this.usePage = usePage;
    }

    public Short gethResolution() {
        return hResolution;
    }

    public void sethResolution(Short hResolution) {
        this.hResolution = hResolution;
    }

    public Short getvResolution() {
        return vResolution;
    }

    public void setvResolution(Short vResolution) {
        this.vResolution = vResolution;
    }

    public Double getHeaderMargin() {
        return headerMargin;
    }

    public void setHeaderMargin(Double headerMargin) {
        this.headerMargin = headerMargin;
    }

    public Double getFooterMargin() {
        return footerMargin;
    }

    public void setFooterMargin(Double footerMargin) {
        this.footerMargin = footerMargin;
    }

    public Short getCopies() {
        return copies;
    }

    public void setCopies(Short copies) {
        this.copies = copies;
    }
}
