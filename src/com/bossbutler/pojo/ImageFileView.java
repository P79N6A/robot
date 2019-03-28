package com.bossbutler.pojo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileView {


	
	
	private static final long serialVersionUID = 1L;

	private String data;

	private String token;

	private String mac;

	private String dateTime;
	
	
	private List<MultipartFile> fileBytes;


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
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


	public List<MultipartFile> getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(List<MultipartFile> fileBytes) {
		this.fileBytes = fileBytes;
	}
	
}
