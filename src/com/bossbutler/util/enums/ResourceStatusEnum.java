package com.bossbutler.util.enums;

public enum ResourceStatusEnum {

	Initialization("初始化", "01"), //
	Release("已发布", "02"), //
	Revoke("已撤销", "03"), //
	Disable("已禁用", "04");

	private ResourceStatusEnum(String name, String index) {
		this.name = name;
		this.index = index;
	}

	public static String getName(String index) {
		for (ResourceStatusEnum c : ResourceStatusEnum.values()) {
			if (c.getIndex().equals(index)) {
				return c.name;
			}
		}
		return null;

	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String index;

	private String name;

}
