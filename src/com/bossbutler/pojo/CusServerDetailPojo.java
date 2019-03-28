package com.bossbutler.pojo;

import java.util.ArrayList;
import java.util.List;

public class CusServerDetailPojo {
	private String serverId;
	private String title;// 服务单标题
	private String billDesc;// 描述
	private String billCode;// 单号
	private String billType;//服务单类型
	private String project;// 所属项目
	private String address;// 项目地址
	private String servertime;// 期望服务时间
	private String customerName;// 租户姓名
	private String phone;// 租户电话
	private String noservertime;// 取消订单时间(当该订单被取消时有值)
	private String preservertime;// 预计完成时间(进行中时有值)
	private String parameter;// 1是派单方的详情时 为被指派人 2是被指派的详情时 为 指派人 3当已完成时 为完成人
								// 4进行中的为接单人 5当租户方的进行或者完成时时为接单人
	private String statusCode;
	private String type;
	private String realservertime;// 实际完成时间(已完成时有值)
	private String pdAccountId;//派单人ID
	private String isSend;
	List<ProjectProcessPojo> pList = new ArrayList<>();
	List<String> picPath ;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getBillDesc() {
		return billDesc;
	}

	public void setBillDesc(String billDesc) {
		this.billDesc = billDesc;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getPreservertime() {
		return preservertime;
	}

	public void setPreservertime(String preservertime) {
		this.preservertime = preservertime;
	}

	public String getRealservertime() {
		return realservertime;
	}

	public void setRealservertime(String realservertime) {
		this.realservertime = realservertime;
	}

	public String getNoservertime() {
		return noservertime;
	}

	public void setNoservertime(String noservertime) {
		this.noservertime = noservertime;
	}

	public List<ProjectProcessPojo> getpList() {
		return pList;
	}

	public void setpList(List<ProjectProcessPojo> pList) {
		this.pList = pList;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServertime() {
		return servertime;
	}

	public void setServertime(String servertime) {
		this.servertime = servertime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<String> getPicPath() {
		return picPath;
	}

	public void setPicPath(List<String> picPath) {
		this.picPath = picPath;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public String getPdAccountId() {
		return pdAccountId;
	}

	public void setPdAccountId(String pdAccountId) {
		this.pdAccountId = pdAccountId;
	}

}
