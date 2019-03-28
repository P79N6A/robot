package com.bossbutler.pojo.org;

public class OrgMediaModel implements java.io.Serializable{

	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "OrgMedia";
	public static final String ALIAS_MEDIA_ID = "媒体ID";
	public static final String ALIAS_ORG_ID = "组织ID";
	public static final String ALIAS_ORDINAL = "序号（用于调序）";
	public static final String ALIAS_MAIN_CLASSIFY = "媒体主类";
	public static final String ALIAS_MEDIA_TYPE = "媒体类别";
	public static final String ALIAS_FILE_TYPE = "文件类型";
	public static final String ALIAS_MEDIA_NO = "资质号码";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_OPERATOR_ID = "操作账号ID";
	public static final String ALIAS_DATA_VERSION = "数据版本";
	
	
	//@Length(max=50)
	private java.lang.String mediaId;
	//@Length(max=50)
	private java.lang.String orgId;
	
	private java.lang.Integer ordinal;
	//@Length(max=5)
	private java.lang.String mainClassify;
	//@Length(max=5)
	private java.lang.String mediaType;
	//@Length(max=20)
	private java.lang.String fileType;
	//@Length(max=50)
	private java.lang.String mediaNo;
	
	private java.util.Date createTime;
	
	private java.util.Date updateTime;
	
	private java.lang.Long operatorId;
	
	private String fileUrl;
	
	private String OrgStatusCode;
	
	private String createTimeStr;
	
	
	public java.lang.String getMediaId() {
		return mediaId;
	}

	public void setMediaId(java.lang.String mediaId) {
		this.mediaId = mediaId;
	}

	public java.lang.String getOrgId() {
		return orgId;
	}

	public void setOrgId(java.lang.String orgId) {
		this.orgId = orgId;
	}

	public java.lang.Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(java.lang.Integer ordinal) {
		this.ordinal = ordinal;
	}

	public java.lang.String getMainClassify() {
		return mainClassify;
	}

	public void setMainClassify(java.lang.String mainClassify) {
		this.mainClassify = mainClassify;
	}

	public java.lang.String getMediaType() {
		return mediaType;
	}

	public void setMediaType(java.lang.String mediaType) {
		this.mediaType = mediaType;
	}

	public java.lang.String getFileType() {
		return fileType;
	}

	public void setFileType(java.lang.String fileType) {
		this.fileType = fileType;
	}

	public java.lang.String getMediaNo() {
		return mediaNo;
	}

	public void setMediaNo(java.lang.String mediaNo) {
		this.mediaNo = mediaNo;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(java.lang.Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getOrgStatusCode() {
		return OrgStatusCode;
	}

	public void setOrgStatusCode(String orgStatusCode) {
		OrgStatusCode = orgStatusCode;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}


	
	
}