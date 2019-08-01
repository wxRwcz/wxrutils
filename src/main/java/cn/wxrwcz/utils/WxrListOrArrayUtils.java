package cn.wxrwcz.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WxrListOrArrayUtils {
    public static Boolean isEmpty(List list) {
        return list == null ? true : list.size() <= 0;
    }

    public static List strArrayDistinct(String[] strArray) {
        List<String> list = Arrays.asList(strArray);
        return new ArrayList(list);
    }
}
