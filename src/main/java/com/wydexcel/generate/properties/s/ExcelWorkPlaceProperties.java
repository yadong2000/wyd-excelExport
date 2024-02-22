package com.wydexcel.generate.properties.s;


import com.wydexcel.generate.properties.ExcelAbstractSheetProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 * "id":111,
 * "type":0,
 * "map":
 * {
 * "sheet1":
 * {
 * "columnWidth":125,
 * "head":
 * {
 * "rows":
 * {
 * "1":
 * {
 * "cellIndex":1,
 * "key":"标题1"
 * "value":"title",
 * "cellStyle":{}
 * }
 * }
 * },
 * "body":
 * {
 * <p>
 * "data":[
 * {"title":"123", "cellStyle":{}}
 * ,{ "title":"3456", "cellStyle":{}  }
 * <p>
 * ]
 * }
 * <p>
 * }
 * <p>
 * }
 * }
 */
public class ExcelWorkPlaceProperties {

    public static final String VERSION_2007 = "xlsx";
    public static final String VERSION_BEFORE_2007 = "xls";
    public static final String VERSION_LOW_MEMORY = "sxlsx";
    private String id;
    private String type;
    private Integer windowSize;


    private Map<String, ExcelAbstractSheetProperties> map = new HashMap<>();


    public Map<String, ExcelAbstractSheetProperties> getMap() {
        return map;
    }

    public void setMap(Map<String, ExcelAbstractSheetProperties> map) {
        this.map = map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return (type == null || "".equals(type) ? "xlsx" : type);
    }

    public void setType(String type) {
        if (!VERSION_2007.equals(type) && !VERSION_BEFORE_2007.equals(type) && !VERSION_LOW_MEMORY.equals(type)) {
            throw new IllegalArgumentException("the type " + type + " not support");
        }
        this.type = type;
    }

    public Integer getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(Integer windowSize) {
        this.windowSize = windowSize;
    }
}
