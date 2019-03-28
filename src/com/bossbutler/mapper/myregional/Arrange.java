package com.bossbutler.mapper.myregional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 部署节点实体
 * @author zst
 */
@JsonInclude(Include.NON_NULL)
public class Arrange implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long arrangeId;   
    private Long projectId; 
    private String projectName;
    private Long orgId;
    private String arrangeCode; 
    private String arrangeName;   
    private Long supperId;   
    private String entrance_type;
    private String resource_type;
    private String arrange_type;
    private String arrangeIds;
    private List<String> projectIds;
    private String accountId;
    private String resourceId;
    private String regionalType;
    private String scopeType;// 01，开放给员工使用
    
    
    
    public String getScopeType() {
		return scopeType;
	}
	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}
	public String getRegionalType() {
		return regionalType;
	}
	public void setRegionalType(String regionalType) {
		this.regionalType = regionalType;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getArrangeIds() {
		return arrangeIds;
	}
	public void setArrangeIds(String arrangeIds) {
		this.arrangeIds = arrangeIds;
	}
	public List<String> getProjectIds() {
		return projectIds;
	}
	public void setProjectIds(List<String> projectIds) {
		this.projectIds = projectIds;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getResource_type() {
		return resource_type;
	}
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}
	public String getArrange_type() {
		return arrange_type;
	}
	public void setArrange_type(String arrange_type) {
		this.arrange_type = arrange_type;
	}
	public String getEntrance_type() {
		return entrance_type;
	}
	public void setEntrance_type(String entrance_type) {
		this.entrance_type = entrance_type;
	}
	private String statusCode;   
    private Date statusTime;
    private Date createTime;
    private Date updateTime;
    private Long operatorId;    
    private Date dataVersion;
	private String temp;//用于是新增还是修改
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public Date getDataVersion() {
		return dataVersion;
	}
	public void setDataVersion(Date dataVersion) {
		this.dataVersion = dataVersion;
	}
	public Long getArrangeId() {
		return arrangeId;
	}
	public void setArrangeId(Long arrangeId) {
		this.arrangeId = arrangeId;
	}
	
	public String getArrangeCode() {
		return arrangeCode;
	}
	public void setArrangeCode(String arrangeCode) {
		this.arrangeCode = arrangeCode;
	}
	public String getArrangeName() {
		return arrangeName;
	}
	public void setArrangeName(String arrangeName) {
		this.arrangeName = arrangeName;
	}
	public Long getSupperId() {
		return supperId;
	}
	public void setSupperId(Long supperId) {
		this.supperId = supperId;
	}
}