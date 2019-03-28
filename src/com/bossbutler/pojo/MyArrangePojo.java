package com.bossbutler.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;;

@JsonInclude(Include.NON_NULL) 
public class MyArrangePojo {

	private String regionalId;// 通行区域Id
	private String arrangeId;// 节点Id
	private String arrangeName;// 名称
	private String projectName;// 名称
	private String arrangeType;// 节点类型
	private String entryRoot;// 入口标识（0，非；1，是；）
	
	
	
	

	public String getEntryRoot() {
		return entryRoot;
	}
	public void setEntryRoot(String entryRoot) {
		this.entryRoot = entryRoot;
	}
	public String getArrangeType() {
		return arrangeType;
	}
	public void setArrangeType(String arrangeType) {
		this.arrangeType = arrangeType;
	}
	public String groupKey(){
		return this.projectName;
	}
	public String getRegionalId() {
		return regionalId;
	}

	public void setRegionalId(String regionalId) {
		this.regionalId = regionalId;
	}

	public String getArrangeId() {
		return arrangeId;
	}

	public void setArrangeId(String arrangeId) {
		this.arrangeId = arrangeId;
	}

	public String getArrangeName() {
		return arrangeName;
	}

	public void setArrangeName(String arrangeName) {
		this.arrangeName = arrangeName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
