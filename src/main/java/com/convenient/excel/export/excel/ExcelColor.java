package com.convenient.excel.export.excel;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExcelColor {

  private long excelColorId;
  private String excelColorName;
  private String excelColorValue;
  private long userId;
  private LocalDateTime createTime;
  private long updateUserId;
  private LocalDateTime updateTime;



}
