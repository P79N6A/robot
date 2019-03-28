package com.bossbutler.pojo.regional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @ClassName: VisitorApplyContents
 * @Description: 邀请访客项目设备编号。
 * @author hzhang
 * @date 2016年9月1日 下午10:07:45
 */
 
@JsonInclude(Include.NON_NULL)
public class VisitorApplyContents {
	/**
	 * ID号  通行编号
	 */
	private String idCode;
	 /**
	  * 项目编号设备编号的前2字节
	  */
	 private String devicePre;
	/**
	 * 设备有效时间组 开门权限（编号及时间剩余）
	 */
	private String openAccess;
	/**
	 * 系统同步码
	 */
	private String synchCode;
	/**
	 * 出入地址
	 */
	private String address;
	/**
	 * 房间名称
	 */
	private String arrangeName;
	/**
	 * 二维码凭证
	 * @return
	 */
	private String visitorCode;
	public String getVisitorCode() {
		return visitorCode;
	}
	public void setVisitorCode(String visitorCode) {
		this.visitorCode = visitorCode;
	}
	public String getArrangeName() {
		return arrangeName;
	}
	public void setArrangeName(String arrangeName) {
		this.arrangeName = arrangeName;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getDevicePre() {
		return devicePre;
	}
	public void setDevicePre(String devicePre) {
		this.devicePre = devicePre;
	}
	public String getOpenAccess() {
		return openAccess;
	}
	public void setOpenAccess(String openAccess) {
		this.openAccess = openAccess;
	}
	public String getSynchCode() {
		return synchCode;
	}
	public void setSynchCode(String synchCode) {
		this.synchCode = synchCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
