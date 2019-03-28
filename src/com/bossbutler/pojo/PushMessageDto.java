package com.bossbutler.pojo;

import java.io.Serializable;
import java.util.Map;

/**
 * 名称 :PushMessageDto 描述 :推送消息返回 创建人 : hzhang 创建时间: 2016年9月18日 下午9:13:06
 */
public class PushMessageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;// 推送类型

	private String id;// 业务ID

	private String content;// 内容

	private String category;// 类别
	
	private String pd = "0";
	
	private String jd = "0";
	private String title;
	private String webHtml;
	private Map<String,String> map; // 2017-07-27 weish
	

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public PushMessageDto() {
		super();
	}

//	public PushMessageDto(String type,String category, String id, String content) {
//		super();
//		this.type = type;
//		this.category = category;
//		this.id = id;
//		this.content = content;
//	}
	
	public PushMessageDto(String type,String category, String id, String content,Map<String,String>info) {
		super();
		this.type = type;
		this.category = category;
		this.id = id;
		this.content = content;
		this.map = info;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWebHtml() {
		return webHtml;
	}

	public void setWebHtml(String webHtml) {
		this.webHtml = webHtml;
	}


}
