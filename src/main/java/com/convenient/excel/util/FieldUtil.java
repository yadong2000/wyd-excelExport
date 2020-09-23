package com.convenient.excel.util;

import java.lang.reflect.Field;

public final class FieldUtil {
    private FieldUtil() {
    }



    public static Field[] getDeclaredFields(Class<?> var0) {
        return var0.getDeclaredFields();
    }
}
