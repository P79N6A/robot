package com.bossbutler.pojo.chart;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

public class ChargingOverview  implements Serializable{

	/**
	 * 收费概况
	 */
	 
	private static final long serialVersionUID = 1L;
	private String payRentCurrent; // 本期实收
	private String payRentPast; // 去年同期
	private String payRentMonth; //本月实收
	private String payRentExpect; // 应收金额
	private String payRate;// 实收比
	private String otherFeesCurrent; //其他费用
	private String otherFeesPast; //去年同期
	private String monthPercent;//这个月实收的占应该收的百分比
	
	@JsonIgnore
	private String peicFee;//赔偿金
	@JsonIgnore
	private String famFee;//罚没金
	@JsonIgnore
	private String zhinNFee;//滞纳金
	@JsonIgnore
	private String weiyFee;//违约金
	@JsonIgnore
	private String bucFee;//补偿金
	private List<String> chartNames;
	private List<RptModel> chartData;
	
	
	private String chargeName;//费目
	private String chatgeId;//费目id
	private String amount;// 金额
	
	
	public String getChargeName() {
		return chargeName;
	}
	public String getChatgeId() {
		return chatgeId;
	}
	public String getAmount() {
		return amount;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public void setChatgeId(String chatgeId) {
		this.chatgeId = chatgeId;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayRentExpect() {
		return payRentExpect;
	}
	public void setPayRentExpect(String payRentExpect) {
		this.payRentExpect = payRentExpect;
	}
	public String getPayRate() {
		return payRate;
	}
	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}
	public String getMonthPercent() {
		return monthPercent;
	}
	public void setMonthPercent(String monthPercent) {
		this.monthPercent = monthPercent;
	}

	public String getPayRentCurrent() {
		return payRentCurrent;
	}
	public List<String> getChartNames() {
		return chartNames;
	}
	public void setChartNames(List<String> chartNames) {
		this.chartNames = chartNames;
	}
	public List<RptModel> getChartData() {
		return chartData;
	}
	public void setChartData(List<RptModel> chartData) {
		this.chartData = chartData;
	}
	public void setPayRentCurrent(String payRentCurrent) {
		this.payRentCurrent = payRentCurrent;
	}
	public String getPayRentPast() {
		return payRentPast;
	}
	public void setPayRentPast(String payRentPast) {
		this.payRentPast = payRentPast;
	}
	public String getPayRentMonth() {
		return payRentMonth;
	}
	public void setPayRentMonth(String payRentMonth) {
		this.payRentMonth = payRentMonth;
	}

	public String getOtherFeesCurrent() {
		return otherFeesCurrent;
	}
	public void setOtherFeesCurrent(String otherFeesCurrent) {
		this.otherFeesCurrent = otherFeesCurrent;
	}
	public String getOtherFeesPast() {
		return otherFeesPast;
	}
	public void setOtherFeesPast(String otherFeesPast) {
		this.otherFeesPast = otherFeesPast;
	}
	public String getPeicFee() {
		return peicFee;
	}
	public void setPeicFee(String peicFee) {
		this.peicFee = peicFee;
	}
	public String getFamFee() {
		return famFee;
	}
	public void setFamFee(String famFee) {
		this.famFee = famFee;
	}
	public String getZhinNFee() {
		return zhinNFee;
	}
	public void setZhinNFee(String zhinNFee) {
		this.zhinNFee = zhinNFee;
	}
	public String getWeiyFee() {
		return weiyFee;
	}
	public void setWeiyFee(String weiyFee) {
		this.weiyFee = weiyFee;
	}
	public String getBucFee() {
		return bucFee;
	}
	public void setBucFee(String bucFee) {
		this.bucFee = bucFee;
	}

}
