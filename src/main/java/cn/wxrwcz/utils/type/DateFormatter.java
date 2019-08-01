package cn.wxrwcz.utils.type;

public enum DateFormatter {

    TIME_FORMAT_YMD_G_HMS("yyyy-MM-dd HH:mm:ss"),
    TIME_FORMAT_YMD_G("yyyy-MM-dd"),
    TIME_FORMAT_YMD_H("yyyy/MM/dd"),
    TIME_FORMAT_YMD_H_HMS("yyyy/MM/dd HH:mm:ss"),
    TIME_FORMAT_TIMESTMP("yyyyMMddHHmmss"),
    TIME_FORMAT_TIME("yyyyMMdd"),
    TIME_FORMAT_CHINA("yyyy年MM月dd日"),
    TIME_FORMAT_CHINASTMP("yyyy年MM月dd日 HH:mm:ss");

    private final String formatter;

    private DateFormatter(String formatter) {
        this.formatter = formatter;
    }

    public String getFormatter() {
        return this.formatter;
    }
}
