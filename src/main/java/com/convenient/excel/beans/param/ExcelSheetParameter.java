package com.convenient.excel.beans.param;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ExcelSheetParameter implements Serializable {
    private Long excelSheetId;//` bigint(20) NOT NULL,
    private String excelSheetName;//` varchar(50) NOT NULL,
    private int excelSheetOrder;//` int(10) NOT NULL DEFAULT '1',
    private Long userId;//` bigint(20) NOT NULL,
    private LocalDateTime createTime;//` datetime NOT NULL,
    private LocalDateTime updateTime;//` datetime NOT NULL,
    private Long updateUserId;//` bigint(20) NOT NULL,
    private Long getwayId;//'//` bigint(20) NOT NULL,


}
