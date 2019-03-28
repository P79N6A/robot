package com.bossbutler.pojo.org;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
 

public class RoleModel implements java.io.Serializable{
	
private static final long serialVersionUID = 5454155825314635342L;
	
 
	//@Length(max=3)
	private java.lang.String appId;
	
	private java.lang.Long permissions;
	private java.lang.Long roleId;
	private java.lang.Long orgId;
	private java.lang.Long roleCode;

	private java.lang.String roleName;
	private java.lang.String roleType;
	private java.lang.String description;
	 
	
	private java.util.Date createTime;
	
	private java.util.Date updateTime;
	
	private java.lang.Long operatorId;
	private String isUsed = "no";
	
	private Boolean editFlag;
	 
	//columns END

	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRoleId())
			.toHashCode();
	}
	

	public java.lang.String getAppId() {
		return appId;
	}


	public void setAppId(java.lang.String appId) {
		this.appId = appId;
	}


	public java.lang.Long getRoleId() {
		return roleId;
	}


	public void setRoleId(java.lang.Long roleId) {
		this.roleId = roleId;
	}


	public java.lang.Long getOrgId() {
		return orgId;
	}


	public void setOrgId(java.lang.Long orgId) {
		this.orgId = orgId;
	}


	public java.lang.Long getRoleCode() {
		return roleCode;
	}


	public void setRoleCode(java.lang.Long roleCode) {
		this.roleCode = roleCode;
	}


	public java.lang.String getRoleName() {
		return roleName;
	}


	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}


	public java.lang.String getRoleType() {
		return roleType;
	}

	public void setRoleType(java.lang.String roleType) {
		this.roleType = roleType;
	}

	public java.lang.Long getPermissions() {
		return permissions;
	}

	public void setPermissions(java.lang.Long permissions) {
		this.permissions = permissions;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}


	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean equals(Object obj) {
		if(obj instanceof RoleModel == false) return false;
		if(this == obj) return true;
		RoleModel other = (RoleModel)obj;
		return new EqualsBuilder()
			.append(getRoleId(),other.getRoleId())
			.isEquals();
	}


	public java.util.Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}


	public java.util.Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}


	public java.lang.Long getOperatorId() {
		return operatorId;
	}


	public void setOperatorId(java.lang.Long operatorId) {
		this.operatorId = operatorId;
	}


	public Boolean getEditFlag() {
		return editFlag;
	}


	public void setEditFlag(Boolean editFlag) {
		this.editFlag = editFlag;
	}
}
