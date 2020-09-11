package com.convenient.excel.beans.entity;

import lombok.Data;

@Data
public class ExcelFont {
  private Long fontId;//` bigint(20) NOT NULL,
  private String fontName;//` varchar(50) NOT NULL,
  private Short fontHeight;//` smallint(10) DEFAULT NULL,
  private Short fontHeightPoints;//` smallint(10) DEFAULT NULL,
  private Boolean italic;//` tinyint(1) DEFAULT NULL,
  private Boolean strikeout;//` tinyint(1) DEFAULT NULL,
  private Short color;//` smallint(10) DEFAULT NULL,
  private Short typeOffset;//` smallint(10) DEFAULT NULL,
  private Short underLine;//` tinyint(10) DEFAULT NULL,
  private Integer charset;//` int(10) DEFAULT NULL,
  private Boolean bold;//` tinyint(1) DEFAULT NULL,
}
