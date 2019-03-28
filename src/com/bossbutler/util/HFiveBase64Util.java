package com.bossbutler.util;

import java.util.Date;

import com.company.util.Base64;
import com.company.util.DES;

/**
 * 将String进行base64编码解码，使用utf-8
 */
public class HFiveBase64Util {

	private static final String separated = "@";

	private static final String salt = "xiaobao";

	/**
	 * 对给定的字符串进行base64解码操作
	 * 
	 * @throws Exception
	 */
	public static String decodeData(String inputData) throws Exception {
		return Base64.getFromBase64(inputData, salt, separated);
	}

	/**
	 * 对给定的字符串进行base64加密操作
	 * 
	 * @throws Exception
	 */
	public static String encodeData(String inputData) throws Exception {
		return Base64.getBase64(inputData, salt, separated);
	}

	public static void main(String[] args) throws Exception {
		long dt = new Date().getTime();
		System.out.println(dt);
		// System.out.println(DES.encrypt("123132机阿萨德法师打发aa", dt + ""));
		System.out.println(DES.decrypt("ellBe95uNwP0DRgm7/mbAOtSeZ70qeCdvVKCH9Hru+c=", "1509696978849"));
	}

}
