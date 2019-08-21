package cn.wxrwcz.utils;

import java.util.Random;
import java.util.UUID;

/** 
 * RandomCode:生成随机Code
 * @Project Name:dhfmmd
 * @File Name:RandomCode.java 
 * @Package Name:com.dhfmmd.utils
 * @Creator:REN GUO QING
 * @Date:2016年5月12日下午2:12:00 
 */
public class RandomCaptchaUtils {
	private static char[] codeSequence={'A','a','B','b','c','D','d','E','e','F','f','G','g','H','h',
		   'i','J','j','k','L','M','m','N','n','p','Q','q','R','r','s','T','t', 'v', 'W',
		   'u','x','Y','y','z','0','1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	/**
	 * GetRandomCode:(获取随机Code).
	 * @param num 获取几位Code
	 * @return 
	 * @return :String 
	 * @Creator:REN GUO QING
	 * @Date:2016年5月12日 下午2:30:44
	 */
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
	/**
	 * GetRandomCode:(获取随机Code).
	 * @param num 获取几位Code
	 * @return
	 * @return :String
	 * @Creator:REN GUO QING
	 * @Date:2016年5月12日 下午2:30:44
	 */
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
