package com.bossbutler.pojo.regional;

import java.io.Serializable;
/**
 * 访客媒体类
 * @author wangjinliang
 *
 */
public class VisitorMedia implements Serializable{
	private static final long serialVersionUID = 1L;
	private String media_id;
	private String visitor_id;//访客ID
	private String media_type;//媒体类别
	private String file_type;//文件类别
	private String main_classify;
	private String media_no;//资质号码
	private String create_time;//创建时间
	private String update_time;//更新时间
	private String operator_id;
	private long ordinal;//排列序号
	private String data_version;
	private String remark;//身份证详情
	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getVisitor_id() {
		return visitor_id;
	}
	public void setVisitor_id(String visitor_id) {
		this.visitor_id = visitor_id;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getMain_classify() {
		return main_classify;
	}
	public void setMain_classify(String main_classify) {
		this.main_classify = main_classify;
	}
	public String getMedia_no() {
		return media_no;
	}
	public void setMedia_no(String media_no) {
		this.media_no = media_no;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public long getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(long ordinal) {
		this.ordinal = ordinal;
	}
	public String getData_version() {
		return data_version;
	}
	public void setData_version(String data_version) {
		this.data_version = data_version;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
