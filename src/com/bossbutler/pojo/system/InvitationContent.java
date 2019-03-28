package com.bossbutler.pojo.system;

/**
 * @author hzhang
 *
 */
public class InvitationContent {

	/**
	 * 项目ID
	 */
    private String projectId;
    /**
     * 发出邀请组织ID
     */
    private String orgId;
    /**
     * 
     */
    private String companyName;
    /**
     * 类型 01：项目管理组织 02：项目协作组织 03：租户协作组织
     */
    private String applyType;
    /**
     * 传递权限值
     */
    private String roles;
    /**
     * 权限类型
     */
    private String roleType;
    /**
     * 员工ID
     */
    private String empId;
    /**
     * 员工ID
     */
    private String applyId;

    private String linkMan;
    
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getApplyType() {
		return applyType;
	}
	
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
    
}
