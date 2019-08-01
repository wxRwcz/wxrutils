package cn.wxrwcz.utils;

import java.util.Collection;
import java.util.Map;

public class WxrEmptyUtils {

    public static boolean isEmptyCollection(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmptyCollection(Collection<?> collection){
        return !isEmptyCollection(collection);
    }

    public static boolean isEmptyMap(Map<?,?> map){
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmptyMap(Map<?,?> map){
        return !isEmptyMap(map);
    }
    public static boolean isNotEmptyArrays(Object[] array){
        return !isEmptyArrays(array);
    }

    public static boolean isEmptyArrays(Object[] array){
        return array==null||array.length==0;
    }

    public static boolean isEmptyOrZeroNumber(Number num) {
        if (null == num || 0 == num.intValue()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmptyOrZeroNumber(Number num) {
        return !isEmptyOrZeroNumber(num);
    }


    public static Boolean isEmptyString(String text) {
        return null == text || text.trim().length() == 0;
    }
    public static Boolean isNotEmptyString(String text) {
        return !isEmptyString(text);
    }
}
