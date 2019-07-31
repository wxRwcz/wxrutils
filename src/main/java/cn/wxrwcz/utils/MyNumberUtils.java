package cn.wxrwcz.utils;

import java.math.BigDecimal;

public class MyNumberUtils {
    public MyNumberUtils() {
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
}
