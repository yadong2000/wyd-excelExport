package com.convenient.excel.export.excel;


import lombok.Data;

@Data
public class ExcelCellstyleConfig extends CommonField {

  private long excelCellstyleId;
  private long shrinkToFit;
  private long fillForeGroundColor;
  private long fillPattern;
  private long bottomBorderColor;
  private long topBorderColor;
  private long rightBorderColor;
  private long leftBorderColor;
  private long borderTop;
  private long borderRight;
  private long borderLeft;
  private long indention;
  private long rotation;
  private String verticalAlignment;
  private boolean wrapTest;
  private String horizontalAlignment;
  private long quotePrefixed;
  private long locked;
  private long hidden;
  private long dataFormat;
  private long font;
  private long fontSize;

}
