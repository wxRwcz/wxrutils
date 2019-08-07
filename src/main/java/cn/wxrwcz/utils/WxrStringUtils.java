package cn.wxrwcz.utils;

import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Pattern;

public class WxrStringUtils {
    private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static boolean isInteger(String str) {
        if (WxrEmptyUtils.isEmptyString(str)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            return pattern.matcher(str).matches();
        }
    }

    public static String subString(String str, int length) {
        return subString(str, 0, length);
    }

    public static String subString(String str, int beginIndex, int endIndex) {
        return str.substring(beginIndex, endIndex > str.length() ? str.length() : endIndex);
    }

    public static boolean isDouble(String str) {
        if (WxrEmptyUtils.isEmptyString(str)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*\\.?[\\d]+$");
            return pattern.matcher(str).matches();
        }
    }

    public static String deleteAllInvisibleCharacters(String str) {
        return str.replaceAll("\\s", "");
    }

    public static boolean checkEmail(String email) {
        return email.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = b + 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname != null && !"".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }
        } catch (Exception var4) {
            ;
        }

        return resultString;
    }

    public static String MD5Code32(String str) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        byte[] b = md5.digest();
        StringBuffer buf = new StringBuffer("");

        for(int offset = 0; offset < b.length; ++offset) {
            int i = b[offset];
            if (i < 0) {
                i += 256;
            }

            if (i < 16) {
                buf.append("0");
            }

            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }

    public static boolean isChinese(String str) {
        if (str == null) {
            return false;
        } else {
            char[] cTemp = str.toCharArray();

            for(int i = 0; i < str.length(); ++i) {
                if (!isChinese(cTemp[i])) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static Map<String, String> getUrlParam( String url) {
        Map<String, String> map = new HashMap();
        String[] strings = url.split("&");
        String[] var3 = strings;
        int var4 = strings.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            String[] params = s.split("=");

            for(int i = 0; i < params.length; ++i) {
                String var10001 = params[i];
                ++i;
                map.put(var10001, params[i]);
            }
        }

        return map;
    }

    public static String getRandom(int length) {
        return getRandom(length, null);
    }

    public static String getRandom(Integer length, Boolean isUpperCase) {
        String uuid = UUID.randomUUID().toString();
        if (length != null && length > 0) {
            uuid = uuid.substring(0, length);
        }
        if (isUpperCase != null && !isUpperCase) {
            uuid = uuid.toLowerCase();
        }
        return uuid;
    }

    public static Integer strExtractNumber(String str) {
        String regex = "\\D";
        return Integer.valueOf(str.replaceAll(regex, ""));
    }

    public static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
