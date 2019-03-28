package com.bossbutler.pojo.apk.upgrade;

/**
 * apk 信息表
 * 
 * @author appClient
 */
public class ApkNoticeVo {

	// private String noticeType; // 通知类型 01-强制 02-警告 03-新版本 04-通知
	// private String noticeWay; // 通知方式：01-弹窗 02-跑马灯
	private String appVersion; // 适应app版本
	private String appSystem; // 设备系统 01：安卓 02：IOS
	private String code; // 应用代码 IBB/BBGJ/GBJN/ZMJN/ZMJO/SHCHJ
	private String accountId;// 帐号ID

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppSystem() {
		return appSystem;
	}

	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}