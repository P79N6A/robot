package com.bossbutler.common.constant;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 审核状态
 * @author hzhang
 *
 */
public class InvitationType {
	
	/**邀请类型（01，邀请组织管理员；02，邀请员工；03，邀请访客）*/ 
	public static final String InvitationType01 = "01";
	/**邀请类型（01，邀请组织管理员；02，邀请员工；03，邀请访客）*/ 
	public static final String InvitationType02 = "02";
	/**邀请类型（01，邀请组织管理员；02，邀请员工；03，邀请访客）*/ 
	public static final String InvitationType03 = "03";
	/**
	 * 邀请码状态
	 */
	public static List<NameValue> list = null;
	/** 初始化*/
	static{
		list = new ArrayList<NameValue>();
		list.add(new NameValue("01", "邀请组织管理员"));
		list.add(new NameValue("02", "邀请员工"));
		list.add(new NameValue("03", "邀请访客"));
	}
	
	/** 根据value获取name*/
	public static String getName(String value){
		String ret = null;
		if (StringUtils.isNotBlank(value)) {
			for (NameValue nv : list) {
				if (value.equals(nv.getValue())) {
					ret = nv.getName();
					break;
				}
			}
		}
		return ret;
	}
}
