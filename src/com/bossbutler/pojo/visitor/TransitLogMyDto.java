package com.bossbutler.pojo.visitor;

/**
 * 
 * 名称 :TransitLogDto
 * 描述 :通行日志 访客通行日志
 * 创建人 :  hzhang
 * 创建时间: 2016年11月19日 下午6:56:37
 *
 */
public class TransitLogMyDto{

	
	private String projectName;
	
	private String deviceName;

	private String curDate;
	
	private String curTime;
	
	private String passTypeName;
	
	private String typeName;

    // 设置分组的key，这里就是把你想要分组的key拼起来
    public String groupKey(){
        return this.curDate;
    }
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public String getCurTime() {
		return curTime;
	}

	public void setCurTime(String curTime) {
		this.curTime = curTime;
	}

	public String getPassTypeName() {
		return passTypeName;
	}

	public void setPassTypeName(String passTypeName) {
		this.passTypeName = passTypeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}