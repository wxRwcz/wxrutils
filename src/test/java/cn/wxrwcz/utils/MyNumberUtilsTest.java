package cn.wxrwcz.utils;


import org.junit.Test;

import java.util.Date;

public class MyNumberUtilsTest {
    @org.junit.Test
    public void isEmptyOrZero() {
        System.out.println(WxrNumberUtils.isEmptyOrZero(1));
    }
    @Test
    public void doulbeToPercent(){
        System.out.println(WxrNumberUtils.doulbeToPercent(0.98,1));
        System.out.println(WxrNumberUtils.doulbeToPercent(0.98,2));
        System.out.println(WxrNumberUtils.doulbeToPercent(0.98,3));
        String today =  WxrDateUtils.dateToStr(new Date());
        System.out.println(today);
        System.out.println(WxrDateUtils.dateToStr(new Date()));
        System.out.println(WxrDateUtils.strToDate("2018-07-23"));
    }
}
