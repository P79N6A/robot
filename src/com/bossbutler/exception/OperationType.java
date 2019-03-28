package com.bossbutler.exception;

/**
 * <p>
 * 操作类型.
 * </p>
 * 
 * @author wangqiao
 *
 */
public enum OperationType {

	ADD("添加数据"), DELETE("删除数据"), UPDATE("更改数据"), QUERY("查询数据");

	private String name;

	private OperationType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
