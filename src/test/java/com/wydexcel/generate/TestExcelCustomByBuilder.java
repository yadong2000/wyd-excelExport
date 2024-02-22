package com.wydexcel.generate;


import com.wydexcel.generate.process.context.ExcelContext;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.process.context.ExcelExportBuilder;
import com.wydexcel.generate.process.context.ExcelExportSheetBuilder;
import com.wydexcel.generate.process.custom.CustomProcess;
import com.wydexcel.generate.properties.ExcelFieldProperties;
import com.wydexcel.generate.properties.generate.ExcelRowGenerateRow;
import com.wydexcel.generate.properties.generate.ExcelSheetGenerate;
import com.wydexcel.generate.properties.s.ExcelWorkPlaceProperties;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestExcelCustomByBuilder extends Base {
    public TestExcelCustomByBuilder() {
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

    public class MyCustomProcess implements CustomProcess<Double> {
        private Workbook workbook;
        private Sheet sheet;
        CreationHelper creationHelper = null;
        ClientAnchor clientAnchor = null;
        Drawing<?> drawing = null;
        private Cell cell;

        @Override
        public void workBook(Workbook workbook) {
            this.workbook = workbook;
            creationHelper = workbook.getCreationHelper();
            clientAnchor = creationHelper.createClientAnchor();
        }

        @Override
        public void spreadSheet(Sheet sheet) {
            this.sheet = sheet;
            drawing = sheet.createDrawingPatriarch();
        }

        @Override
        public void otherInfo(Cell cell) {
            this.cell = cell;
        }

        @Override
        public String cellName() {
            return "title";
        }


        @Override
        public CustomProcess processData(Double d) {
            System.out.println("=================================" + d);
            return this;
        }

        @Override
        public void processLogic() {

            Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
            hyperlink.setAddress("http://www.baidu.com");
            cell.setHyperlink(hyperlink);
            XSSFRichTextString rt = new XSSFRichTextString("sexy girl");
            rt.applyFont((short) 13);
            Comment comment = drawing.createCellComment(clientAnchor);
            comment.setString(rt);
            comment.setAuthor("王志霞");
            cell.setCellComment(comment);
            cell.setHyperlink(hyperlink);
        }

        @Override
        public String sheetName() {
            return "sheet1";
        }

    }

    @Test
    public void testFont() {
        MyCustomProcess myCustomProcess = new MyCustomProcess();
        ExcelContext excelContext = ExcelContextAllContext.getInstance().parseCompleteById("anyThing", Arrays.asList(myCustomProcess));
        ExcelSheetGenerate excelSheetGenerate = excelContext.getSheetGenerator();
        long start;
        System.out.println(start = System.currentTimeMillis());
        for (int i = 0; i < 2; i++) {
            ExcelRowGenerateRow rowGenerator = excelSheetGenerate
                    .getRowGenerator();
            if (i == 1) {
                rowGenerator.
                        generateRich("title", "1212")
                        .generateRich("desc", "1212");
            } else {
                rowGenerator.
                        generateCell("title", 1212d, myCustomProcess.processData(Double.parseDouble("12232")))
                        .generateCell("desc", 2121d, myCustomProcess.processData(Double.parseDouble("343434")));
            }
            rowGenerator.completeCurrentRow();


        }
        excelContext.write("test_builder_" + System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() - start);
    }
}
