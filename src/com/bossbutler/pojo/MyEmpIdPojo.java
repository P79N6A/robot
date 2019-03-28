package com.bossbutler.pojo;

public class MyEmpIdPojo {
private String accountId;
private String projectId;
private String permissions;//权限值 派单2048 接单 4096
public String getAccountId() {
	return accountId;
}
public void setAccountId(String accountId) {
	this.accountId = accountId;
}
public String getProjectId() {
	return projectId;
}
public void setProjectId(String projectId) {
	this.projectId = projectId;
}
public String getPermissions() {
	return permissions;
}
public void setPermissions(String permissions) {
	this.permissions = permissions;
}

}
