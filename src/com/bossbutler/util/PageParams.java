package com.bossbutler.util;

public class PageParams {
	
	private int pageNo; // 当前页
	private int pageSize; // 数据条数


	public int getPageNo() {
		if (pageNo == 0) {
			return 1;
		}
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		if (pageSize == 0) {
			return 10;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
