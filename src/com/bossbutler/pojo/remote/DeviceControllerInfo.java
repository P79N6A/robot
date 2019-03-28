package com.bossbutler.pojo.remote;

/**
 * 远程设备信息
 * 
 * @author wangqiao
 */
public class DeviceControllerInfo {

	private String controllerCode;
	private String controllerType;
	private int channel;
	private String projectId;

	public DeviceControllerInfo(String controllerCode, String controllerType, int channel, String projectId) {
		super();
		this.controllerCode = controllerCode;
		this.controllerType = controllerType;
		this.channel = channel;
		this.projectId = projectId;
	}

	public String getControllerCode() {
		return controllerCode;
	}

	public void setControllerCode(String controllerCode) {
		this.controllerCode = controllerCode;
	}

	public String getControllerType() {
		return controllerType;
	}

	public void setControllerType(String controllerType) {
		this.controllerType = controllerType;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
