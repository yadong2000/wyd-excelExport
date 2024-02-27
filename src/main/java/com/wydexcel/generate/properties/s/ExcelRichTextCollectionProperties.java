package com.wydexcel.generate.properties.s;

import com.wydexcel.generate.process.context.ExcelContextAllContext;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
public class ExcelRichTextCollectionProperties extends ExcelCellBase {

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_RICHTEXT;
    }

    private String text;
    private List<ExcelRichTextProperties> richTextProperties = new ArrayList<>();


    public List<ExcelRichTextProperties> getRichTextProperties() {
        return richTextProperties;
    }

    public void setRichTextProperties(List<ExcelRichTextProperties> richTextProperties) {
        this.richTextProperties = richTextProperties;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
