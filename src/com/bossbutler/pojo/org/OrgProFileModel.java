package com.bossbutler.pojo.org;

import java.io.Serializable;
import java.util.Date;

public class OrgProFileModel implements Serializable {
	private Long profileId;// 组织文件Id
	private Long orgId;// 组织ID
	private Long empId;// 员工id
	private String type;// 配置类型
	private String name;// 配置名称
	private String profile;// 配置文件 （json）
	private String statusCode;// 状态代码（01 初始化，02 已禁用）
	private Date statusTime;// 状态时间
	private String statusTimeStr;// 
	private Date createTime;// 创建时间
	private String createTimeStr;// 
	private Date updateTime;//更新时间
	private String updateTimeStr;
	
	
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Date getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
	public String getStatusTimeStr() {
		return statusTimeStr;
	}
	public void setStatusTimeStr(String statusTimeStr) {
		this.statusTimeStr = statusTimeStr;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateTimeStr() {
		return updateTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	
	
}
