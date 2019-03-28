package com.bossbutler.pojo.visitor;

import com.bossbutler.util.PageParams;

/**
 * 名称 :VisitorApplyIn
 * 描述 :
 * 创建人 :  hzhang
 * 创建时间: 2016年10月26日 下午8:35:40
 */
public class VisitorMachineIn extends PageParams {
	
	private String operatorId;//帐号ID
	private String visitorId;//访客ID
	private String regionalId;// 通行区域ID
	private String mackAddress;// mac地址
	
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}
	public String getRegionalId() {
		return regionalId;
	}
	public void setRegionalId(String regionalId) {
		this.regionalId = regionalId;
	}
	public String getMackAddress() {
		return mackAddress;
	}
	public void setMackAddress(String mackAddress) {
		this.mackAddress = mackAddress;
	}
	
}
