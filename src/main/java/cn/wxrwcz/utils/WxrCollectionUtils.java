package cn.wxrwcz.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;
@Slf4j
public class WxrCollectionUtils {

    public static List strArrayDistinct(String[] strArray) {
        List<String> list = Arrays.asList(strArray);
        return new ArrayList(list);
    }
    /**
     * 将 List 集合转化为 Map, 可将 List 对象的任意字段当做 Map 的 key
     * @param list          对象集合
     * @param methodName    获取字段值的方法，比如 "getId"
     * @param <V>           key的类型，比如 Long
     * @param <T>           对象类型，比如 User
     * @return  Map  转换后的对象
     */
    public static <V, T> Map<V, List<T>> listToListMap(List<T> list, String methodName){
        Map<V, List<T>> map = new HashMap<>();
        if (list.size() == 0){
            return map;
        }
        try{
            for (T obj : list) {
                Method method = obj.getClass().getMethod(methodName);
                V key = (V) method.invoke(obj);
                if (map.get(key) == null){
                    List<T> objList = new ArrayList<T>();
                    objList.add(obj);
                    map.put(key, objList);
                }else {
                    map.get(key).add(obj);
                }
            }
        }catch (Exception exception){
            log.error("Error converting List collection to Map");
        }
        return map;
    }
    public static <T> JSONObject listToJSONByField(List<T> list, String keyField) {
        String keyMethod = GET+WxrStringUtils.captureName(keyField);
        return listToJSON(list,keyMethod);
    }
    /**
     * 将 List 集合转化为 Map, 可将 List 对象的任意字段当做 Map 的 key,字段重复的对象，会放到一个list在保存到JSON中
     * @param list          对象集合
     * @param methodName    获取字段值的方法，比如 "getId"
     * @param <T>           对象类型，比如 User
     * @return  JSONObject  转换后的对象
     */
    public static <V,T> JSONObject listToJSON(List<T> list, String methodName){
        JSONObject json = new JSONObject();
        if (list.size() == 0){
            return json;
        }
        list.forEach(obj->{
            try{
                Method method = obj.getClass().getMethod(methodName);
                String key = String.valueOf(method.invoke(obj));
                modifyJson(json,obj,key,null);
            }catch (Exception exception){
                log.error("Error converting List collection to Map");
            }
        });
        return json;
    }
    public static <T> JSONObject listToJSONByField(List<T> list, String keyField,String valueField) {
        String keyMethod = GET+WxrStringUtils.captureName(keyField);
        String valueMethod = GET+WxrStringUtils.captureName(valueField);
        return listToJSON(list,keyMethod,valueMethod);
    }
    private static final String GET = "get";
    public static <T> JSONObject listToJSON(List<T> list, String keyMethod,String valueMethod){
        JSONObject json = new JSONObject();
        if (list.size() == 0){
            return json;
        }
        list.forEach(obj->{
            try{
                Method keymethod = obj.getClass().getMethod(keyMethod);
                Method valMethod = obj.getClass().getMethod(valueMethod);
                String key = String.valueOf(keymethod.invoke(obj));
                String val = String.valueOf(valMethod.invoke(obj));
                modifyJson(json,obj,key,val);
            }catch (Exception exception){
                log.error("Error converting List collection to Map");
            }
        });
        return json;
    }

    private static void modifyJson(JSONObject json,Object obj,String key,String val){
        boolean flag = WxrEmptyUtils.isEmptyString(val);
        if (json.get(key) == null){
            json.put(key, flag?obj:val);
        }else {
            if(json.get(key) instanceof List){
                json.getObject(key,List.class).add(flag?obj:val);
            }else{
                List objList = new ArrayList<>();
                objList.add(json.get(key));
                objList.add(flag?obj:val);
                json.put(key,objList);
            }
        }
    }
}