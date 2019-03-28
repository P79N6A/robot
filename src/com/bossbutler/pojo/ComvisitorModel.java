package com.bossbutler.pojo;

import java.util.ArrayList;
import java.util.List;

import com.bossbutler.pojo.complaint.ComplaintModel;

public class ComvisitorModel {
	List<ComplaintModel> data = new ArrayList<>();
	
	PagePojo page=new PagePojo();
	public List<ComplaintModel> getData() {
		return data;
	}
	public void setData(List<ComplaintModel> data) {
		this.data = data;
	}
	public PagePojo getPage() {
		return page;
	}
	public void setPage(PagePojo page) {
		this.page = page;
	}
	
}
