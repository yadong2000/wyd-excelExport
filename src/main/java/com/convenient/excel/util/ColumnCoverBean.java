package com.convenient.excel.util;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.convenient.excel.util.ControllerUtil.UNDERLINE;

public class ColumnCoverBean<T> {
    public static BiFunction<Row, RowMetadata, Map<String, Object>> COVER_COLUMN_TO_MAP_CAMEL = (row, metadata) -> {
        Collection<String> columnNames = metadata.getColumnNames();
        Map<String, Object> map = new HashMap<>();
        columnNames.forEach((column) -> map.put(humpToLine(column), row.get(column)));
        return map;
    };
    public BiFunction<Map<String, Object>, Class, T> COVER_MAP_TO_BEAN = (map, clazz) -> {
        return install(map, clazz);
    };


    public static String humpToLine(String param) {
        if (param==null||"".equals(param.trim())){
            return "";
        }
        StringBuilder sb=new StringBuilder(param);
        Matcher mc= Pattern.compile(UNDERLINE).matcher(param);
        int i=0;
        while (mc.find()){
            int position=mc.end()-(i++);
            String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
        }
        return sb.toString();
    }

    public T install(Map<String, Object> map, Class zlass) {
        getClassField(zlass);
        Object obj = null;
        try {
            obj = zlass.newInstance();
            for (Map.Entry<String, Field> entry : fields.entrySet()) {
                Field v = entry.getValue();
                if(map.get(v.getName())==null){
                    continue;
                }
                v.setAccessible(true);
                v.set(obj, map.get(v.getName()));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    private Map<String, Field> fields = new HashMap<>();

    private void getClassField(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            fields.put(field.getName(), field);
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            getClassField(superClass);
        }
    }

    public static void main(String[] args) {
        System.out.println(humpToLine("a_b"));
    }
}
