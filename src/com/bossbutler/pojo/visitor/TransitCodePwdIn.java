package com.bossbutler.pojo.visitor;

/**
 * 重置密码参数
 * 
 * @author wangqiao
 */
public class TransitCodePwdIn {
	
	public TransitCodePwdIn(String pwd, String accountId) {
		super();
		this.pwd = pwd;
		this.accountId = accountId;
	}

	private String pwd;// 设置的通行密码

	private String accountId;// 用户ID

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
