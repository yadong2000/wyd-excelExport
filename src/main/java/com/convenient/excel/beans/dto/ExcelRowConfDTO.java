package com.convenient.excel.beans.dto;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("excel_row_conf")
public class ExcelRowConfDTO {
    @Id
    private Long excelRowId;
    private Long sheetId;
    private Short height;
    private Boolean zeroHeight;


}
