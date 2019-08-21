package cn.wxrwcz.utils;

import java.util.Random;
import java.util.UUID;

public class WxrRandomCaptchaUtils {
	private static char[] codeSequence={'A','a','B','b','c','D','d','E','e','F','f','G','g','H','h',
		   'i','J','j','k','L','M','m','N','n','p','Q','q','R','r','s','T','t', 'v', 'W',
		   'u','x','Y','y','z','0','1', '2', '3', '4', '5', '6', '7', '8', '9'};

	public static String randomCode(Integer num){
		//生成随机类   
	    Random random = new Random(); 
	    String code="";
	    for (int i = 0; i < num; i++) {
	    	Integer r=random.nextInt(codeSequence.length);
	    	String c =String.valueOf(codeSequence[r]);
	    	code+=c;
	    }
		return code;
	}

	public static String randomNum(Integer num){
		//生成随机类
		Random random = new Random();
		String code="";
		for (int i = 0; i < num; i++) {
			code+=random.nextInt(10);
		}
		return code;
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
}
