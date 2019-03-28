package com.bossbutler.pojo.Information;

/**
 * 名称 :InformationGroup 描述 : 分组列表数据 创建人 : hzhang 创建时间: 2016年10月29日 下午1:11:34
 */
public class InformationGroup {

	private String infoType;
	private String infoTypeName;
	private String infoTitle;
	private String contents;
	private String unreadCount;
	private String createTime;

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getInfoTypeName() {
		return infoTypeName;
	}

	public void setInfoTypeName(String infoTypeName) {
		this.infoTypeName = infoTypeName;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(String unreadCount) {
		this.unreadCount = unreadCount;
	}

}