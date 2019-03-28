package com.bossbutler.pojo.regional;

import java.util.ArrayList;
import java.util.List;

public class RegionalModel {



	private java.lang.Long regional_id;
	
	private java.lang.Long org_id;
	
	private java.lang.Long supper_id;
	
	private java.lang.String regional_type;
	
	private java.lang.String regional_name;
	
	private java.lang.Long regional_code;
	
	private java.lang.Long devices;
	
	private java.lang.String status_code;

	private java.lang.String status_time;

	private java.lang.String create_time;

	private java.lang.String update_time;

	private java.lang.Long operator_id;

	private java.lang.String data_version;
	
	private java.lang.Long project_id;
	
	public java.lang.String arrange_type;
	
	private java.lang.Long terminal_id;
	
	private List<Device> deviceList = new ArrayList<>();
	

	public java.lang.Long getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(java.lang.Long terminal_id) {
		this.terminal_id = terminal_id;
	}

	public java.lang.String getArrange_type() {
		return arrange_type;
	}

	public void setArrange_type(java.lang.String arrange_type) {
		this.arrange_type = arrange_type;
	}

	public java.lang.Long getProject_id() {
		return project_id;
	}

	public void setProject_id(java.lang.Long project_id) {
		this.project_id = project_id;
	}

	public java.lang.Long getRegional_id() {
		return regional_id;
	}

	public void setRegional_id(java.lang.Long regional_id) {
		this.regional_id = regional_id;
	}

	public java.lang.Long getOrg_id() {
		return org_id;
	}

	public void setOrg_id(java.lang.Long org_id) {
		this.org_id = org_id;
	}

	public java.lang.Long getSupper_id() {
		return supper_id;
	}

	public void setSupper_id(java.lang.Long supper_id) {
		this.supper_id = supper_id;
	}

	public java.lang.String getRegional_type() {
		return regional_type;
	}

	public void setRegional_type(java.lang.String regional_type) {
		this.regional_type = regional_type;
	}

	public java.lang.String getRegional_name() {
		return regional_name;
	}

	public void setRegional_name(java.lang.String regional_name) {
		this.regional_name = regional_name;
	}

	public java.lang.Long getRegional_code() {
		return regional_code;
	}

	public void setRegional_code(java.lang.Long regional_code) {
		this.regional_code = regional_code;
	}

	public java.lang.Long getDevices() {
		return devices;
	}

	public void setDevices(java.lang.Long devices) {
		this.devices = devices;
	}

	public java.lang.String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(java.lang.String status_code) {
		this.status_code = status_code;
	}

	public java.lang.String getStatus_time() {
		return status_time;
	}

	public void setStatus_time(java.lang.String status_time) {
		this.status_time = status_time;
	}

	public java.lang.String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(java.lang.String create_time) {
		this.create_time = create_time;
	}

	public java.lang.String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(java.lang.String update_time) {
		this.update_time = update_time;
	}

	public java.lang.Long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(java.lang.Long operator_id) {
		this.operator_id = operator_id;
	}

	public java.lang.String getData_version() {
		return data_version;
	}

	public void setData_version(java.lang.String data_version) {
		this.data_version = data_version;
	}

	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
}
