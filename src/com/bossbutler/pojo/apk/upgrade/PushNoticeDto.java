package com.bossbutler.pojo.apk.upgrade;

/**
 * 推送消息类
 * 
 * @author horizon
 */
public class PushNoticeDto {

	private String infoId;
	private String title;
	private String contentJson;// 消息体重type为03，0101,0202
	private String createTime;// 创建时间
	private int readed;// 1为未读，0为已读

	public PushNoticeDto() {
		super();
	}

	public PushNoticeDto(String infoId, String title, String contentJson, String createTime) {
		super();
		this.infoId = infoId;
		this.title = title;
		this.contentJson = contentJson;
		this.createTime = createTime;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentJson() {
		return contentJson;
	}

	public void setContentJson(String contentJson) {
		this.contentJson = contentJson;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getReaded() {
		return readed;
	}

	public void setReaded(int readed) {
		this.readed = readed;
	}

}
