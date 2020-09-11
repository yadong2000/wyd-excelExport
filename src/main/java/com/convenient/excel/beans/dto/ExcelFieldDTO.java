package com.convenient.excel.beans.dto;

import lombok.Data;

@Data
public class ExcelFieldDTO {
    private Long excelSheetId;//` bigint(20) NOT NULL,
    private Long excelFieldId;//` bigint(20) NOT NULL,
    private String excelFieldName;//` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    private String excelFieldTitle;//` varchar(100) NOT NULL,
    private Long userId;//` bigint(20) NOT NULL,
    private Long createTime;//` datetime NOT NULL,
    private Long updateTime;//` datetime NOT NULL,
    private Long updateUserId;//` bigint(20) NOT NULL,
    private int excelStartRowIndex;//` int(10) NOT NULL,
    private int excelEndRowIndex;//` int(10) NOT NULL,
    private int excelStartCellIndex;//` int(10) NOT NULL,
    private int excelEndCellIndex;//` int(10) NOT NULL,
    private byte needMergeRegoin;//` tinyint(1) NOT NULL,
    private String defaultValue;//` varchar(100) DEFAULT NULL,
    private Object value;//` varchar(100) DEFAULT NULL,
    private String cellFormula;
    private String hyperlink;
    private String cellComment;
    ///
    private long excelCellstyleId;
    private Boolean shrinkToFit;
    private Short fillForeGroundColor;
    private Short fillBackgroundColor;
    private String fillPattern;
    private Short bottomBorderColor;
    private Short topBorderColor;
    private Short rightBorderColor;
    private Short leftBorderColor;
    private String borderTop;
    private String borderRight;
    private String borderLeft;
    private String borderBottom;
    private Short indention;
    private Short rotation;
    private String verticalAlignment;
    private Byte wrapTest;
    private String horizontalAlignment;
    private Boolean quotePrefixed;
    private Byte locked;
    private Boolean hidden;
    private Short dataFormat;

    private Short columnWidth;
    //
    private Long fontId;
    private String fontName;//` varchar(50) NOT NULL,
    private Short fontHeight;//` smallint(10) DEFAULT NULL,
    private Short fontHeightPoints;//` smallint(10) DEFAULT NULL,
    private Byte italic;//` tinyint(1) DEFAULT NULL,
    private Byte strikeout;//` tinyint(1) DEFAULT NULL,
    private Short color;//` smallint(10) DEFAULT NULL,
    private Short typeOffset;//` smallint(10) DEFAULT NULL,
    private Byte underLine;//` tinyint(10) DEFAULT NULL,
    private Integer charset;//` int(10) DEFAULT NULL,
    private Byte bold;//` tinyint(1) DEFAULT NULL,
}
