package com.wydexcel.generate.properties.s;

import com.wydexcel.generate.process.context.ExcelContextAllContext;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.poi.common.usermodel.HyperlinkType;
@Builder
@NoArgsConstructor
public class ExcelLinkProperties extends ExcelCellBase {


    public String linkType;

    public HyperlinkType getHyperlinkType() {
        if(null ==linkType || "".equals(linkType)){
            return HyperlinkType.URL;
        }
        return HyperlinkType.valueOf(linkType);
    }

    @Override
    public String getType() {
        return ExcelContextAllContext.getInstance().PROCESSTYPE_LINK;
    }

    private String link;

    public String getlinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
