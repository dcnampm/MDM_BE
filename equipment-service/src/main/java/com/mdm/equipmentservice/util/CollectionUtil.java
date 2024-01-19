package com.mdm.equipmentservice.util;

import java.util.List;

public class CollectionUtil {


    public static <T> T getLastElement(List<T> list) {
        return list.get(list.size() - 1);
    }
}
