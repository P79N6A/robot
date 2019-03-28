package com.bossbutler.pojo;

import java.util.List;

public class TreeNode {

	public String id;
	public String name;
	public Boolean isParent;
	public Integer Count = 0;
	public Boolean checked = false;
	public String pId = "";
	public Boolean open = false;
	public String Regional_type;
	public List<Object> list;
	private List<DevicePojo> deviceList;

	public String getRegional_type() {
		return Regional_type;
	}

	public void setRegional_type(String regional_type) {
		Regional_type = regional_type;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public List<DevicePojo> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DevicePojo> deviceList) {
		this.deviceList = deviceList;
	}

}
