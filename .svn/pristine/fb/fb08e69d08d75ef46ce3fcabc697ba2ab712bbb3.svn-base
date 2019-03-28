package com.bossbutler.common.constant;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 帐号常量表
 * @author hzhang
 *
 */
public class AccountConstant {
	
	/**平台账户职能（1：管理员；2：普通）*/ 
	public static final Long Role1 = 1L;
	/**平台账户职能（1：管理员；2：普通）*/ 
	public static final Long Role2 = 2L;
	/**验证级别（1，手机认证；2，邮箱认证；4，实名认证）*/ 
	public static final String AuthEnum1 = "1";
	/**验证级别（1，手机认证；2，邮箱认证；4，实名认证）*/ 
	public static final String AuthEnum2 = "2";
	/**验证级别（1，手机认证；2，邮箱认证；4，实名认证）*/ 
	public static final String AuthEnum3 = "3";
	
	/**通行类型（十六进制1个字节：00，qr；01，ic；02，pwd；03，IDCard；04，id卡）*/ 
	public static final String TransitTypeQr = "00";
	/**通行类型（十六进制1个字节：00，qr；01，ic；02，pwd；03，IDCard；04，id卡）*/ 
	public static final String TransitTypeIc = "01";
	/**通行类型（十六进制1个字节：00，qr；01，ic；02，pwd；03，IDCard；04，id卡）*/ 
	public static final String TransitTypePwd = "02";
	/**通行类型（十六进制1个字节：00，qr；01，ic；02，pwd；03，IDCard；04，id卡）*/ 
	public static final String TransitTypeIDCard = "03";
	/**通行类型（十六进制1个字节：00，qr；01，ic；02，pwd；03，IDCard；04，id卡）*/ 
	public static final String TransitTypeId = "04";
	/**状态代码（01，初始化；02，已验证；03，已认证（备用）；04，已禁用；）*/
	public static final String TransitStatus01 = "01";
	/**状态代码（01，初始化；02，已验证；03，已认证（备用）；04，已禁用；）*/
	public static final String TransitStatus02 = "02";
	/**状态代码（01，初始化；02，已验证；03，已认证（备用）；04，已禁用；）*/
	public static final String TransitStatus03 = "03";
	/**状态代码（01，初始化；02，已验证；03，已认证（备用）；04，已禁用；）*/
	public static final String TransitStatus04 = "04";
	/**
	 * 邀请码状态
	 */
	public static List<NameValue> applytypeList = null;
	/** 初始化*/
	static{
		applytypeList = new ArrayList<NameValue>();
	}
	
	/** 根据value获取name*/
	public static String getName(String value){
		String ret = null;
		if (StringUtils.isNotBlank(value)) {
			for (NameValue nv : applytypeList) {
				if (value.equals(nv.getValue())) {
					ret = nv.getName();
					break;
				}
			}
		}
		return ret;
	}
}
