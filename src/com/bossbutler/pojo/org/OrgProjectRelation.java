package com.bossbutler.pojo.org;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织与项目关系表
 * @author hzhang
 *
 */
public class OrgProjectRelation implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long relationId;

    private Long projectId;

    private Long orgId;
    
    private Long applyOrg;
    /**
     * 身份类型（01，产权人；02，委托人；03，转租人）
     */
    private String rightsType;
    /**
     * 职能类别（0301，物业职能；0302，承租职能；0303，业主职能；）
     */
    private String identityType;
    /**
     * 职务权限值
     */
    private Long roles;
    /**
     * 状态代码（01，初始化；02，已禁用；）
     */
    private String statusCode;
    /**
     * 状态时间
     */
    private Date statusTime;

    private Date createTime;

    private Date updateTime;
    /**
     * 操作人
     */
    private Long operatorId;

    private Date dataVersion;

    
	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getApplyOrg() {
		return applyOrg;
	}

	public void setApplyOrg(Long applyOrg) {
		this.applyOrg = applyOrg;
	}

	public Long getRoles() {
		return roles;
	}

	public void setRoles(Long roles) {
		this.roles = roles;
	}

	public String getRightsType() {
		return rightsType;
	}

	public void setRightsType(String rightsType) {
		this.rightsType = rightsType;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
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

}