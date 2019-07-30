package cn.wxrwcz.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @Description: java类作用描述
 * @Author: wangcz
 * @CreateDate: 2019/7/30$ 18:37$
 * @Version: 1.0
 */
public class MyComputerInfoUtils {
    private static Properties props = System.getProperties();
    private static InetAddress addr;

    public MyComputerInfoUtils() {
    }

    public static String getSystemName() {
        return props.getProperty("os.name");
    }

    public static String getgetSystemVersion() {
        return props.getProperty("os.version");
    }

    public static String getComputerName() {
        return addr.getHostName();
    }

    public static String getComputerIpAddress() {
        return addr.getHostAddress();
    }

    static {
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }

    }
}