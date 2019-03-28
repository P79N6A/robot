package com.bossbutler.pojo.visitor;

import com.bossbutler.util.PageParams;

/**
 * 名称 :VisitorApplyIn
 * 描述 :
 * 创建人 :  hzhang
 * 创建时间: 2016年10月26日 下午8:35:40
 */
public class VisitorApplyIn extends PageParams {
	
	private String accountId;//帐号ID
	private String visitorId;//访客ID
	private String applyId;//邀请id
	private String visitorTime;//邀请时间
	private String projectId;//项目ID
	private String orgId;//组织ID
	private String operatorId;
	private String vTime;//访问时间点
	private String sourceType;// 访客来源 01，长期访客
	private String dateformatType;// 
	private String arrangeId;// 节点ID
	private String beginDate;
	private String endDate;
	private String beginTime;
	private String endTime;
	private String applyType;
	private String statusCode;
	
	
	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getArrangeId() {
		return arrangeId;
	}

	public void setArrangeId(String arrangeId) {
		this.arrangeId = arrangeId;
	}

	public String getDateformatType() {
		return dateformatType;
	}

	public void setDateformatType(String dateformatType) {
		this.dateformatType = dateformatType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getVisitorTime() {
		return visitorTime;
	}

	public void setVisitorTime(String visitorTime) {
		this.visitorTime = visitorTime;
	}

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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getvTime() {
		return vTime;
	}

	public void setvTime(String vTime) {
		this.vTime = vTime;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
