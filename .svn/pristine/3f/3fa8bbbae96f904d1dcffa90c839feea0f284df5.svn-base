package com.bossbutler.pojo;

import com.common.cnnetty.gateway.pojo.EOutIntType;

/**
 * 扫描二维码开门参数
 * 
 * @author wangqiao
 */
public class ScanQRData {

	private String projectId;// 项目id
	private String deviceCode;// 设备code
	private String deviceType;// 设备类型(01，闸机；02，门禁；)
	private String ioType;// 进出标识(00，进；01，出)

	public ScanQRData(String projectId, String deviceCode, String deviceType, String ioType) {
		super();
		this.projectId = projectId;
		this.deviceCode = deviceCode;
		this.deviceType = deviceType;
		this.ioType = ioType;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	public EOutIntType getIoType() {
		if ("00".equals(ioType)) {
			return EOutIntType.INT;
		} else if ("01".equals(ioType)) {
			return EOutIntType.OUT;
		} else {
			return null;
		}
	}
}
