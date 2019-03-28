package com.bossbutler.pojo.robot;

import java.util.Date;
import java.util.List;

import com.bossbutler.pojo.BaseModel;
import com.bossbutler.util.enums.ResourceStatusEnum;

public class RobotRequestModel extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String loginId;// 机器人id
	private String requestType;// 请求类型
	private String requestAddress;// 请求地址
	private String requestParam;// 请求参数
	private String result;// 结果
	private String resultParam;// 返回参数
	private String createTime;//创建时间
	private String requestTime;// 请求时间
	private String reponseTime;// 响应时间
	
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getReponseTime() {
		return reponseTime;
	}
	public void setReponseTime(String reponseTime) {
		this.reponseTime = reponseTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestAddress() {
		return requestAddress;
	}
	public void setRequestAddress(String requestAddress) {
		this.requestAddress = requestAddress;
	}
	public String getRequestParam() {
		return requestParam;
	}
	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultParam() {
		return resultParam;
	}
	public void setResultParam(String resultParam) {
		this.resultParam = resultParam;
	}
	
	
	
	
	
	
}
