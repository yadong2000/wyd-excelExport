package com.wydexcel.generate;

import com.wydexcel.generate.process.context.ExcelContext;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestExcelCellBaseCellStyleByJSON {

    static String json = "{\n" +
            "    \"id\": \"demo-for-easy\",\n" +
            "    \"map\":\n" +
            "    {\n" +
            "        \"sheet1\":\n" +
            "        {\n" +
            "            \"bases\": [\n" +
            "            {\n" +
            "                \"borderRight\": \"DOUBLE\",\n" +
            "                \"alignment\": \"CENTER\",\n" +
            "                \"verticalAlignment\": \"CENTER\",\n" +
            "                \"columnWidth\": 185,\n" +
            "                \"columnWidth\": 45,\n" +
            "                \"id\": \"base_cellstyle\",\n" +
            "                \"isHeader\": 2,\n" +
            "                \"key\": \"datetime2\",\n" +
            "                \"rightBorderColor\": 10,\n" +
            "                \"type\": \"base\",\n" +
            "                \"borderRight\": \"DASH_DOT\",\n" +
            "                \"fillBackgroundColor\": \"10\",\n" +
            "                \"fillPattern\": \"BRICKS\",\n" +
            "                \"leftBorderColor\": \"10\",\n" +
            "                \"rightBorderColor\": \"51\",\n" +
            "                \"rotation\": \"18\",\n" +
//            "                \"rotation\": \"18\",\n" +
            "                \"fillForegroundColor\": \"18\",\n" +
            "                \"wrapTest\": false\n" +
            "            }],\n" +
            "            \"cells\": [\n" +
            "            {\n" +
            "                \"excelEndCellIndex\": 0,\n" +
            "                \"excelEndRowIndex\": 0,\n" +
            "                \"excelFieldName\": \"title\",\n" +
            "                \"excelStartCellIndex\": 0,\n" +
            "                \"excelStartRowIndex\": 0,\n" +
            "                \"using\": \"base_cellstyle\",\n" +
            "                \"value\": \"标题\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"excelEndCellIndex\": 0,\n" +
            "                \"excelEndRowIndex\": 0,\n" +
            "                \"excelFieldName\": \"salary\",\n" +
            "                \"excelStartCellIndex\": 1,\n" +
            "                \"excelStartRowIndex\": 0,\n" +
            "                \"using\": \"base_cellstyle\",\n" +
            "                \"value\": \"工资\"\n" +
            "            }]\n" +
            "        }\n" +
            "    },\n" +
            "    \"type\": \"xlsx\"\n" +

            "}";

    @Before
    public void setUp() {

    }

    /**
     * 测试 excel Font
     * <p>
     * <p>
     * font_metrics.properties
     */
    @Test
    public void testBuilderAndgetDataFromDbWithBaseCellStyle() {
        ExcelContext excelContext = ExcelContextAllContext.getInstance().parse(json).parseCompleteById("demo-for-easy"); //如果追求效率，这里可以改成异步
        ExcelSheetGenerate sheetGenerator = excelContext.getSheetGenerator("sheet1");
        //根据map生成excel
        List<Demo> dataFromDb = getDataFromDb();
        for (Demo demo : dataFromDb) {
            sheetGenerator.generate(demo);
        }
        excelContext.write("myFirst_object_WydExcelDemo");//这里不用指定后缀，已经在配置中指定
        excelContext.colse();
    }

    @Test
    public void testBuilderAndgetObjectDataFromDbWithBaseCellStyle() throws ExecutionException, InterruptedException {
        CompletableFuture<ExcelContext> contextCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return ExcelContextAllContext.getInstance().parse(json).parseCompleteById("demo-for-easy");
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
        excelContext.write("myFirst_WydExcelDemo");//这里不用指定后缀，已经在配置中指定
        excelContext.colse();
    }

    @Test
    public void testBuilderAndgetMapDataFromDbWithBaseCellStyle() {
        ExcelContext excelContext = ExcelContextAllContext.getInstance().parse(json).parseCompleteById("demo-for-easy"); //如果追求效率，这里可以改成异步
        ExcelSheetGenerate sheetGenerator = excelContext.getSheetGenerator("sheet1");
        //根据map生成excel
        List<Map<String, Object>> dataFromDb = getDataFromDataBase();
        for (Map<String, Object> map : dataFromDb) {
            sheetGenerator.generate(map);
        }
        excelContext.write("myFirst_map_WydExcelDemo");//这里不用指定后缀，已经在配置中指定
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

