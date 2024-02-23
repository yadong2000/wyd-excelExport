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

import java.util.HashMap;
import java.util.Map;

public class TestExcelFontByBuilder extends Base {
    public TestExcelFontByBuilder() {
        super();
    }

    @Before
    public void setUp() {
        ExcelContextAllContext.getInstance()
                .parse(ExcelExportBuilder
                        .builder()
                        .id("anyThing")
                        .sheet(ExcelExportSheetBuilder
                                .builder()
                                .name("sheet1")
                                .field(new ExcelFieldProperties("desc", "描述", 0, 1, "common_for_desc_base,desc_font_id"))
                                .field(new ExcelFieldProperties("title", "标题", 0, 0, "common_for_title_base,title_font_id"))
                                .build())
                        .type(ExcelWorkPlaceProperties.VERSION_2007)
                        .build());
    }



    /**
     * 测试 excel Font
     * <p>
     * <p>
     * font_metrics.properties
     */
    @Test
    public void testFont() {
        ExcelContext excelContext = ExcelContextAllContext.getInstance().parseCompleteById("anyThing");
        ExcelSheetGenerate excelSheetGenerate = excelContext.getSheetGenerator();
        long start;
        System.out.println(start = System.currentTimeMillis());
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "标题" + i);
            map.put("desc", "描述" + i);
            excelSheetGenerate.generate(map);
        }
        excelContext.write("test_font_" + System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() - start);
    }
}
