package com.convenient.excel.beans.entity;


import lombok.Data;

@Data
public class ExcelCellstyleConfig {

  private long excelCellstyleId;
  private Boolean shrinkToFit;
  private Short fillForeGroundColor;
  private Short fillPattern;
  private Short bottomBorderColor;
  private Short topBorderColor;
  private Short rightBorderColor;
  private Short leftBorderColor;
  private Short borderTop;
  private Short borderRight;
  private Short borderLeft;
  private Short indention;
  private Short rotation;
  private String verticalAlignment;
  private Byte wrapTest;
  private String horizontalAlignment;
  private Boolean quotePrefixed;
  private Boolean locked;
  private Boolean hidden;
  private Short dataFormat;
  private Long fontId;

}
