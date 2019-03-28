package com.bossbutler.pojo.visitor;

/**
 * 名称 :
 * 描述 :访客记录表
 * 创建人 :  hzhang
 * 创建时间: 2016年10月26日 下午8:35:40
 */
public class VisitorRecord {
	
	
	private String visitorId;//访客ID
	
	private String visitorName;//访客姓名
	
	private String projectName;//项目名称
	
	private String visitorDate;//访问时间
	
	private String orgName;//被访问组织
	
	private String visitorReason;//访问事由
	
	private String visitoredName;//被访人

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getVisitorDate() {
		return visitorDate;
	}

	public void setVisitorDate(String visitorDate) {
		this.visitorDate = visitorDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getVisitorReason() {
		return visitorReason;
	}

	public void setVisitorReason(String visitorReason) {
		this.visitorReason = visitorReason;
	}

	public String getVisitoredName() {
		return visitoredName;
	}

	public void setVisitoredName(String visitoredName) {
		this.visitoredName = visitoredName;
	}
	

	
}
