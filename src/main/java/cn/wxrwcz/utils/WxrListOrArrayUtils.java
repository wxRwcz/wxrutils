package cn.wxrwcz.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: wangcz
 * @CreateDate: 2019/8/1$ 11:30$
 * @Version: 1.0
 */
public class WxrListOrArrayUtils {
    public static Boolean isEmpty(List list) {
        return list == null ? true : list.size() <= 0;
    }

    public static List strArrayDistinct(String[] strArray) {
        List<String> list = Arrays.asList(strArray);
        return new ArrayList(list);
    }
}
