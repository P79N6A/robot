package com.bossbutler.common.constant;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

 
/**
 * 媒体类型
 * @author hzhang
 *
 */
public class MediaType {
	/**61001:"头像"*/   
	public static final String MediaType01 = "61001";
	/**61002:"主图"*/  
	public static final String MediaType02 = "61002";
	/**61003:"详图"*/  
	public static final String MediaType03 = "61003";
	/**61004:"视频"*/   
	public static final String MediaType04 = "61004";
	/**61101:"身份证(正面)"*/  
	public static final String MediaType05 = "61101";
	/**61102:"身份证(反面)"*/   
	public static final String MediaType06 = "61102";
	/**61103:"营业执照"*/  
	public static final String MediaType07 = "61103";
	/**61104:"税务登记证"*/  
	public static final String MediaType08 = "61104";
	/** 61105:"组织机构代码证"*/  
	public static final String MediaType09 = "61105";
	/** 61106:"三证合一"*/  
	public static final String MediaType10 = "61106";
	/**61201:"房源产证"*/    
	public static final String MediaType11 = "61201";
	/**61202:"预售/预租许可证"*/    
	public static final String MediaType12 = "61202";
	/**61203:"租赁委托协议（企业）"*/     
	public static final String MediaType13 = "61203";
	/**61204:"租赁委托协议（个人）"*/  
	public static final String MediaType14 = "61204";
	/**61205:"转租授权协议（企业）"*/  
	public static final String MediaType15 = "61205";
	/**61206:"转租授权协议（个人）*/  
	public static final String MediaType16 = "61206";
	/**61301服务工单黑图*/
	public static final String MediaType301 = "61301";
	/**61107,访客身份证；61108，访客其他证件；61109，抓拍图片*/
	public static final String MediaTypeVisitor07 = "61107";
	/**61107,访客身份证；61108，访客其他证件；61109，抓拍图片*/
	public static final String MediaTypeVisitor08 = "61108";
	/**61107,访客身份证；61108，访客其他证件；61109，抓拍图片*/
	public static final String MediaTypeVisitor09 = "61109";
	
	/**
	 * 返回 {"name":"XX","value":"xxx"} …… 数据
	 */
	public static List<NameValue> list = null;
	/**
	 * 有编号媒体集合
	 */
	public static List<NameValue> nolist = null;
	/** 初始化*/
	static{
		list = new ArrayList<NameValue>();
		list.add(new NameValue(MediaType01,"头像"));
		list.add(new NameValue(MediaType02,"主图"));
		list.add(new NameValue(MediaType03,"详图"));
		list.add(new NameValue(MediaType04,"视频"));
		list.add(new NameValue(MediaType05,"身份证（正面）"));
		list.add(new NameValue(MediaType06,"身份证（反面）"));
		list.add(new NameValue(MediaType07,"营业执照"));
		list.add(new NameValue(MediaType08,"税务登记证"));
		list.add(new NameValue(MediaType09,"组织机构代码证"));
		list.add(new NameValue(MediaType10,"三证合一"));
		list.add(new NameValue(MediaType11,"房源产证"));
		list.add(new NameValue(MediaType12,"预售/预租许可证"));
		list.add(new NameValue(MediaType13,"租赁委托协议（企业）"));
		list.add(new NameValue(MediaType14,"租赁委托协议（个人）"));
		list.add(new NameValue(MediaType15,"转租授权协议（企业）"));
		list.add(new NameValue(MediaType16,"转租授权协议（个人）"));
		nolist.add(new NameValue(MediaType05,"身份证（正面）"));
		nolist.add(new NameValue(MediaType06,"身份证（反面）"));
		nolist.add(new NameValue(MediaType07,"营业执照"));
		nolist.add(new NameValue(MediaType08,"税务登记证"));
		nolist.add(new NameValue(MediaType09,"组织机构代码证"));
		nolist.add(new NameValue(MediaType11,"房源产证"));
		nolist.add(new NameValue(MediaType12,"预售/预租许可证"));
 
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
	/** 根据value获取name*/
	public static List<NameValue> getnolist(){
		nolist.add(new NameValue(MediaType05,"身份证（正面）"));
		nolist.add(new NameValue(MediaType06,"身份证（反面）"));
		nolist.add(new NameValue(MediaType07,"营业执照"));
		nolist.add(new NameValue(MediaType08,"税务登记证"));
		nolist.add(new NameValue(MediaType09,"组织机构代码证"));
		nolist.add(new NameValue(MediaType11,"房源产证"));
		nolist.add(new NameValue(MediaType12,"预售/预租许可证"));
		return nolist;
	}
}
