package com.bossbutler.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.company.util.CommonUtil;

/**
 * <p>
 * 对称加密.
 * </p>
 * 
 * @author wq
 *
 */
public class MacConfirmFm {

	private static final String VERSION = "1$0%1";

	/**
	 * 报文校验密钥常量
	 */
	private static final String KEY = "botler#bao!sysTeM@BanW@file.Boss";

	/**
	 * 随机生成6位数字字符数组
	 * 
	 * @param dateTime
	 *            时间戳
	 * @return
	 */
	private static String generateCheckKey(String dateTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(KEY);
		sb.append(CommonUtil.Object2String(dateTime));
		sb.append(VERSION);
		return sb.toString();
	}

	/**
	 * 根据报文获得验证信息.
	 * 
	 * @param data
	 *            报文
	 * @param dateTime
	 *            时间戳
	 * @throws Exception
	 */
	private static String getCheckMac(String data01, String dateTime) throws Exception {
		String localMac = StringUtils.EMPTY;
		String loc = data01 + generateCheckKey(dateTime);
		localMac = DigestUtils.md5Hex(loc);
		return localMac;
	}

	/**
	 * 检测报文是否异常
	 * 
	 * @param mac
	 *            传来的验证信息
	 * @param data
	 *            报文
	 * @param dateTime
	 *            时间戳
	 * @return Boolean
	 * @throws Exception
	 */
	public static Boolean checkMac(String mac, String data, String dateTime) throws Exception {
		String localMac = getCheckMac(data, dateTime);
		if (localMac.equals(mac)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据报文获得手机验证信息.
	 * 
	 * @param data
	 *            报文
	 * @param dateTime
	 *            时间戳
	 * @return
	 * @throws Exception
	 */
	public static String getMacForMobile(String data, String dateTime) throws Exception {
		return getCheckMac(data, dateTime);
	}

}