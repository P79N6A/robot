package com.bossbutler.pojo.apk.upgrade;

public class ApkNoticeDto {

	private String noticeType; // 通知类型 01-强制 02-警告 03-新版本 04-通知
	private String noticeWay; // 通知方式：01-弹窗 02-跑马灯
	private String fontColor; // 字体颜色
	private String noticeContent; // 通知内容

	private Integer displayTime; // 显示时间
	private String downloadPath; // 下载地址
	private String skipPath; // 访问地址
	private String htmlTitle;
	private String title;
	private String versionName;

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeWay() {
		return noticeWay;
	}

	public void setNoticeWay(String noticeWay) {
		this.noticeWay = noticeWay;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Integer getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(Integer displayTime) {
		this.displayTime = displayTime;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getSkipPath() {
		return skipPath;
	}

	public void setSkipPath(String skipPath) {
		this.skipPath = skipPath;
	}

	public String getHtmlTitle() {
		return htmlTitle;
	}

	public void setHtmlTitle(String htmlTitle) {
		this.htmlTitle = htmlTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

}