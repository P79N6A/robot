package com.bossbutler.exception;
/**
 * 同步名异常
 * @author wangjinliang
 *
 */
public class TransitListException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message; // 异常对应的描述信息
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TransitListException(String message){
		this.message = message;
	}
	public TransitListException() {

	}
}
