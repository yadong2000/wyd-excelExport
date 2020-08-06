package com.convenient.excel.export.excel;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelGetway {

    private long getwayId;
    private long type;
    private long userId;
    private LocalDateTime createTime;
    private long updateUserId;
    private LocalDateTime updateTime;
    private String parameter;

    private List<ExcelSheet> sheets = new ArrayList<>();

    @Data
    public static class ExcelSheet {
        private long excelSheetId;
        private String excelSheetName;
        private long excelSheetOrder;
        private long userId;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        private long updateUserId;
        List<ExcelField> fields = new ArrayList<>();
        private ExcelSheetStyleConfig sheetStyleConfig;
    }


    @Data
    public static class ExcelField {

        private long excelSheetId;
        private long excelFieldId;
        private String excelFieldName;
        private String excelFieldTitle;
        private long userId;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        private long updateUserId;
        private long excelStartRowIndex;
        private long excelEndRowIndex;
        private long excelStartCellIndex;
        private long excelEndCellIndex;
        private long needMergeRegoin;
        private String defaultValue;
        private ExcelRowConf rowConf;
        private ExcelCellstyleConfig cellstyleConfig;

    }

}
