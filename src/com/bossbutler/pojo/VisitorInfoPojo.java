package com.bossbutler.pojo;

public class VisitorInfoPojo extends BaseModel {
	
	private String name;// 姓名
	private String phone;// 电话号码
	private String company;// 所属公司
	private long accountId;// 邀请人Id
	private String begindate;// 访问开始时间
	private String enddate;// 访问结束时间
	private String beginTime;
	private String endTime;
	private int follows;// 随行人数
	private String regionalId;// 同行区域Id
	private long visitorid;// 访客Id
	private String sourceType;// 数据来源类型
	private long relationid;// 关系ID
	private String projectId;
	private String time;// 访问时间
	private String transitCode;// 通行码（16进制序列）
	private String contents; //邀请传递信息
	private String visitorCode; //邀请码
	private String isMessage;//短信内容。
	private String orgId;
	private String roomNum;//室号
	private String transitDevices;//通行设备集合
	private String remark;
	private String operatorId;
	private String trafficStrategy;
	private String arrangeId;// 节点ID
	private String applyType;
	private Integer limitCount;
	private String projectCode;
	
	
	
	
	

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getArrangeId() {
		return arrangeId;
	}

	public void setArrangeId(String arrangeId) {
		this.arrangeId = arrangeId;
	}

	public String getTrafficStrategy() {
		return trafficStrategy;
	}

	public void setTrafficStrategy(String trafficStrategy) {
		this.trafficStrategy = trafficStrategy;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRegionalId() {
		return regionalId;
	}

	public void setRegionalId(String regionalId) {
		this.regionalId = regionalId;
	}

	public long getAccountId() {
		return accountId;
	}

	public long getVisitorid() {
		return visitorid;
	}

	public int getFollows() {
		return follows;
	}

	public void setFollows(int follows) {
		this.follows = follows;
	}

	public long getRelationid() {
		return relationid;
	}

	public void setRelationid(long relationid) {
		this.relationid = relationid;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public void setVisitorid(long visitorid) {
		this.visitorid = visitorid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getTransitCode() {
		return transitCode;
	}

	public void setTransitCode(String transitCode) {
		this.transitCode = transitCode;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getVisitorCode() {
		return visitorCode;
	}

	public void setVisitorCode(String visitorCode) {
		this.visitorCode = visitorCode;
	}

	public String getIsMessage() {
		return isMessage;
	}

	public void setIsMessage(String isMessage) {
		this.isMessage = isMessage;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getTransitDevices() {
		return transitDevices;
	}

	public void setTransitDevices(String transitDevices) {
		this.transitDevices = transitDevices;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

}
