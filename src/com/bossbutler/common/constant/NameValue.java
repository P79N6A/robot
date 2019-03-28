package com.bossbutler.common.constant;
/**
 * 
 * @author hzhang
 * @data 2015年12月26日19:23:20
 *
 */
public class NameValue {

	private String name;
	private String value;
	
	public NameValue(){
		
	}
	public NameValue(String value, String name) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
