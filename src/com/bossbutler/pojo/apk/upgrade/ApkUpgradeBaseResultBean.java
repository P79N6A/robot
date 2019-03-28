package com.bossbutler.pojo.apk.upgrade;

/**
 * apk 返回结果基础类
 * 
 * @author horizon
 */
public class ApkUpgradeBaseResultBean {

	private String code;
	private String message;
	private Object data;
	private ApkUpgradeSystemInfo systemInfo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ApkUpgradeSystemInfo getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(ApkUpgradeSystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}

}
