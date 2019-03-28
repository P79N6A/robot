package com.bossbutler.pojo.regional;

import java.io.Serializable;
import java.util.Date;
/**
 * 设备实体
 * @author zst
 */
public class DeviceQuery implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Long deviceId;
 
    private Long controllerId;
    private Long deviceCode;
    private String deviceName;
    private String deviceType;
     
    private String statusCode;    
   
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getControllerId() {
		return controllerId;
	}

	public void setControllerId(Long controllerId) {
		this.controllerId = controllerId;
	}

	public Long getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Long deviceCode) {
		this.deviceCode = deviceCode;
	}

 
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

    
}