package cn.wxrwcz.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class WxrUserInfoUtils {

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
    public static String phoneMosaic(String phone) {
        return phoneMosaic(phone, (Integer)null, (Integer)null);
    }

    public static String phoneMosaic(String phone, Integer front, Integer back) {
        if (WxrEmptyUtils.isEmptyString(phone)) {
            return null;
        } else {
            if (front == null || front < 0) {
                front = 3;
            }

            if (back == null || back < 0) {
                back = 4;
            }

            if (WxrStringUtils.isInteger(phone)) {
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
        if (WxrEmptyUtils.isEmptyString(name)) {
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

    public static Integer getAge(String idcard) {
        if (idcard == null) {
            return null;
        }
        Date birthdate = getBirthdayDate(idcard);
        GregorianCalendar currentDay = new GregorianCalendar();
        currentDay.setTime(birthdate);
        int year = currentDay.get(Calendar.YEAR);
        String birYear = WxrDateUtils.dateToStr(new Date(),"yyyy");
        return Integer.parseInt(birYear) - year;
    }
    private static Date getBirthdayDate(String idcard){
        String birthday = idcard.substring(6, 14);
        return WxrDateUtils.strToDate(birthday,"yyyyMMdd");
    }
    public static String getBirthday(String idcard) {
        if (idcard == null) {
            return null;
        }
        return WxrDateUtils.dateToStr(getBirthdayDate(idcard),"yyyy-MM-dd");
    }
    public static Boolean isBoy(String idcard) {
        String id17 = idcard.substring(16, 17);
        return Integer.parseInt(id17) % 2 != 0;
    }
}
