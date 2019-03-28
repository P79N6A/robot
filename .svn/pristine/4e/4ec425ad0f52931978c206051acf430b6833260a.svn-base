package com.bossbutler.pojo;

import com.common.cnnetty.gateway.pojo.EOutIntType;

public class RemoteOpenDoor {

	private String cardCode;
	private String deviceCode;
	private int openSeconds = -1;
	private int oiType;

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public int getOpenSeconds() {
		return openSeconds;
	}

	public void setOpenSeconds(int openSeconds) {
		this.openSeconds = openSeconds;
	}

	public void setOiType(int oiType) {
		this.oiType = oiType;
	}

	public EOutIntType getOiType() {
		if (oiType == 0) {
			return EOutIntType.INT;
		} else if (oiType == 1) {
			return EOutIntType.OUT;
		} else {
			return null;
		}
	}

}
