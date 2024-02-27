package com.wydexcel.generate.properties.s;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class ExcelHeaderOrFooterProperties {

    private String left;
    private String center;
    private String right;
    private String type;// type =footer  or  type=header

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (null != type) {
            if (!(type.equals("footer") || type.equals("header"))) {
                throw new IllegalArgumentException("ExcelHeaderOrFooter type must be footer or header ");
            }

        }
        this.type = type;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
