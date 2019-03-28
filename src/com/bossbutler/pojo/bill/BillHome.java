package com.bossbutler.pojo.bill;

import java.io.Serializable;
import java.util.List;

import com.bossbutler.pojo.ServiceTypePojo;

public class BillHome implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String projectId;

	private String projectName;

	private int waitCount;

	private int receivedCount;

	private int completeCount;
	
	private int pwaitCount;

	private int preceivedCount;

	private int pcompleteCount;
	

	private String ck;// 查看权限 1.有该权限 0.没有
	private String pd;// 派单权限 1.有该权限 0.没有
	private String jd;// 接单权限 1.有该权限 0.没有

	private List<ServiceTypePojo> billTypeList;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getWaitCount() {
		return waitCount;
	}

	public void setWaitCount(int waitCount) {
		this.waitCount = waitCount;
	}

	public int getReceivedCount() {
		return receivedCount;
	}

	public void setReceivedCount(int receivedCount) {
		this.receivedCount = receivedCount;
	}

	public int getCompleteCount() {
		return completeCount;
	}

	public void setCompleteCount(int completeCount) {
		this.completeCount = completeCount;
	}

	public List<ServiceTypePojo> getBillTypeList() {
		return billTypeList;
	}

	public void setBillTypeList(List<ServiceTypePojo> billTypeList) {
		this.billTypeList = billTypeList;
	}

	public String getCk() {
		return ck;
	}

	public void setCk(String ck) {
		this.ck = ck;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public int getPwaitCount() {
		return pwaitCount;
	}

	public void setPwaitCount(int pwaitCount) {
		this.pwaitCount = pwaitCount;
	}

	public int getPreceivedCount() {
		return preceivedCount;
	}

	public void setPreceivedCount(int preceivedCount) {
		this.preceivedCount = preceivedCount;
	}

	public int getPcompleteCount() {
		return pcompleteCount;
	}

	public void setPcompleteCount(int pcompleteCount) {
		this.pcompleteCount = pcompleteCount;
	}

}