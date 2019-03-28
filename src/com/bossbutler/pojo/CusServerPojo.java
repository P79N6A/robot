package com.bossbutler.pojo;

public class CusServerPojo {
	private String accountId;
	private String tittle;// 标题
	private String name;// 接单人
	private String monadtime;// 创建时间
	private String monadrype;// 服务单类型
	private String type;// 状态
	private String comments;// 是否评论 0未评论 1.以评论 只有状态为已完成时 才有数据
	private String serverId;// 服务单Id
	private String address;// 服务地址 这个是待接单有值
	private String servercode;// 服务单单号
	private String content;// 服务单内容
	private String statusCode;//状态代码（01，初始化；02，已提交；03，已派单；04，被指派；05，已撤回；06，已接单；07，已完成；08，已评价；09，已取消）


	public String getServercode() {
		return servercode;
	}

	public void setServercode(String servercode) {
		this.servercode = servercode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMonadtime() {
		return monadtime;
	}

	public void setMonadtime(String monadtime) {
		this.monadtime = monadtime;
	}

	public String getMonadrype() {
		return monadrype;
	}

	public void setMonadrype(String monadrype) {
		this.monadrype = monadrype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
}
