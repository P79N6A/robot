package com.bossbutler.pojo.visitor;

import java.io.Serializable;
import java.util.Date;

public class TerminalPojo implements Serializable{
	private long terminal_id;
	private long project_id;
	private String terminal_code;
	private String terminal_type;
	private String terminal_name;
	private String terminal_desc;
	private int barcode_time;
	private String barcode_remark;
	private String terminal_remark;
	private String local_ip;
	private String subnet_mask;
	private String gateway;
	private String mac_address;
	private String serial_number;
	private String system_version;
	private String status_code;
	private Date status_time;
	private Date create_time;
	private Date update_time;
	private long operator_id;
	private Date data_version;
	public long getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(long terminal_id) {
		this.terminal_id = terminal_id;
	}
	public long getProject_id() {
		return project_id;
	}
	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}
	public String getTerminal_code() {
		return terminal_code;
	}
	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
	}
	public String getTerminal_type() {
		return terminal_type;
	}
	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}
	public String getTerminal_name() {
		return terminal_name;
	}
	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}
	public String getTerminal_desc() {
		return terminal_desc;
	}
	public void setTerminal_desc(String terminal_desc) {
		this.terminal_desc = terminal_desc;
	}
	public int getBarcode_time() {
		return barcode_time;
	}
	public void setBarcode_time(int barcode_time) {
		this.barcode_time = barcode_time;
	}
	public String getBarcode_remark() {
		return barcode_remark;
	}
	public void setBarcode_remark(String barcode_remark) {
		this.barcode_remark = barcode_remark;
	}
	public String getTerminal_remark() {
		return terminal_remark;
	}
	public void setTerminal_remark(String terminal_remark) {
		this.terminal_remark = terminal_remark;
	}
	public String getLocal_ip() {
		return local_ip;
	}
	public void setLocal_ip(String local_ip) {
		this.local_ip = local_ip;
	}
	public String getSubnet_mask() {
		return subnet_mask;
	}
	public void setSubnet_mask(String subnet_mask) {
		this.subnet_mask = subnet_mask;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getMac_address() {
		return mac_address;
	}
	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getSystem_version() {
		return system_version;
	}
	public void setSystem_version(String system_version) {
		this.system_version = system_version;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public Date getStatus_time() {
		return status_time;
	}
	public void setStatus_time(Date status_time) {
		this.status_time = status_time;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public long getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}
	public Date getData_version() {
		return data_version;
	}
	public void setData_version(Date data_version) {
		this.data_version = data_version;
	}
	
	
}
