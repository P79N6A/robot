package com.bossbutler.pojo.system;

import java.util.List;

import com.bossbutler.pojo.MyCompany;

public class AccountOut {

	private String accountId;// 账户ID
	private Integer accountCode;// 账户编号
	private String loginId;// 登录名
	private String loginName;// 昵称
	private String mobilephone;// 移动电话
	private String email;// 电子邮箱
	private String qrId;// 二维码
	private String icId;// IC卡
	private String nfcId;// nfc卡
	private String fpId;// 指纹ID
	private String statusCode;// 01，初始化；02，已验证；03，已认证；04，已禁用；
	// private Date loginTime;//登录时间
	private String headPath;
	// 登录识别字段
	private String token;
	private String currentTime;
	private String sex;// 性别
	private String score;// 评分
	private String billCount;// 接单数据
	private List<MyCompany> companies;
	private String menus;// 菜单数据
	private String qrMenus;// 二维码下方菜单
	private String openShow;// 01，登录打开首页；02，登录打开二维码
	private String language;// 语言
	private String addSecond;
	private String refSecond;
	
	
	
	public String getAddSecond() {
		return addSecond;
	}

	public void setAddSecond(String addSecond) {
		this.addSecond = addSecond;
	}

	public String getRefSecond() {
		return refSecond;
	}

	public void setRefSecond(String refSecond) {
		this.refSecond = refSecond;
	}

	public String getQrMenus() {
		return qrMenus;
	}

	public void setQrMenus(String qrMenus) {
		this.qrMenus = qrMenus;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getOpenShow() {
		return openShow;
	}

	public void setOpenShow(String openShow) {
		this.openShow = openShow;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Integer getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(Integer accountCode) {
		this.accountCode = accountCode;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobile(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getQrId() {
		return qrId;
	}

	public void setQrId(String qrId) {
		this.qrId = qrId;
	}

	public String getIcId() {
		return icId;
	}

	public void setIcId(String icId) {
		this.icId = icId;
	}

	public String getNfcId() {
		return nfcId;
	}

	public void setNfcId(String nfcId) {
		this.nfcId = nfcId;
	}

	public String getFpId() {
		return fpId;
	}

	public void setFpId(String fpId) {
		this.fpId = fpId;
	}

	// public Date getLoginTime() {
	// return loginTime;
	// }
	//
	// public void setLoginTime(Date loginTime) {
	// this.loginTime = loginTime;
	// }

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getBillCount() {
		return billCount;
	}

	public void setBillCount(String billCount) {
		this.billCount = billCount;
	}

	public List<MyCompany> getCompanies() {
		return companies;
	}

	public void setCompanies(List<MyCompany> companies) {
		this.companies = companies;
	}

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

}