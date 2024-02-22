package com.wydexcel.generate;


import com.wydexcel.generate.process.context.ExcelContext;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.process.context.ExcelExportBuilder;
import com.wydexcel.generate.process.context.ExcelExportSheetBuilder;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import com.wydexcel.generate.properties.s.ExcelCellBaseProperties;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestExcelCellBaseCellStyleByBuilder {

    private static String id = "test_excel_baseStyle_byBuilder";

    @Before
    public void setUp() {
        ExcelContextAllContext.getInstance()
                .parse(ExcelExportBuilder
                        .builder()
                        .id(id)
                        .sheet(ExcelExportSheetBuilder
                                .builder()
                                .name("sheet1")
                                .field(new ExcelFieldProperties("title", "标题", 0, 0, "base_cellstyle"))
                                .field(new ExcelFieldProperties("salary", "工资", 0, 1, "base_cellstyle"))
                                .property(() -> {
                                    ExcelCellBaseProperties cellBaseProperties = new ExcelCellBaseProperties();
                                    cellBaseProperties.setVerticalAlignment(VerticalAlignment.CENTER);
                                    cellBaseProperties.setBorderRight(BorderStyle.DOUBLE);
                                    cellBaseProperties.setId("base_cellstyle");
                                    cellBaseProperties.setAlignment(HorizontalAlignment.CENTER);
                                    cellBaseProperties.setColumnWidth(15);
                                    cellBaseProperties.setIsHeader(2);
                                    cellBaseProperties.setRightBorderColor((short) 12);
                                    cellBaseProperties.setFillBackgroundColor((short) 23);
                                    cellBaseProperties.setFillPattern(FillPatternType.THICK_BACKWARD_DIAG);
                                    cellBaseProperties.setLeftBorderColor((short) 24);
                                    cellBaseProperties.setRightBorderColor((short) 24);
                                    cellBaseProperties.setIsHeader(2);
                                    return cellBaseProperties;
                                })
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
    public void testBuilderAndgetDataFromDbWithBaseCellStyle() {
        ExcelContext excelContext = ExcelContextAllContext.getInstance().parseCompleteById(id); //如果追求效率，这里可以改成异步
        ExcelSheetGenerate sheetGenerator = excelContext.getSheetGenerator("sheet1");
        //根据map生成excel
        List<Demo> dataFromDb = getDataFromDb();
        for (Demo demo : dataFromDb) {
            sheetGenerator.generate(demo);
        }
        excelContext.write("myFirst_object_bulider_WydExcelDemo");//这里不用指定后缀，已经在配置中指定
        excelContext.colse();
    }

    @Test
    public void testBuilderAndgetObjectDataFromDbWithBaseCellStyle() throws ExecutionException, InterruptedException {
        CompletableFuture<ExcelContext> contextCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return ExcelContextAllContext.getInstance().parseCompleteById(id);
        });
        //根据map生成excel
        List<Demo> dataFromDb = getDataFromDb();
        ExcelContext excelContext = contextCompletableFuture.get(); //如果追求效率，这里可以改成异步
        ExcelSheetGenerate sheetGenerator = excelContext.getSheetGenerator("sheet1");

        for (Demo demo : dataFromDb) {
            sheetGenerator
                    .getRowGenerator()
                    .generateCell("salary", demo.getSalary())//or map.get("salary") or other ways
                    .generateCell("title", demo.getTitle())//or map.get("salary") or other ways
                    .completeCurrentRow();
        }
        excelContext.write("myFirst_bulider_WydExcelDemo");//这里不用指定后缀，已经在配置中指定
        excelContext.colse();
    }

    @Test
    public void testBuilderAndgetMapDataFromDbWithBaseCellStyle() {
        ExcelContext excelContext = ExcelContextAllContext.getInstance().parseCompleteById(id); //如果追求效率，这里可以改成异步
        ExcelSheetGenerate sheetGenerator = excelContext.getSheetGenerator("sheet1");
        //根据map生成excel
        List<Map<String, Object>> dataFromDb = getDataFromDataBase();
        for (Map<String, Object> map : dataFromDb) {
            sheetGenerator.generate(map);
        }
        excelContext.write("myFirst_map_bulider_WydExcelDemo");//这里不用指定后缀，已经在配置中指定
        excelContext.colse();
    }


    private static List<Map<String, Object>> getDataFromDataBase() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("salary", Double.valueOf("2300.5677788889"));
        map1.put("title", "Hello WydExcel");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("salary", Double.valueOf("20.5677788889"));
        map2.put("title", "Hello POI");
        return Arrays.asList(map1, map2);
    }


    private static List<Demo> getDataFromDb() {
        Demo demo1 = new Demo();
        demo1.setSalary(Double.parseDouble("2.9876566"));
        demo1.setTitle("Hello WydExcel");
        Demo demo2 = new Demo();
        demo2.setSalary(Double.parseDouble("245.1224367"));
        demo2.setTitle("Hello POI");
        return Arrays.asList(demo1, demo2);
    }

    private static class Demo {
        private String title;
        private Double salary;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Double getSalary() {
            return salary;
        }

        public void setSalary(Double salary) {
            this.salary = salary;
        }
    }
}
