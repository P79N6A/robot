package com.bossbutler.common.constant;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 角色常量表
 * @author hzhang
 *
 */
public class RolesConstant {
	
	/**角色类型（01，系统基础角色；02，系统管理角色；0301，物业职能角色；0302，承租职能角色；04，用户自定义角色）*/ 
	public static final String RoleType01 = "01";
	/**角色类型（01，系统基础角色；02，系统管理角色；0301，物业职能角色；0302，承租职能角色；04，用户自定义角色）*/ 
	public static final String RoleType02 = "02";
	/**角色类型（01，系统基础角色；02，系统管理角色；0301，物业职能角色；0302，承租职能角色；04，用户自定义角色）*/ 
	public static final String RoleType0301 = "0301";
	/**角色类型（01，系统基础角色；02，系统管理角色；0301，物业职能角色；0302，承租职能角色；04，用户自定义角色）*/ 
	public static final String RoleType0302 = "0302";
	/**角色类型（01，系统基础角色；02，系统管理角色；0301，物业职能角色；0302，承租职能角色；04，用户自定义角色）*/ 
	public static final String RoleType04 = "04";
	/**
	 * 角色类型
	 */
	public static List<NameValue> roletypeList = null;
	/** 初始化*/
	static{
		roletypeList = new ArrayList<NameValue>();
		roletypeList.add(new NameValue("01", "系统基础角色"));
		roletypeList.add(new NameValue("02", "系统管理角色"));
		roletypeList.add(new NameValue("0301", "物业职能角色"));
		roletypeList.add(new NameValue("0302", "承租职能角色"));
		roletypeList.add(new NameValue("04", "用户自定义角色"));
	}
	
	/** 根据value获取name*/
	public static String getName(String value){
		String ret = null;
		if (StringUtils.isNotBlank(value)) {
			for (NameValue nv : roletypeList) {
				if (value.equals(nv.getValue())) {
					ret = nv.getName();
					break;
				}
			}
		}
		return ret;
	}
}
