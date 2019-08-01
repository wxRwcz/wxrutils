package cn.wxrwcz.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WxrNumberUtils {
    public WxrNumberUtils() {
    }

    public static Integer getIntegerForSkipBlanks(Integer number) {
        return number == null ? 0 : number;
    }

    public static BigDecimal getBigDecimalForSkipBlanks(BigDecimal bigDecimal) {
        return bigDecimal == null ? new BigDecimal("0.00") : bigDecimal;
    }

    public static Integer getIntegerIncrement(Integer number) {
        return number == null ? 1 : Integer.valueOf(number + 1);
    }

    public static Integer getIntegerDecrement(Integer number) {
        return number == null ? -1 : Integer.valueOf(number - 1);
    }

    public static Integer getIntegerAddNumer(Integer integer, Integer number) {
        integer = integer == null ? 0 : integer;
        number = number == null ? 0 : number;
        return integer + number;
    }

    public static boolean isEmptyOrZero(Number num) {
        if (null == num || 0 == num.intValue()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmptyOrZero(Number num) {
        return !isEmptyOrZero(num);
    }

    public static double get2Double(double a) {
        DecimalFormat df = new DecimalFormat("0.00");
        return new Double(df.format(a).toString());
    }

    public static String doubleTo2String(double a) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(a).toString();
    }
    private static NumberFormat format;
    public static String doulbeToPercent(double a, int newValue){
        if(format==null){
            synchronized (WxrNumberUtils.class){
                if(format==null){
                    format = NumberFormat.getPercentInstance();
                }
            }
        }
        format.setMinimumFractionDigits(newValue);
        return format.format(a);
    }
    public static String numberFormat(Number a,Number b,int newValue){
        if(null==b||null==a){
            return "0.00%";
        }
        Double aDouble = a.doubleValue();
        Double bDouble = b.doubleValue();
        if(aDouble==0.00||bDouble==0.00){
            return "0.00%";
        }
        return doulbeToPercent( a.doubleValue()/b.doubleValue(),newValue);
    }
    public static String numberToPercentDefault(Number a,Number b){
        return doulbeToPercent( a.doubleValue()/b.doubleValue(),2);
    }
}
