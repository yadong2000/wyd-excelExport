package com.convenient.excel.beans.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ExcelSheet implements Serializable {
    private Long excelSheetId;//` bigint(20) NOT NULL,
    private String excelSheetName;//` varchar(50) NOT NULL,
    private int excelSheetOrder;//` int(10) NOT NULL DEFAULT '1',
    private Long userId;//` bigint(20) NOT NULL,
    private LocalDateTime createTime;//` datetime NOT NULL,
    private LocalDateTime updateTime;//` datetime NOT NULL,
    private Long updateUserId;//` bigint(20) NOT NULL,
    private Long getwayId;//'//` bigint(20) NOT NULL,
    //style
    private long excelSheetStyleId;
    private long mergeRegion;
    private Short columnWidth;
    private Byte autoSizeColumn;
    private Short columnHidden;
    private Boolean rightToLeft;
    private Integer defaultRowHeight;
    private Boolean verticallyCenter;
    private Boolean horizontallyCenter;
    private Boolean forceFormulaRecalculation;
    private Boolean autoBreaks;
    private Boolean displayGuts;
    private Boolean displayZeros;
    private Boolean fitToPage;
    private Boolean rowSumsBelow;
    private Boolean printGridLines;
    private Boolean rowSumsRight;
    private Boolean printRowAndColumnHeadings;
    private Boolean selected;
    private Short margin;
    private String protectSheet;
    private Integer zoom;
    private String showInPane;//, two int
    private String shiftRows;//,three int
    private String createFreezePane;//four int or two int
    private String createSplitPane;//five int
    private Integer columnBreak;//
    private long columnGroupCollapsed;// one int one boolean
    private long activeCellAddressId;// two int
    private String groupColumn;// two int
    private long ungroupColumn;// two int

}
