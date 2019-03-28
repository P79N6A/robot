package com.bossbutler.pojo;

public class MobileCodeView extends BaseModel {

	private String sn;
	private String phone;
	private String content;
	private String isCheckReg;//校验是否注册
	private String accountId;//

	/**
	 * 手机识别码
	 * 
	 * @return
	 */
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 手机号码
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 发送的消息
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsCheckReg() {
		return isCheckReg;
	}

	public void setIsCheckReg(String isCheckReg) {
		this.isCheckReg = isCheckReg;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	
}