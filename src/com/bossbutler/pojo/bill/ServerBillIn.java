package com.bossbutler.pojo.bill;

/**
 * 用于放服务单列表参数
 * 
 * @author 98030
 *
 */
public class ServerBillIn {

	private String pageNo;// 当前页
	private String pageSize;// 每页的条数
	
	private String type;// 1.带接单，2，进行中3，已完成 4.已取消
	private String serverType;// 服务类型
	private String accountId;
	private String serverId;// 服务单ID
	private String projectId;
	private String isAdmin;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

}
