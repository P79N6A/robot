package com.bossbutler.pojo.visitor;

import java.io.Serializable;
/**
 * 
 * 名称 :TransitLog
 * 描述 :访客设备
 * 创建人 :  hzhang
 * 创建时间: 2016年11月19日 下午6:56:37
 *
 */
public class Terminal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long terminalId;
	
	private String terminalName;

	private Long projectId;

	private String projectName;

	private String macAddress;

	private String projectCode;

	private String profile;

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}


}