package com.bossbutler.pojo;

import java.util.Date;

public class ImagePojo {
	private String mediaId;
	private String accountId;
	private String mediaType;// 媒体类别
	private String fileType;// 文件类别
	private String mainClassify;
	private Date createtime;// 创建时间
	private Date updatetime;// 更新时间
	private String ordinal;// 排列序号

	public String getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(String ordinal) {
		this.ordinal = ordinal;
	}

	public String getMainClassify() {
		return mainClassify;
	}

	public void setMainClassify(String mainClassify) {
		this.mainClassify = mainClassify;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}
