package com.bossbutler.pojo.chart;

import java.io.Serializable;

/*
 * 租赁概况
 */
public class LeasingOverview implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String floorage_area  ;/*总面积*/
	private	String available_area ;/*使用面积*/ 
	private	String before_available_area ;/*去年使用面积*/
	private	String endContranct ;/*本年到期合同*/
	private	String endMContranct ;/*本月到期合同*/
	private	String bookContranct ;/*本年签定合同*/
	private	String bookMContranct ;/*本年签定合同*/
	private	String customerNum ;/*租户总数*/
	private	String addCustomerNum ;/*入驻客户数*/
	private	String addMCustomerNum ;/*月入驻客户数*/
	private	String subCustomerNum ;/*退租客户数*/
	private	String subMCustomerNum ;/*本月退租客户数*/
	private	String rentQuote ;/*租金均价*/
	public String getFloorage_area() {
		return floorage_area;
	}
	public void setFloorage_area(String floorage_area) {
		this.floorage_area = floorage_area;
	}
	public String getAvailable_area() {
		return available_area;
	}
	public void setAvailable_area(String available_area) {
		this.available_area = available_area;
	}
	public String getBefore_available_area() {
		return before_available_area;
	}
	public void setBefore_available_area(String before_available_area) {
		this.before_available_area = before_available_area;
	}
	public String getEndContranct() {
		return endContranct;
	}
	public void setEndContranct(String endContranct) {
		this.endContranct = endContranct;
	}
	public String getEndMContranct() {
		return endMContranct;
	}
	public void setEndMContranct(String endMContranct) {
		this.endMContranct = endMContranct;
	}
	public String getBookContranct() {
		return bookContranct;
	}
	public void setBookContranct(String bookContranct) {
		this.bookContranct = bookContranct;
	}
	public String getBookMContranct() {
		return bookMContranct;
	}
	public void setBookMContranct(String bookMContranct) {
		this.bookMContranct = bookMContranct;
	}
	public String getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}
	public String getAddCustomerNum() {
		return addCustomerNum;
	}
	public void setAddCustomerNum(String addCustomerNum) {
		this.addCustomerNum = addCustomerNum;
	}
	public String getAddMCustomerNum() {
		return addMCustomerNum;
	}
	public void setAddMCustomerNum(String addMCustomerNum) {
		this.addMCustomerNum = addMCustomerNum;
	}
	public String getSubCustomerNum() {
		return subCustomerNum;
	}
	public void setSubCustomerNum(String subCustomerNum) {
		this.subCustomerNum = subCustomerNum;
	}
	public String getSubMCustomerNum() {
		return subMCustomerNum;
	}
	public void setSubMCustomerNum(String subMCustomerNum) {
		this.subMCustomerNum = subMCustomerNum;
	}
	public String getRentQuote() {
		return rentQuote;
	}
	public void setRentQuote(String rentQuote) {
		this.rentQuote = rentQuote;
	}
	
}
