package cn.wxrwcz.beans;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
public class WxrDynamicBean {
    private Object object = null;
    private BeanMap beanMap = null;

    public WxrDynamicBean(Map propertyMap) {
        this.object = generateBean(propertyMap);
        this.beanMap = BeanMap.create(this.object);
    }

    private Object generateBean(Map propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        Set keySet = propertyMap.keySet();
        for(Iterator i = keySet.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            generator.addProperty(key, (Class) propertyMap.get(key));
        }
        return generator.create();
    }

    /**
     * 给bean属性赋值
     * @param property 属性名
     * @param value 值
     */
    public void setValue(Object property, Object value) {
        beanMap.put(property, value);
    }

    /**
     * 通过属性名得到属性值
     * @param property 属性名
     * @return 值
     */
    public Object getValue(String property) {
        return beanMap.get(property);
    }

    public Object getObject() {
        return this.object;
    }
}
