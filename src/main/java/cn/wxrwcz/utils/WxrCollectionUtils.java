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
    public static <T> JSONObject listToJSON(List<T> list, String keyField) {
        JSONObject json = new JSONObject();
        if (list.size() == 0){
            return json;
        }
        list.forEach(obj->{
            modifyJson(json,obj,String.valueOf(WxrBeanUtils.getProperty(obj,keyField)),null);
        });
        return json;
    }
    public static <T> JSONObject listToJSON(List<T> list, String keyField,String valueField) {
        JSONObject json = new JSONObject();
        if (list.size() == 0){
            return json;
        }
        list.forEach(obj->{
            String key = String.valueOf(WxrBeanUtils.getProperty(obj,keyField));
            String val = String.valueOf(WxrBeanUtils.getProperty(obj,valueField));
            modifyJson(json,obj,key,val);
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