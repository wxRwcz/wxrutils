package cn.wxrwcz.utils;

import cn.wxrwcz.beans.WxrDynamicBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
public class WxrBeanUtils  extends org.springframework.beans.BeanUtils {
    private WxrBeanUtils() {
    }

    /**
     *
     * @param object   旧的对象带值
     * @param addMap   动态需要添加的属性和属性类型
     * @param addValMap  动态需要添加的属性和属性值
     * @return  新的对象
     */
    public static Object dynamicClass(Object object, HashMap addMap, HashMap addValMap) {
        HashMap returnMap = new HashMap();
        HashMap typeMap = new HashMap();


        Class<?> type = object.getClass();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(type);
        } catch (IntrospectionException e) {
            log.error("获取接口信息错误："+e.toString());
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = null;
                try {
                    result = readMethod.invoke(object);
                } catch (IllegalAccessException e) {
                    log.error("非法访问异常："+e.toString());
                } catch (InvocationTargetException e) {
                    log.error("调用目标异常："+e.toString());
                }
                //可以判断为 NULL不赋值
                returnMap.put(propertyName, result);
                typeMap.put(propertyName, descriptor.getPropertyType());
            }
        }

        returnMap.putAll(addValMap);
        typeMap.putAll(addMap);
        //map转换成实体对象
        WxrDynamicBean bean = new WxrDynamicBean(typeMap);
        //赋值
        Set keys = typeMap.keySet();
        for (Iterator it = keys.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            bean.setValue(key, returnMap.get(key));
        }
        Object obj = bean.getObject();
        return obj;
    }
    public static <T> T toBean(Map<String, Object> beanMap, Class<T> valueType) {
        T bean = WxrBeanUtils.newInstance(valueType);
        PropertyDescriptor[] beanPds = getPropertyDescriptors(valueType);
        for (PropertyDescriptor propDescriptor : beanPds) {
            String propName = propDescriptor.getName();
            // 过滤class属性
            if (propName.equals("class")) {
                continue;
            }
            if (beanMap.containsKey(propName)) {
                Method writeMethod = propDescriptor.getWriteMethod();
                if (null == writeMethod) {
                    continue;
                }
                Object value = beanMap.get(propName);
                if (!writeMethod.isAccessible()) {
                    writeMethod.setAccessible(true);
                }
                try {
                    writeMethod.invoke(bean, value);
                } catch (Throwable e) {
                    throw new RuntimeException("Could not set property '" + propName + "' to bean", e);
                }
            }
        }
        return bean;
    }
    public static <T> T newInstance(Class<?> clazz) {
        return (T) instantiateClass(clazz);
    }
    /**
     * 获取Bean的属性
     * @param bean bean
     * @param propertyName 属性名
     * @return 属性值
     */
    public static Object getProperty(Object bean, String propertyName) {
        PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
        if (pd == null) {
            throw new RuntimeException("Could not read property '" + propertyName + "' from bean PropertyDescriptor is null");
        }
        Method readMethod = pd.getReadMethod();
        if (readMethod == null) {
            throw new RuntimeException("Could not read property '" + propertyName + "' from bean readMethod is null");
        }
        if (!readMethod.isAccessible()) {
            readMethod.setAccessible(true);
        }
        try {
            return readMethod.invoke(bean);
        } catch (Throwable ex) {
            throw new RuntimeException("Could not read property '" + propertyName + "' from bean", ex);
        }
    }

    /**
     * 设置Bean属性
     * @param bean bean
     * @param propertyName 属性名
     * @param value 属性值
     */
    public static void setProperty(Object bean, String propertyName, Object value) {
        PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
        if (pd == null) {
            throw new RuntimeException("Could not set property '" + propertyName + "' to bean PropertyDescriptor is null");
        }
        Method writeMethod = pd.getWriteMethod();
        if (writeMethod == null) {
            throw new RuntimeException("Could not set property '" + propertyName + "' to bean writeMethod is null");
        }
        if (!writeMethod.isAccessible()) {
            writeMethod.setAccessible(true);
        }
        try {
            writeMethod.invoke(bean, value);
        } catch (Throwable ex) {
            throw new RuntimeException("Could not set property '" + propertyName + "' to bean", ex);
        }
    }
    public static void main(String[] args){
        Object object =dynamicClass(new Object(),new HashMap(){{
            this.put("key",String.class);
        }},new HashMap(){{
            this.put("key","String");
        }});
        setProperty(object,"key","key");
        log.info(getProperty(object,"key").toString());
    }
    @SuppressWarnings("rawtypes")
    public static Map toMap(Object src) {
        return BeanMap.create(src);
    }
}
