package cn.wxrwcz.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WxrPropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxrPropsUtil.class);


    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream is = null;
        try {
            //将资源文件加载为流
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            props.load(is);
            if(is==null){
                throw new FileNotFoundException(fileName+"file is not Found");
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("load properties file filure",e);
        } catch (IOException e) {
            LOGGER.error("load properties file filure",e);
        } finally {
            if(is !=null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure",e);
                }
            }
        }
        return props;
    }


    public static String getString(Properties props,String key){
        return getString(props,key,"");
    }


    public static String getString(Properties props,String key,String defaultValue){
        String value = defaultValue;
        if (props.containsKey(key)){
            value = props.getProperty(key);
        }
        return value;
    }


    public static int getInt(Properties props,String key){
        return getInt(props,key,0);
    }


    public static int getInt(Properties props,String key,int defaultValue){
        int value = defaultValue;
        if (props.containsKey(key)){
            value = WxrCastUtil.castInt(props.getProperty(key));
        }
        return value;
    }


    public static boolean getBoolean(Properties props, String key){
        return getBoolean(props,key,false);
    }


    public static boolean getBoolean(Properties props,String key,Boolean defaultValue){
        boolean value = defaultValue;
        if (props.containsKey(key)){
            value = WxrCastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
