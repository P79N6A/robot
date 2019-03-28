package com.bossbutler.pojo.system;

import java.util.Date;

import com.bossbutler.pojo.BaseModel;

public class Account extends BaseModel {

	private String accountId;// 账户ID
	private Integer accountCode;// 账户编号
	private String loginId;// 登录名
	private String appId;// App
	private String authenum;
	private String roles;
	private String gender;
	private String loginName;// 昵称
	private String mobilephone;// 移动电话
	private String email;// 电子邮箱
	private String password;// 密码
	private String statusCode;// 01，初始化；02，已验证；03，已认证；04，已禁用；
	private Date loginTime;// 登录时间
	private Date statusTime;// 状态时间
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private String operatorId;// 操作账号ID

	private String dataVersion;// 数据版本
	private String transit_code;
	private String transit_type;
	private String emp_name;
	
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	private String transitId;
	private String begin_date;
	private String end_date;

	public String getTransitId() {
		return transitId;
	}

	public void setTransitId(String transitId) {
		this.transitId = transitId;
	}

	public String getTransit_code() {
		return transit_code;
	}

	public void setTransit_code(String transit_code) {
		this.transit_code = transit_code;
	}

	public String getTransit_type() {
		return transit_type;
	}

	public void setTransit_type(String transit_type) {
		this.transit_type = transit_type;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public Account() {
		super();
	}

	public Account(String accountId, Integer accountCode, String loginId, String mobilephone, String email,
			String password, String statusCode, Date statusTime, Date createTime, Date updateTime, String operatorId,
			String dataVersion) {
		super();
		this.accountId = accountId;
		this.accountCode = accountCode;
		this.loginId = loginId;
		this.mobilephone = mobilephone;
		this.email = email;
		this.password = password;
		this.statusCode = statusCode;
		this.statusTime = statusTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.operatorId = operatorId;
		this.dataVersion = dataVersion;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(String dataVersion) {
		this.dataVersion = dataVersion;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAuthenum() {
		return authenum;
	}

	public void setAuthenum(String authenum) {
		this.authenum = authenum;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}