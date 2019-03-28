package com.bossbutler.pojo;

import org.apache.ibatis.session.RowBounds;

public class PagingBounds extends RowBounds {

	// 总记录数
	private int total;

	// 查询的起始位置
	private int offset;

	// 查询多少行记录
	private int limit;
	
	// 当前页
	private int page;

	// 每页多少行
	private int rows;

	public PagingBounds() {
	}

	public PagingBounds(int offset, int limit,int page,int rows) {
		super(offset, limit);
		this.offset = offset;
		this.limit = limit;
		this.page = page;
		this.rows = rows;
	}

	public void setMeToDefault(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
