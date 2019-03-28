package com.bossbutler.pojo.dictionary;

import com.bossbutler.pojo.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;
@JsonInclude(Include.NON_NULL) 
public class DictionaryDataModel extends BaseModel implements java.io.Serializable{
	private String id;// 字典值
	private String name;//字典名称
	private String typeId;// ；类别id
	private String vagueDataId;// 用于模糊查询字典值
	private  String remark; //备注

	private List<String> dataIdList;

	

	
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
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getVagueDataId() {
		return vagueDataId;
	}
	public void setVagueDataId(String vagueDataId) {
		this.vagueDataId = vagueDataId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public List<String> getDataIdList() {
		return dataIdList;
	}

	public void setDataIdList(List<String> dataIdList) {
		this.dataIdList = dataIdList;
	}
}
