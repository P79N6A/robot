package com.bossbutler.common;

import java.io.Serializable;

/**
 * <p>
 * 系统设置，配置文件上传访问信息.
 * </p>
 * 
 * @author wq
 * 
 */
public class InitConf implements Serializable {

	private static final long serialVersionUID = -3079207348737878899L;

	private static String selfhost;
	private static String selftmpsavepath;
	private static String selfimgpath;
	private static String filesystemhost;
	private static String fmuploadtmppath;
	private static String fmuploadtmp2filepath;
	private static String fmloadpath;
	private static String fmuploadpath;

	private static String dahaoSupplierNameMult;

	private static String selfvisitorapplypath;
	private static String idworkDistrictField;
	private static String idworkStationField;
	// 远程开门重发参数
	private static int rodoorTimeoutseconds;
	private static int rodoorResendlimit;
	private static int rodoorOpenseconds;
	// 默认远程开门每个人独占时长默认60秒
	private static int rodooropenlockseconds;
	// 默认远程开门超时时间1秒
	private static final int RODOOR_TIMEOUT_SECONDS = 1;
	// 默认远程开门不重发
	private static final int RODOOR_RESEND_lIMIT = 0;
	// 默认远程开门保持时长2秒
	private static final int RODOOR_OPEN_RESENDS = 2;
	// 默认远程开门每个人独占时长默认60秒
	private static final int RODOOR_OPENLOCK_RESENDS = 60;

	// 手机app是否需要定位功能
	private static int appLocationFlag;
	
	// 手机app定位功能偏移量
	private static int appLocationOffset;

	// 消息推送平台host
	private static String msgMqpmHost;
	// 消息推送平台rest请求token
	private static String msgMqpmHostRestTransitToken;
	
	// 是否保存升级请求01,保存；02，不保存；
	private static String appUpgradeLog;
	
	

	public static String getAppUpgradeLog() {
		return appUpgradeLog;
	}

	public static void setAppUpgradeLog(String appUpgradeLog) {
		InitConf.appUpgradeLog = appUpgradeLog;
	}

	public static String getSelfhost() {
		return selfhost;
	}

	public void setSelfhost(String selfhost) {
		InitConf.selfhost = selfhost;
	}

	public static String getSelftmpsavepath() {
		return selftmpsavepath;
	}

	public void setSelftmpsavepath(String selftmpsavepath) {
		InitConf.selftmpsavepath = selftmpsavepath;
	}

	public static String getSelfimgpath() {
		return selfimgpath;
	}

	public void setSelfimgpath(String selfimgpath) {
		InitConf.selfimgpath = selfimgpath;
	}

	public static String getFilesystemhost() {
		return filesystemhost;
	}

	public void setFilesystemhost(String filesystemhost) {
		InitConf.filesystemhost = filesystemhost;
	}

	public static String getFmuploadtmppath() {
		return fmuploadtmppath;
	}

	public void setFmuploadtmppath(String fmuploadtmppath) {
		InitConf.fmuploadtmppath = fmuploadtmppath;
	}

	public static String getFmloadpath() {
		return fmloadpath;
	}

	public void setFmloadpath(String fmloadpath) {
		InitConf.fmloadpath = fmloadpath;
	}

	public static String getFmuploadtmp2filepath() {
		return fmuploadtmp2filepath;
	}

	public static void setFmuploadtmp2filepath(String fmuploadtmp2filepath) {
		InitConf.fmuploadtmp2filepath = fmuploadtmp2filepath;
	}

	public static String getFmuploadpath() {
		return fmuploadpath;
	}

	public void setFmuploadpath(String fmuploadpath) {
		InitConf.fmuploadpath = fmuploadpath;
	}

	public static String getSelfvisitorapplypath() {
		return selfvisitorapplypath;
	}

	public static void setSelfvisitorapplypath(String selfvisitorapplypath) {
		InitConf.selfvisitorapplypath = selfvisitorapplypath;
	}

	public static int getRodoorTimeoutseconds() {
		if (rodoorTimeoutseconds == 0) {
			rodoorTimeoutseconds = RODOOR_TIMEOUT_SECONDS;
		}
		return rodoorTimeoutseconds;
	}

	public void setRodoorTimeoutseconds(int rodoorTimeoutseconds) {
		InitConf.rodoorTimeoutseconds = rodoorTimeoutseconds;
	}

	public static int getRodoorResendlimit() {
		return rodoorResendlimit == 0 ? RODOOR_RESEND_lIMIT : rodoorResendlimit;
	}

	public void setRodoorResendlimit(int rodoorResendlimit) {
		InitConf.rodoorResendlimit = rodoorResendlimit;
	}

	public static int getRodoorOpenseconds() {
		return rodoorOpenseconds == 0 ? RODOOR_OPEN_RESENDS : rodoorOpenseconds;
	}

	public void setRodoorOpenseconds(int rodoorOpenseconds) {
		InitConf.rodoorOpenseconds = rodoorOpenseconds;
	}

	public static int getRodooropenlockseconds() {
		if (rodooropenlockseconds == 0) {
			int seconds = getRodoorTimeoutseconds() * getRodoorResendlimit();
			rodooropenlockseconds = (seconds < RODOOR_OPENLOCK_RESENDS ? RODOOR_OPENLOCK_RESENDS : seconds);
		}
		return rodooropenlockseconds;
	}

	public static String getIdworkDistrictField() {
		return idworkDistrictField;
	}

	public void setIdworkDistrictField(String idworkDistrictField) {
		InitConf.idworkDistrictField = idworkDistrictField;
	}

	public static String getIdworkStationField() {
		return idworkStationField;
	}

	public void setIdworkStationField(String idworkStationField) {
		InitConf.idworkStationField = idworkStationField;
	}

	public static String getDahaoSupplierNameMult() {
		return dahaoSupplierNameMult;
	}

	public void setDahaoSupplierNameMult(String dahaoSupplierNameMult) {
		InitConf.dahaoSupplierNameMult = dahaoSupplierNameMult;
	}

	public static int getAppLocationFlag() {
		return appLocationFlag;
	}

	public void setAppLocationFlag(int appLocationFlag) {
		InitConf.appLocationFlag = appLocationFlag;
	}

	public static int getAppLocationOffset() {
		return appLocationOffset;
	}

	public void setAppLocationOffset(int appLocationOffset) {
		InitConf.appLocationOffset = appLocationOffset;
	}

	public static String getMsgMqpmHost() {
		return msgMqpmHost;
	}

	public void setMsgMqpmHost(String msgMqpmHost) {
		InitConf.msgMqpmHost = msgMqpmHost;
	}

	public static String getMsgMqpmHostRestTransitToken() {
		return msgMqpmHostRestTransitToken;
	}

	public void setMsgMqpmHostRestTransitToken(String msgMqpmHostRestTransitToken) {
		InitConf.msgMqpmHostRestTransitToken = msgMqpmHostRestTransitToken;
	}

}
