package com.bossbutler.common.constant;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

 

/**
 * 图片大类
 * @author hzhang
 *
 */
public class MediaMainClassify {
		
	/**60001:"平台广告"*/   
	public static final String MediaMainClassify01 = "60001";
	/** 60002:"用户资质"*/   
	public static final String MediaMainClassify02 = "60002";
	/**60003:"物业项目"*/   
	public static final String MediaMainClassify03 = "60003";
	/**60004:"单元房源"*/   
	public static final String MediaMainClassify04 = "60004";
	/**60005:"工位房源"*/   
	public static final String MediaMainClassify05 = "60005";
	/**60006:"会务房源"*/   
	public static final String MediaMainClassify06 = "60006";
	/**60007:"平台资源"*/   
	public static final String MediaMainClassify07 = "60007";
	/**60008:"系统资源"*/   
	public static final String MediaMainClassify08 = "60008";
	/**60009:"物业保修"*/   
	public static final String MediaMainClassify09 = "60009";
	/**60010:"会员信息"*/   
	public static final String MediaMainClassify10 = "60010";
	/**60011:"访客机信息"*/   
	public static final String MediaMainClassify11 = "60011";
	/**
	 * 
	 */
	public static List<NameValue> list = null;
	/** 初始化*/
	static{
		list = new ArrayList<NameValue>();
		list.add(new NameValue(MediaMainClassify01,"平台广告"));
		list.add(new NameValue(MediaMainClassify02,"用户资质"));
		list.add(new NameValue(MediaMainClassify03,"物业项目"));
		list.add(new NameValue(MediaMainClassify04,"单元房源"));
		list.add(new NameValue(MediaMainClassify05,"工位房源"));
		list.add(new NameValue(MediaMainClassify06,"会务房源"));
		list.add(new NameValue(MediaMainClassify07,"平台资源"));
		list.add(new NameValue(MediaMainClassify08,"系统资源"));
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
