package com.wydexcel.generate.common;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

public class ConvenientExcelValidateUtil {


    public static <T> T parseObject(String json, Class c) {
        try {
            T t = JSONObject.parseObject(json, (Type) c);
            return t;
        } catch (Exception e) {
            throw new IllegalArgumentException("转换" + c.getSimpleName() + "出错");
        }

    }

}


