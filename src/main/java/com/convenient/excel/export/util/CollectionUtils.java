package com.convenient.excel.export.util;

import java.util.Collection;
import java.util.List;

public class CollectionUtils {
    public static boolean isEmpty(Collection collect) {
        return (collect == null || collect.size() == 0);
    }
}
