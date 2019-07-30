package cn.wxrwcz.utils;

import com.sun.istack.internal.NotNull;

import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Description: java类作用描述
 * @Author: wangcz
 * @CreateDate: 2019/7/30$ 18:36$
 * @Version: 1.0
 */
public class MyStringUtils {
    private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private MyStringUtils() {
    }

    public static Integer getIndex(String old, String index) {
        return old.indexOf(index);
    }

    public static String getTextRight(String old, String index) {
        return old.indexOf(index) == -1 ? old : old.substring(old.indexOf(index) + index.length());
    }

    public static String getTextRights(String old, String index) {
        return old.lastIndexOf(index) == -1 ? old : old.substring(old.lastIndexOf(index) + index.length());
    }

    public static String getTextLeft(String old, String index) {
        return old.indexOf(index) == -1 ? old : old.substring(0, old.indexOf(index));
    }

    public static String getTextLefts(String old, String index) {
        return old.lastIndexOf(index) == -1 ? old : old.substring(0, old.lastIndexOf(index));
    }

    public static String getTextMiddle(String old, String left, String right) {
        String temp = getTextRight(old, left);
        return getTextLeft(temp, right);
    }

    public static String getTextMiddles(String old, String left, String right) {
        String temp = getTextRight(old, left);
        return getTextLefts(temp, right);
    }

    public static Boolean isEmpty(String text) {
        return text == null ? true : text.trim().equals("");
    }

    public static Boolean isEmpty(List list) {
        return list == null ? true : list.size() <= 0;
    }

    public static Integer strCaseInt(String number) {
        return new Integer(number);
    }

    public static Long strCaseLong(String number) {
        return new Long(number);
    }

    public static Integer strAddCaseInt(String number1, Object number2) {
        return new Integer(number1) + new Integer(number2.toString());
    }

    public static boolean isInteger(String str) {
        if (isEmpty(str)) {
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
        if (isEmpty(str)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*\\.?[\\d]+$");
            return pattern.matcher(str).matches();
        }
    }

    public static String deleteAllInvisibleCharacters(String str) {
        return str.replaceAll("\\s", "");
    }

    public static void listDistinct(List list) {
        HashSet set = new HashSet(list);
        list.clear();
        list.addAll(set);
    }

    public static String[] strArrayDistinct(String[] strArray) {
        List<String> list = Arrays.asList(strArray);
        List<String> arrayList = new ArrayList(list);
        listDistinct(arrayList);
        return (String[])arrayList.toArray(new String[arrayList.size()]);
    }

    public static boolean checkEmail(String email) {
        return email.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
    }

    public static String longUrlToShorUrl(String longUrl) {
        String key = "LiuZhi";
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String sMD5EncryptResult = MD5Code32(key + longUrl);
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];

        for(int i = 0; i < 4; ++i) {
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            long lHexLong = 1073741823L & Long.parseLong(sTempSubString, 16);
            String outChars = "";

            for(int j = 0; j < 6; ++j) {
                long index = 61L & lHexLong;
                outChars = outChars + chars[(int)index];
                lHexLong >>= 5;
            }

            resUrl[i] = outChars;
        }

        return resUrl[1];
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

    public static String phoneMosaic(String phone) {
        return phoneMosaic(phone, (Integer)null, (Integer)null);
    }

    public static String phoneMosaic(String phone, Integer front, Integer back) {
        if (isEmpty(phone)) {
            return null;
        } else {
            if (front == null || front < 0) {
                front = 3;
            }

            if (back == null || back < 0) {
                back = 4;
            }

            if (!isInteger(phone)) {
                return phone;
            } else {
                int len = phone.length();
                String left = phone.substring(0, front);
                StringBuffer sb = new StringBuffer(left);

                for(int i = 0; i < len - (front + back); ++i) {
                    sb.append("*");
                }

                sb.append(phone.substring(len - back));
                return sb.toString();
            }
        }
    }

    public static String nameMosaic(String name) {
        if (isEmpty(name)) {
            return null;
        } else {
            int len = name.length();
            StringBuffer sb = new StringBuffer();
            sb.append(name.substring(0, 1));
            if (len > 2) {
                for(int i = 1; i < len - 1; ++i) {
                    sb.append("*");
                }

                sb.append(name.substring(len - 1));
            } else {
                sb.append("*");
            }

            return sb.toString();
        }
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

    public static Map<String, String> getUrlParam(@NotNull String url) {
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
        return getRandom((Integer)null, (Boolean)null);
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

    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber != null && !"".equals(IDNumber)) {
            String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
            boolean matches = IDNumber.matches(regularExpression);
            if (matches && IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    int[] idCardWi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    String[] idCardY = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;

                    int idCardMod;
                    for(int i = 0; i < idCardWi.length; ++i) {
                        idCardMod = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = idCardMod * idCardWi[i];
                        sum += count;
                    }

                    char idCardLast = charArray[17];
                    idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() + "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }
                } catch (Exception var10) {
                    var10.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            } else {
                return matches;
            }
        } else {
            return false;
        }
    }

    public static Integer strExtractNumber(String str) {
        String regex = "\\D";
        return Integer.valueOf(str.replaceAll(regex, ""));
    }

    public static String strZeroize(String str, int places) {
        StringBuffer sb = new StringBuffer();

        for(int i = -1; i < places - str.length(); ++i) {
            sb.append("0");
        }

        sb.append(str);
        return sb.toString().substring(sb.length() - places);
    }
}
