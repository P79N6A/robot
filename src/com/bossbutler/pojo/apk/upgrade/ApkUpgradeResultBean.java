package com.bossbutler.pojo.apk.upgrade;

import com.bossbutler.util.MacConfirm;
import com.company.exception.AppException;
import com.company.util.BeanUtility;

/**
 * apk 升级返回结果
 * 
 * @author horizon
 */
public class ApkUpgradeResultBean extends ApkUpgradeBaseResultBean {

	public ApkUpgradeResultBean() {
		super();
	}

	public ApkUpgradeResultBean setMessageCodeData(String message, String code, Object resultData) {
		this.setMessage(message);
		this.setCode(code);
		this.setData(resultData);
		return this;
	}

	public ApkUpgradeResultBean setMessageCodeDataSign(String message, String code, Object resultData)
			throws AppException {
		this.setMessage(message);
		this.setCode(code);
		if ("000000000".equals(code)) {
			String data;
			if (!(resultData instanceof String)) {
				data = BeanUtility.objectToJsonStr(resultData, true);
			} else {
				data = (String) resultData;
			}
			String dateTime = Long.toString(System.currentTimeMillis());
			this.setSystemInfo(new ApkUpgradeSystemInfo(MacConfirm.getMacForMobile(data, dateTime), dateTime));
			this.setData(data);
		} else {
			this.setData(resultData);
		}
		return this;
	}

}
