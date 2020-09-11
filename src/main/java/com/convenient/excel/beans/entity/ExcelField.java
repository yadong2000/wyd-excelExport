package com.convenient.excel.beans.entity;

import lombok.Data;

@Data
public class ExcelField {
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
    private String cellFormula;
    private String hyperlink;
    private String cellComment;
}
