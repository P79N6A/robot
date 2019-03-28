package com.bossbutler.jms;

/**
 * 队列枚举
 * 
 * @author wangqiao
 */
public enum MqQueueEnumAppServer {

	SYNCH_REMOTE_OPEN_DOOR("gateway_message_synch_remote_open_door", "迪尔西远程开门队列"),// 迪尔西远程开门队列
	SYNCH_DAHAO_REMOTE_OPEN_DOOR("dahao_message_synch_remote_open_door", "大豪远程开门队列"),// 大豪远程开门队列
	SYNCH_DAHAO_REMOTE_CHANGE_OPEN("dahao_message_synch_remote_change_open", "大豪远程常开或取消常开门禁闸机队列"),// 大豪远程常开或取消常开门禁闸机队列
	SYNCH_DAHAO_SCAN_QR_REMOTE_CHANGE_OPEN("dahao_message_synch_scan_qr_remote_change_open", "大豪远程扫描二维码开门禁闸机队列"),// 大豪远程扫描二维码开门禁闸机队列
	SYNCH_DERXI_SCAN_QR_REMOTE_CHANGE_OPEN("derxi_message_synch_scan_qr_remote_change_open", "迪尔西远程扫描二维码开门禁闸机队列");// 迪尔西远程扫描二维码开门禁闸机队列

	private MqQueueEnumAppServer(String name, String describe) {
		this.name = name;
		this.describe = describe;
	}

	private String name;
	private String describe;

	public String getName() {
		return name;
	}

	public String getDescribe() {
		return describe;
	}

}
