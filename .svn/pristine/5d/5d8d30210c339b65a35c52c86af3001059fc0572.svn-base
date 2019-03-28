package com.bossbutler.common.constant;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 审核状态
 * @author hzhang
 *
 */
public class StatusType {
	
	/**邀请码状态（01，初始化；02，已验证；03，已过期）*/ 
	public static final String InvitationStatus01 = "01";
	/**邀请码状态（01，初始化；02，已验证；03，已过期）*/ 
	public static final String InvitationStatus02 = "02";
	/**邀请码状态（01，初始化；02，已验证；03，已过期）*/ 
	public static final String InvitationStatus03 = "03";
	
	/**状态代码（01，初始化；02，已验证；03，已认证（备用）；04，已禁用；）*/ 
	public static final String AccountStatus01 = "01";
	/**状态代码（01，初始化；02，已验证；03，已认证（备用）；04，已禁用；）*/ 
	public static final String AccountStatus02 = "02";
	/**状态代码（01，初始化；02，已验证；03，已认证（备用）；04，已禁用；）*/ 
	public static final String AccountStatus03 = "03";
	/**状态代码（01，初始化；02，已验证；03，已认证（备用）；04，已禁用；）*/ 
	public static final String AccountStatus04 = "04";
	
	/**状态代码(01，初始化；02，已禁用；)*/ 
	public static final String INIT = "01";
	/**状态代码(01，初始化；02，已禁用；)*/ 
	public static final String DISABLE = "01";
	/**状态代码（01，新增；02，修改；03，删除；04，过期；05，装载；06，卸载）*/ 
	public static final String TransitAdd = "01";
	/**状态代码（01，新增；02，修改；03，删除；04，过期；05，装载；06，卸载）*/ 
	public static final String TransitEdit = "02";
	/**状态代码（01，新增；02，修改；03，删除；04，过期；05，装载；06，卸载）*/ 
	public static final String TransitDelete = "03";
	/**状态代码（01，新增；02，修改；03，删除；04，过期；05，装载；06，卸载）*/ 
	public static final String TransitTimeOut = "04";
	/**状态代码（01，新增；02，修改；03，删除；04，过期；05，装载；06，卸载）*/ 
	public static final String TransitExe = "05";
	/**状态代码（01，新增；02，修改；03，删除；04，过期；05，装载；06，卸载）*/ 
	public static final String TransitUn = "06";
	/**
	 * 邀请码状态
	 */
	public static List<NameValue> invitationList = null;
	/**
	 * 帐号状态
	 */
	public static List<NameValue> accountList = null;
	/** 初始化*/
	static{
		invitationList = new ArrayList<NameValue>();
		invitationList.add(new NameValue("01", "未审核"));
		invitationList.add(new NameValue("02", "提交审核"));
		invitationList.add(new NameValue("03", "已审核"));
		
		accountList = new ArrayList<NameValue>();
		accountList.add(new NameValue("01", "初始化"));
		accountList.add(new NameValue("02", "已验证"));
		accountList.add(new NameValue("03", "已认证"));
		accountList.add(new NameValue("04", "已禁用"));
	}
	
	/** 根据value获取name*/
	public static String getName(String value){
		String ret = null;
		if (StringUtils.isNotBlank(value)) {
			for (NameValue nv : invitationList) {
				if (value.equals(nv.getValue())) {
					ret = nv.getName();
					break;
				}
			}
		}
		return ret;
	}
}
