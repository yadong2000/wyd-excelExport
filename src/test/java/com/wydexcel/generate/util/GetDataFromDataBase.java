package com.wydexcel.generate.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GetDataFromDataBase {


    public static List<Map<String, Object>> getMapFromDatabase() {
        List<Map<String, Object>> list = new ArrayList<>();
        getDataBase(900, (i) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "我说怎么回事呢" + i);
            map.put("desc", "我回来了" + i);
            map.put("date", "我的野蛮女友直冲式发型" + i);
            list.add(map);
        });
        return list;
    }

    public static class A {
        private String title;
        private String desc;
        private String date;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static List<A> getObjectFromDatabase() {
        List<A> list = new ArrayList<>();
        getDataBase(900, (i) -> {
            A a = new A();
            a.setTitle("我说怎么回事呢" + i);
            a.setDesc("我回来了" + i);
            a.setDate("我的野蛮女友直冲式发型" + i);
            list.add(a);
        });
        return list;
    }


    public static void getDataBase(int total, Consumer consumer) {
        for (int i = 0; i < total; i++) {
            consumer.accept(i);
        }
    }

}
