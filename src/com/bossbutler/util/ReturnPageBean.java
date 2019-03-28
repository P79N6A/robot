package com.bossbutler.util;

import java.util.ArrayList;
import java.util.List;

public class ReturnPageBean<T> {
	
	List<T> data = new ArrayList<>();
	ReturnPage page = new ReturnPage();

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public ReturnPage getPage() {
		return page;
	}
	public void setPage(ReturnPage page) {
		this.page = page;
	}

}
