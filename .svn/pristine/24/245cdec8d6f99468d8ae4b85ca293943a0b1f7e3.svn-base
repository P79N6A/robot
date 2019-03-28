package com.bossbutler.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MobileSystemParams {
	public static final SimpleDateFormat datef = new SimpleDateFormat("yyyyMMdd:HHmmss");
	private String date = datef.format(new Date()).split(":")[0];//字符串（YYYYMMdd）6位长度
	private String time = datef.format(new Date()).split(":")[1];//字符串（HHmmss）6位长度
	private String oldToken;//移动端保留的token码，如果没有为空串
	private String token;//登录新token
	private String nocache;//防止缓存随机数（同时用户加密）
	private String mac;//加密后的md5值
	
	
	public MobileSystemParams(String oldToken, String token, String nocache, String mac) {
		super();
		this.oldToken = oldToken;
		this.token = token;
		this.nocache = nocache;
		this.mac = mac;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOldToken() {
		return oldToken;
	}
	public void setOldToken(String oldToken) {
		this.oldToken = oldToken;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNocache() {
		return nocache;
	}
	public void setNocache(String nocache) {
		this.nocache = nocache;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	
}
