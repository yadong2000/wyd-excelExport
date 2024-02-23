package com.wydexcel.generate;

import com.wydexcel.generate.process.context.ExcelContext;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.process.context.ExcelExportBuilder;
import com.wydexcel.generate.process.context.ExcelExportSheetBuilder;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class TestExcelDataFormatByBuilder extends  Base {


    @Before
    public void setUp() {
        ExcelContextAllContext.getInstance()
                .parse(ExcelExportBuilder
                        .builder()
                        .id("dataFormat_anyThing")
                        .sheet(ExcelExportSheetBuilder
                                .builder()
                                .name("sheet1")
                                .field(new ExcelFieldProperties("title", "标题", 0, 0, "common_for_title_base,title_font_id"))
                                .field(new ExcelFieldProperties("datetime", "当前日期", 0, 1, "common_for_title_base,title_font_id,yyyy-MM-dd HH:mm:ss"))
                                .field(new ExcelFieldProperties("date", "当前时间", 0, 2, "common_for_title_base,title_font_id,yyyy-MM-dd"))
                                .field(new ExcelFieldProperties("money", "金钱", 0, 3, "common_for_title_base,desc_font_id,money-format"))
                                .field(new ExcelFieldProperties("salary", "工资(元)", 0, 4, "common_for_title_base,desc_font_id,salary-format"))
                                .field(new ExcelFieldProperties("dateForLocalDate", "新当前时间", 0, 5, "common_for_title_base111,desc_font_id,yyyy-MM-dd-dateForLocalDate"))
                                .build())
                        .type(ExcelWorkPlaceProperties.VERSION_2007)
                        .build());

    }

    @Test
    public void testBuildDataFormat() {
        ExcelContext dataFormat_anyThing = ExcelContextAllContext.getInstance().parseCompleteById("dataFormat_anyThing");
        ExcelSheetGenerate sheetGenerator = dataFormat_anyThing.getSheetGenerator();
        long start;
        //  datetime，date，money，document，data
        System.out.println(start = System.currentTimeMillis());
        for (int i = 0; i < 2; i++) {
            sheetGenerator
                    .getRowGenerator()
                    .generateCell("title", "测试标题" + i)
                    .generateCell("datetime", LocalDateTime.now())
                    .generateCell("dateForLocalDate", LocalDate.now())
                    .generateCell("money", Double.valueOf("2045.0234324"))
                    .generateCell("salary", Double.valueOf("1900.0234324"))
                    .generateCell("date", new Date())
                    .completeCurrentRow();
        }
        dataFormat_anyThing.write("test_dataFormat_" + System.currentTimeMillis() + "_" + "builder");
        dataFormat_anyThing.colse();
        System.out.println(System.currentTimeMillis() - start);
    }

}
