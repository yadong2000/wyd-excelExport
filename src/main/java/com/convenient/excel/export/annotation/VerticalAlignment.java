package com.convenient.excel.export.annotation;

public enum VerticalAlignment {
    TOP,
    CENTER,
    BOTTOM,
    JUSTIFY,
    DISTRIBUTED;

    private VerticalAlignment() {
    }

    public short getCode() {
        return (short)this.ordinal();
    }

    public static VerticalAlignment forInt(int code) {
        if (code >= 0 && code < values().length) {
            return values()[code];
        } else {
            throw new IllegalArgumentException("Invalid VerticalAlignment code: " + code);
        }
    }
}
