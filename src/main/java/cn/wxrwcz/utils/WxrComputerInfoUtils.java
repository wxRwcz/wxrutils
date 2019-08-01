package cn.wxrwcz.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class WxrComputerInfoUtils {
    private static Properties props = System.getProperties();
    private static InetAddress addr;
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
