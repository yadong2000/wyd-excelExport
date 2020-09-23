package com.convenient.excel.util;

import org.springframework.data.relational.core.sql.SqlIdentifier;
//import sun.reflect.misc.FieldUtil;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BeanToColumn<T> {

    private final Map<SqlIdentifier, Object> updates = new LinkedHashMap();
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public Map<SqlIdentifier, Object> getClassField(Object clazz)  {
        Field[] declaredFields = FieldUtil.getDeclaredFields(clazz.getClass());
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                updates.put(SqlIdentifier.quoted(humpToLine2(field.getName())), field.get(clazz));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return updates;
    }

    private String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) throws IllegalAccessException {
        BeanToColumn column = new BeanToColumn();

    }
}
