package com.bossbutler.pojo;

public class PagingBoundsView {
	// 当前页
	private int page;

	// 每页多少行
	private int rows;

	public int getPage() {
		if(page <= 0) {
			return 1;
		}
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		if(rows <= 0) {
			return 10;
		}
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public PagingBounds buiderPagingBounds() {
		if(rows <= 0) {
			rows =  10;
		}
		if(page <= 0) {
			page = 1;
		}
		int offset = (this.page - 1) * this.rows;
		int limit = this.rows;
		return new PagingBounds(offset, limit,this.page,this.rows);
	}
}
