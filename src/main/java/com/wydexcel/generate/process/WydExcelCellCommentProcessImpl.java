package com.wydexcel.generate.process;

import com.wydexcel.generate.process.context.ExcelContextAllContext;
import com.wydexcel.generate.properties.generate.ExcelArgument;
import com.wydexcel.generate.properties.s.ExcelCommentProperties;
import com.wydexcel.generate.properties.s.ExcelRichTextCollectionProperties;
import org.apache.poi.ss.usermodel.*;

public class WydExcelCellCommentProcessImpl implements Process<ExcelCommentProperties, Comment> {

    private Workbook workbook;

    @Override
    public void setWorkBook(Workbook workbook) {
        this.workbook = workbook;
    }

    private Sheet sheet;

    @Override
    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    private final WydExcelRichTextProcessImpl wydExcelRichTextProcess = new WydExcelRichTextProcessImpl();

    private CreationHelper creationHelper = null;
    private ExcelArgument sheetConf;

    private Drawing<?> drawing = null;
    private ClientAnchor anchor = null;

    @Override
    public void setSheetConf(ExcelArgument sheetConf) {
        this.sheetConf = sheetConf;
    }

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_COMMENT;
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public void excelPropertySet(Cell cell, String key, boolean isBody) {
        ExcelCommentProperties property = (ExcelCommentProperties) sheetConf.getExcelPropertity("comment", getKey(key, isBody));
        if (null == property) {
            return;
        }
        excelPropertySet(cell, property, isBody);
    }

    public Comment excelPropertySet(Cell cell, ExcelCommentProperties property, boolean isBody) {
        if (null == property) {
            return null;
        }
        if (null == creationHelper) {
            creationHelper = workbook.getCreationHelper();
        }
        if (null == drawing) {
            drawing = sheet.createDrawingPatriarch();
        }
        if (null == anchor) {
            anchor = creationHelper.createClientAnchor();
        }
        Comment comment = drawing.createCellComment(anchor);
        ExcelRichTextCollectionProperties richText = property.getRichText();
        if (null != richText) {
            comment.setString(wydExcelRichTextProcess.process(richText, isBody));
        }
        comment.setAuthor(property.getAuthor());
        cell.setCellComment(comment);
        return comment;
    }
}
