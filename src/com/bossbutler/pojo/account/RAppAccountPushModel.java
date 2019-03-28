package com.bossbutler.pojo.account;

public class RAppAccountPushModel {
	private String id;
	private String appCodeName;
	private String accountId;
	private String pushFlag;//1为推送0为不推送
	private String projectId;
	private String menus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppCodeName() {
		return appCodeName;
	}
	public void setAppCodeName(String appCodeName) {
		this.appCodeName = appCodeName;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPushFlag() {
		return pushFlag;
	}
	public void setPushFlag(String pushFlag) {
		this.pushFlag = pushFlag;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getMenus() {
		return menus;
	}
	public void setMenus(String menus) {
		this.menus = menus;
	}
	
	

}
