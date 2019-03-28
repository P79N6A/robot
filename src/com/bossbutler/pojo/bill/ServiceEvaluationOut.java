package com.bossbutler.pojo.bill;

public class ServiceEvaluationOut {
	
	private String accountId;
	private String headpath;// 头像
	private String name;// 姓名
	private String serverCode;//服务单号
	private String serverTitle;//服务标题
	private String phone;// 电话
	private String score;// 得分
	private String billCount;//接单数
	
    private int skills;//技能
    private int attitude;//态度
    private int image;//形象
    private int efficient;//效率
    private String remark;//备注说明
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getHeadpath() {
		return headpath;
	}
	public void setHeadpath(String headpath) {
		this.headpath = headpath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getServerCode() {
		return serverCode;
	}
	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}
	public String getServerTitle() {
		return serverTitle;
	}
	public void setServerTitle(String serverTitle) {
		this.serverTitle = serverTitle;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getBillCount() {
		return billCount;
	}
	public void setBillCount(String billCount) {
		this.billCount = billCount;
	}
	public int getSkills() {
		return skills;
	}
	public void setSkills(int skills) {
		this.skills = skills;
	}
	public int getAttitude() {
		return attitude;
	}
	public void setAttitude(int attitude) {
		this.attitude = attitude;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public int getEfficient() {
		return efficient;
	}
	public void setEfficient(int efficient) {
		this.efficient = efficient;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
