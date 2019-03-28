package com.bossbutler.pojo.visitor;

/**
 * 名称 :VisitorApplyIn
 * 描述 :
 * 创建人 :  hzhang
 * 创建时间: 2016年10月26日 下午8:35:40
 */
public class VisitorApplyOut {
	
	private String applyId;//邀请记录ID
	
	private String visitorId;//访客ID
	
	private String visitorName;//访客姓名
	
	private String projectName;//项目名称
	
	private String visitorDate;//访问时间
	
	private String invitationDate;//邀请时间
	
	private String arriveTime;//到达时间
	
	private String passCount;//通行次数
	
	private String passStatus;//通行状态
	
	private int limitCount;//同行人数
	

	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}

	public String getVisitorDate() {
		return visitorDate;
	}

	public void setVisitorDate(String visitorDate) {
		this.visitorDate = visitorDate;
	}

	public String getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(String invitationDate) {
		this.invitationDate = invitationDate;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getPassCount() {
		return passCount;
	}

	public void setPassCount(String passCount) {
		this.passCount = passCount;
	}

	public String getPassStatus() {
		return passStatus;
	}

	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}
	
}
