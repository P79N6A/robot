package com.bossbutler.pojo;

public class BaseModel {
	private String pageNo;//当前页
	private String pageSize;//每页的条数
	
	private java.lang.Integer numId=0;
	
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public java.lang.Integer getNumId() {
		return numId;
	}
	public void setNumId(java.lang.Integer numId) {
		this.numId = numId;
	}
}