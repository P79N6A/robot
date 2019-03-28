package com.bossbutler.pojo;
/**
 * 手机通用返回结果bean
 * @author hzhang
 * 
 */
public class ResultBean {
	
	private boolean isError = false;    
	private int errorType; 
	private MobileSystemParams sysParams;
	private String errorMessage;
	private Object result;
	
	public ResultBean(){
		super();
	}
	
	public ResultBean(boolean isError, int errorType, String errorMessage,
			Object result,String oldToken, String token, String nocache, String mac) {
		super();
		MobileSystemParams sysParams = new MobileSystemParams(oldToken, token, nocache, mac);
		this.isError = isError;
		this.errorType = errorType;
		this.sysParams = sysParams;
		this.errorMessage = errorMessage;
		this.result = result;
	}
	
	public ResultBean(boolean isError, int errorType, MobileSystemParams sysParams, String errorMessage,
			Object result) {
		super();
		this.isError = isError;
		this.errorType = errorType;
		this.sysParams = sysParams;
		this.errorMessage = errorMessage;
		this.result = result;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public MobileSystemParams getSysParams() {
		return sysParams;
	}
	public void setSysParams(MobileSystemParams sysParams) {
		this.sysParams = sysParams;
	} 
	
}
