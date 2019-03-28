package com.bossbutler.pojo;

public class SystemInfoModel {

	private String mac;

	private String dateTime;

	/**
	 * 加密签名
	 * 
	 * @return
	 */
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}