package com.bossbutler.pojo.remote;

/**
 * 设备主控板项目映射类
 * 
 * @author wangqiao
 */
public class DeviceControlModel {

	private String deviceCode;// 设备编号
	private String deviceType;// 设备类型（01，闸机；02，门禁；）
	private String serialNumber;// 设备序列号
	private String channel;// 大豪设备通道号（1,2为门禁；3为闸机；0为迪尔西板子）
	private String controllerCode;// 主控板编号
	private String controllerType;// 主控板类型
	private String dstatusCode;// 设备状态代码（01，初始化；02，已启用；03，挂起；04，故障）
	private String statusCode;// 主控板状态代码（01，初始化；02，已启用；03，挂起；04，故障）
	private String dUplinkStatus;// 设备上行状态（01，在线；02，离线）
	private String dDownlinkStatus;// 设备下行状态（01，在线；02，离线）
	private String cUplinkStatus;// 主控板上行状态（01，在线；02，离线）
	private String cDownlinkStatus;// 主控板下行状态（01，在线；02，离线）
	private String projectId;// 项目id

	/**
	 * 分控板字符串结构
	 * 
	 * 格式controllerCode_controllerType_isOnline_projectId_channel_deviceType
	 * 
	 * @param isOnline
	 *            是否设置为已经上线（对于设备如果主控板和分控板都上线才为上线true；对于主控板只要主控板状态为已经改上线则true）
	 * @return
	 */
	public String getDeviceCacheVal(boolean isOnline) {
		StringBuilder sb = new StringBuilder();
		sb.append(controllerCode);
		sb.append("_");
		sb.append(controllerType);
		sb.append("_");
		sb.append(isOnline);
		sb.append("_");
		sb.append(projectId);
		sb.append("_");
		sb.append(channel);
		sb.append("_");
		sb.append(deviceType);
		return sb.toString();
	}
	
	/**
	 * 分控板序列号字符串结构
	 * 
	 * 格式controllerCode_controllerType_isOnline_projectId_channel_deviceType_deviceCode
	 * 
	 * @param isOnline
	 *            是否设置为已经上线（对于设备如果主控板和分控板都上线才为上线true；对于主控板只要主控板状态为已经改上线则true）
	 * @return
	 */
	public String getDaHaoDeviceSerialNumberCacheVal(boolean isOnline) {
		StringBuilder sb = new StringBuilder();
		sb.append(controllerCode);
		sb.append("_");
		sb.append(controllerType);
		sb.append("_");
		sb.append(isOnline);
		sb.append("_");
		sb.append(projectId);
		sb.append("_");
		sb.append(channel);
		sb.append("_");
		sb.append(deviceType);
		sb.append("_");
		sb.append(deviceCode);
		return sb.toString();
	}
	
	/**
	 * 主控板字符串结构
	 * 
	 * 格式controllerCode_controllerType_isOnline_projectId
	 * 
	 * @param isOnline
	 *            是否设置为已经上线（对于设备如果主控板和分控板都上线才为上线true；对于主控板只要主控板状态为已经改上线则true）
	 * @return
	 */
	public String getControllerCacheVal(boolean isOnline) {
		StringBuilder sb = new StringBuilder();
		sb.append(controllerCode);
		sb.append("_");
		sb.append(controllerType);
		sb.append("_");
		sb.append(isOnline);
		sb.append("_");
		sb.append(projectId);
		return sb.toString();
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getDstatusCode() {
		return dstatusCode;
	}

	public void setDstatusCode(String dstatusCode) {
		this.dstatusCode = dstatusCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getdUplinkStatus() {
		return dUplinkStatus;
	}

	public void setdUplinkStatus(String dUplinkStatus) {
		this.dUplinkStatus = dUplinkStatus;
	}

	public String getdDownlinkStatus() {
		return dDownlinkStatus;
	}

	public void setdDownlinkStatus(String dDownlinkStatus) {
		this.dDownlinkStatus = dDownlinkStatus;
	}

	public String getcUplinkStatus() {
		return cUplinkStatus;
	}

	public void setcUplinkStatus(String cUplinkStatus) {
		this.cUplinkStatus = cUplinkStatus;
	}

	public String getcDownlinkStatus() {
		return cDownlinkStatus;
	}

	public void setcDownlinkStatus(String cDownlinkStatus) {
		this.cDownlinkStatus = cDownlinkStatus;
	}

}
