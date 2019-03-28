package com.bossbutler.pojo;

/**
 * 用于放服务单列表参数
 * 
 * @author 98030
 *
 */
public class PropertyRequestPojo {
	private String characters;// 0 物业 1 租户
	private String type;// 1.带接单，2，进行中3，已完成 4.已取消
	private String serverType;// 服务类型
	private String accountId;
	private String bpaccountId;// 被派单的Id
	private String serverId;// 服务单ID
	private String empId;// 员工Id
	private String bpempId;// 被拍单员工Id
	private String pageNo;// 当前页
	private String pageSize;// 每页的条数
	private String page;// 当前页
	private String pagesize;// 每页的条数
	private String progressId;// 进度Id
	private String bprogressId;// 被派单的进度Id
	private String pretime;// 预计完成时间 1.一小时内2.三小时内 3. 一天内
	private String projectId;
	private String rejectedContent;//驳回原因
	private String solution;//解决方案
	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public String getBpaccountId() {
		return bpaccountId;
	}

	public void setBpaccountId(String bpaccountId) {
		this.bpaccountId = bpaccountId;
	}

	public String getBpempId() {
		return bpempId;
	}

	public void setBpempId(String bpempId) {
		this.bpempId = bpempId;
	}

	public String getPretime() {
		return pretime;
	}

	public void setPretime(String pretime) {
		this.pretime = pretime;
	}

	public String getBprogressId() {
		return bprogressId;
	}

	public void setBprogressId(String bprogressId) {
		this.bprogressId = bprogressId;
	}

	public String getProgressId() {
		return progressId;
	}

	public void setProgressId(String progressId) {
		this.progressId = progressId;
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

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getRejectedContent() {
		return rejectedContent;
	}

	public void setRejectedContent(String rejectedContent) {
		this.rejectedContent = rejectedContent;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

}
