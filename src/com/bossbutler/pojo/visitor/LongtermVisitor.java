package com.bossbutler.pojo.visitor;

import com.bossbutler.pojo.ImageInfoPojo;
/**
 * 长期访客访客参数
 * @author hzhang
 */
public class LongtermVisitor extends ImageInfoPojo {

	private String name;// 姓名
	private String phone;// 电话号码
	private String remarks;//备注信息
	private String applyType;//长期访客类型
	private String regionalNames;//通行区域名称列表
	private String icCardNo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRegionalNames() {
		return regionalNames;
	}
	public void setRegionalNames(String regionalNames) {
		this.regionalNames = regionalNames;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getIcCardNo() {
		return icCardNo;
	}
	public void setIcCardNo(String icCardNo) {
		this.icCardNo = icCardNo;
	}
	
}
