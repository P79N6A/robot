package com.bossbutler.pojo.bill;

import java.io.Serializable;
import java.util.Date;
/**
 * 名称 :ServiceEvaluation
 * 描述 :评价表
 * 创建人 :  hzhang
 * 创建时间: 2016年10月26日 上午9:45:40
 */
public class ServiceEvaluation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6615469791374085034L;
	private Long evaluationId;
    private Long billId;
    private Long accountId;
    private int skills;//技能
    private int attitude;//态度
    private int image;//形象
    private int efficient;//效率
    private String remark;//备注说明
    private String statusCode;//状态代码（01，初始化；）
    private Date statusTime;
    private Date createTime;
    private Date updateTime;
    private Long operatorId;
    private Date dataVersion;
    private int score;//星星分数 
    
	public Long getEvaluationId() {
		return evaluationId;
	}
	public void setEvaluationId(Long evaluationId) {
		this.evaluationId = evaluationId;
	}
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public int getSkills() {
		return skills;
	}
	public void setSkills(int skills) {
		this.skills = skills;
	}
	public int getAttitude() {
		return attitude;
	}
	public void setAttitude(int attitude) {
		this.attitude = attitude;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public int getEfficient() {
		return efficient;
	}
	public void setEfficient(int efficient) {
		this.efficient = efficient;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Date getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public Date getDataVersion() {
		return dataVersion;
	}
	public void setDataVersion(Date dataVersion) {
		this.dataVersion = dataVersion;
	}

    
}