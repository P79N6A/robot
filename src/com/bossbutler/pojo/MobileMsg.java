package com.bossbutler.pojo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class MobileMsg implements Serializable{


	private static final long serialVersionUID = 8987972350298091717L;

	private String phone;
	private String name;
	private int orgId;
	private String[] contents;
	private String templateType;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String[] getContents() {
		return contents;
	}

	public void setContents(String[] contents) {
		this.contents = contents;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getName() {
		return StringUtils.isBlank(name) ? StringUtils.EMPTY : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}



}
