package com.wydexcel.generate.properties.s;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class ExcelTableProperties {


    private Integer fromCol;
    private Integer toCol;
    private Integer fromRow;
    private Integer toRow;

    private String tableName;
    private String tableDisplayName;
    //     style.setName("TableStyleMedium2");
    //            style.setShowColumnStripes(false);
    //            style.setShowRowStripes(true);
    //            style.setFirstColumn(false);
    //            style.setLastColumn(false);
    //            style.setShowRowStripes(true);
    //            style.setShowColumnStripes(true);
}
