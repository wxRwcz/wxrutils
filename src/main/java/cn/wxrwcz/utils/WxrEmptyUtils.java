package cn.wxrwcz.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class WxrEmptyUtils {
    private WxrEmptyUtils() {
    }
    /**
     * 判断对象为空
     * @param obj 对象名
     * @return 是否为空
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if ((obj instanceof Collection)) {
            return isEmptyCollection((Collection<?>) obj);
        }
        if ((obj instanceof CharSequence)) {
            return isEmptyCharSequence((CharSequence) obj);
        }
        if(obj instanceof Map){
            return isEmptyMap((Map<?, ?>) obj);
        }
        if(obj instanceof Array){
            return isEmptyArrays((Object[]) obj);
        }
        return false;
    }

    /**
     * 判断对象不为空
     * @param obj 对象名
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

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


    public static Boolean isEmptyCharSequence(CharSequence text) {
        return null == text || text.length() == 0;
    }
    public static Boolean isNotEmptyCharSequence(CharSequence text) {
        return !isEmptyCharSequence(text);
    }
}
