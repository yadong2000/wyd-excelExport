package com.wydexcel.generate.properties.s;


import com.wydexcel.generate.process.WydExcelCellCommentProcessImpl;
import com.wydexcel.generate.process.context.ExcelContextAllContext;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class ExcelCommentProperties extends ExcelCellBase<WydExcelCellCommentProcessImpl> {

    private String value;
    private String author;
    private ExcelRichTextCollectionProperties richText;

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_COMMENT;
    }

    public ExcelRichTextCollectionProperties getRichText() {
        return richText;
    }

    public void setRichText(ExcelRichTextCollectionProperties richText) {
        this.richText = richText;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
