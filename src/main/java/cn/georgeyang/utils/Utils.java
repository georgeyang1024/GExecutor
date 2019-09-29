package cn.georgeyang.utils;

import java.util.List;
import java.util.Map;

public class Utils {

    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length <= 0;
    }

    public static boolean isNotEmpty(Map obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Map obj) {
        return obj == null || obj.size() <= 0;
    }

    public static boolean isNotEmpty(String obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(String obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

}
