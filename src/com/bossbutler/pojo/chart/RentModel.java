package com.bossbutler.pojo.chart;

import java.io.Serializable;

public class RentModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String billId;
	private String beginDate;
	private String endDate;
	private String floorageArea;
	private String contractId;
	private String rentDaysAmount;
	public String getBillId() {
		return billId;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getFloorageArea() {
		return floorageArea;
	}
	public String getContractId() {
		return contractId;
	}
	public String getRentDaysAmount() {
		return rentDaysAmount;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setFloorageArea(String floorageArea) {
		this.floorageArea = floorageArea;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setRentDaysAmount(String rentDaysAmount) {
		this.rentDaysAmount = rentDaysAmount;
	}
	
	
}
