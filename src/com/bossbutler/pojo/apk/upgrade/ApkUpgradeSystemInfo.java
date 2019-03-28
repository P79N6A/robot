package com.bossbutler.pojo.apk.upgrade;

public class ApkUpgradeSystemInfo {

	private String mac;
	private String dateTime;

	public ApkUpgradeSystemInfo(String mac, String dateTime) {
		super();
		this.mac = mac;
		this.dateTime = dateTime;
	}

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
