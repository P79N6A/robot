package com.bossbutler.common.constant;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
/**
 * 名称 :RegionalConstant
 * 描述 :通行区域常量表包含 regional/arrange/device 表
 * 创建人 :  hzhang
 * 创建时间: 2016年9月2日 下午9:41:43
 */
public class RegionalConstant {
	
	/**部署类型（01，公共节点；02，非公共节点；）*/ 
	public static final String ArrangeType01 = "01";
	/**部署类型（01，公共节点；02，非公共节点；）*/ 
	public static final String ArrangeType02 = "02";
	
	
	
	
	
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
