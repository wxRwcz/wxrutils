package cn.wxrwcz.utils;


import cn.wxrwcz.utils.type.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtils {
    private MyDateUtils() {
    }

    public static Date getYesterdayTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -1);
        return cal.getTime();
    }

    public static Date getAssignZeroTime(Date date, Integer day) {
        Calendar cal = Calendar.getInstance();
        if (date == null) {
            date = new Date();
        }

        cal.setTime(date);
        cal.add(5, day == null ? 0 : day);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date addAssignTime(Date date, Integer day) {
        Calendar cal = Calendar.getInstance();
        if (date == null) {
            date = new Date();
        }

        cal.setTime(date);
        cal.add(5, day == null ? 0 : day);
        return cal.getTime();
    }

    public static Date getAssignZeroTime(Date date) {
        return getAssignZeroTime(date, (Integer)null);
    }

    public static Date getTodayZeroTime() {
        return getAssignZeroTime(new Date());
    }

    public static Date addAssignTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(1, year == null ? 0 : year);
        cal.add(2, month == null ? 0 : month);
        cal.add(5, day == null ? 0 : day);
        cal.add(11, hour == null ? 0 : hour);
        cal.add(12, minute == null ? 0 : minute);
        cal.add(13, second == null ? 0 : second);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date addAssignTime(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return addAssignTime(year, month, day, hour, minute, (Integer)null);
    }

    public static Date addAssignTime(Integer year, Integer month, Integer day, Integer hour) {
        return addAssignTime(year, month, day, hour, (Integer)null, (Integer)null);
    }

    public static Date addAssignTime(Integer year, Integer month, Integer day) {
        return addAssignTime(year, month, day, (Integer)null, (Integer)null, (Integer)null);
    }

    public static Date addAssignTime(Integer year, Integer month) {
        return addAssignTime(year, month, (Integer)null, (Integer)null, (Integer)null, (Integer)null);
    }

    public static Date addAssignTimeYear(Integer year) {
        return addAssignTime(year, (Integer)null, (Integer)null, (Integer)null, (Integer)null, (Integer)null);
    }

    public static Date addAssignTimeMonth(Integer month) {
        return addAssignTime((Integer)null, month, (Integer)null, (Integer)null, (Integer)null, (Integer)null);
    }

    public static Date addAssignTimeDay(Integer day) {
        return addAssignTime((Integer)null, (Integer)null, day, (Integer)null, (Integer)null, (Integer)null);
    }

    public static Date addAssignTimeHour(Integer hour) {
        return addAssignTime((Integer)null, (Integer)null, (Integer)null, hour, (Integer)null, (Integer)null);
    }

    public static Date addAssignTimeMinute(Integer minute) {
        return addAssignTime((Integer)null, (Integer)null, (Integer)null, (Integer)null, minute, (Integer)null);
    }

    public static Date addAssignTimeSecond(Integer second) {
        return addAssignTime((Integer)null, (Integer)null, (Integer)null, (Integer)null, (Integer)null, second);
    }

    public static Date getAssignTime(Integer day, Integer hour, Integer minute, Integer second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(5, day == null ? 0 : day);
        cal.set(11, hour == null ? cal.get(11) : hour);
        cal.set(12, minute == null ? cal.get(12) : minute);
        cal.set(13, second == null ? cal.get(13) : second);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getAssignTime() {
        return getAssignTime((Integer)null);
    }

    public static Date getAssignTime(Integer day) {
        return getAssignTime(day, (Integer)null);
    }

    public static Date getAssignTime(Integer day, Integer hour) {
        return getAssignTime(day, hour, (Integer)null);
    }

    public static Date getAssignTime(Integer day, Integer hour, Integer minute) {
        return getAssignTime(day, hour, minute, (Integer)null);
    }

    public static String getTodayZeroTimeToStr() {
        return getTodayZeroTimeToStr((Boolean)null);
    }

    public static String getTodayZeroTimeToStr(Boolean all) {
        return getAssignTimeToStr(getTodayZeroTime(), all);
    }

    public static String getAssignTimeToStr(Date date, Boolean all) {
        if (date == null) {
            date = new Date();
        }

        if (all == null) {
            all = true;
        }

        String str;
        if (all) {
            str = DateFormatter.TIME_FORMAT_YMD_G_HMS.getFormatter();
        } else {
            str = DateFormatter.TIME_FORMAT_YMD_G.getFormatter();
        }

        SimpleDateFormat df = new SimpleDateFormat(str);
        return df.format(date);
    }

    public static String getTimestamp() {
        return getTimestamp((Date)null);
    }

    public static String getTimestamp(Date date) {
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat df = new SimpleDateFormat(DateFormatter.TIME_FORMAT_TIMESTMP.getFormatter());
        return df.format(date);
    }

    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int)((date2.getTime() - date1.getTime()) / 86400000L);
        return Math.abs(days);
    }

    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(6);
        int day2 = cal2.get(6);
        int year1 = cal1.get(1);
        int year2 = cal2.get(1);
        if (year1 == year2) {
            return Math.abs(day2 - day1);
        } else {
            int timeDistance = 0;

            for(int i = year1; i < year2; ++i) {
                if ((i % 4 != 0 || i % 100 == 0) && i % 400 != 0) {
                    timeDistance += 365;
                } else {
                    timeDistance += 366;
                }
            }

            return Math.abs(timeDistance + (day2 - day1));
        }
    }

    public static String stampToDate(Long s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        String res = simpleDateFormat.format(date);
        return res;
    }

    public static Date dateAddDate(Date d1, Date d2) {
        long dl1 = d1.getTime();
        long dl2 = d2.getTime();
        long now = (new Date()).getTime();
        return new Date(dl1 + dl2 - now);
    }

    public static Date strToDate(String str) {
        String format = null;
        if (str.contains("-") && str.contains(":")) {
            format = DateFormatter.TIME_FORMAT_YMD_G_HMS.getFormatter();
        } else if (str.contains("/") && str.length() == 10) {
            format = DateFormatter.TIME_FORMAT_YMD_G.getFormatter();
        } else if (str.contains("/") && str.contains(":")) {
            format = DateFormatter.TIME_FORMAT_YMD_H_HMS.getFormatter();
        } else if (str.contains("/") && str.length() == 10) {
            format = DateFormatter.TIME_FORMAT_YMD_H.getFormatter();
        } else if (str.contains("年") && str.contains("月") && str.contains("日") && str.contains(":")) {
            format = DateFormatter.TIME_FORMAT_CHINASTMP.getFormatter();
        } else if (str.contains("年") && str.contains("月") && str.contains("日") && str.length() == 10) {
            format = DateFormatter.TIME_FORMAT_CHINA.getFormatter();
        } else if (str.length() == 14) {
            format = DateFormatter.TIME_FORMAT_TIMESTMP.getFormatter();
        } else if (str.length() == 8) {
            format = DateFormatter.TIME_FORMAT_TIME.getFormatter();
        }

        if (format == null) {
            throw new RuntimeException("被转化的日期不正确");
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

            try {
                return simpleDateFormat.parse(str);
            } catch (ParseException var4) {
                var4.printStackTrace();
                throw new RuntimeException("被转化的日期不正确");
            }
        }
    }

    public static String dateToStr(Date date, DateFormatter dateFormatter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatter.getFormatter());
        return simpleDateFormat.format(date);
    }

    public static String dateToStr(Date date) {
        return dateToStr(date, DateFormatter.TIME_FORMAT_YMD_G_HMS);
    }

    public static Boolean dateCompareGreater(Date date1, Date date2) {
        return date1.getTime() > date2.getTime();
    }
}

