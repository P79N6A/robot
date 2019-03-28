package com.bossbutler.exception;

/**
 * <p>
 * 调度异常
 * </p>
 * 
 * @author wangqiao
 *
 */
public class QuartzException extends Exception {

	private static final long serialVersionUID = 1L;

	private String retCd; // 异常对应的返回码
	private String msgDes; // 异常对应的描述信息

	public QuartzException() {
		super();
	}

	public QuartzException(String message) {
		super(message);
		msgDes = message;
	}

	public QuartzException(String retCd, String msgDes) {
		super();
		this.retCd = retCd;
		this.msgDes = msgDes;
	}

	public String getRetCd() {
		return retCd;
	}

	public String getMsgDes() {
		return msgDes;
	}
}
