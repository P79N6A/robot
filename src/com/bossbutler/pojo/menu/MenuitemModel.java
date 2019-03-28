package com.bossbutler.pojo.menu;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class MenuitemModel implements java.io.Serializable{
	
private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MenuItem";
	public static final String ALIAS_MENU_ID = "菜单ID（3，2，2）";
	public static final String ALIAS_APP_ID = "应用程序ID";
	public static final String ALIAS_ORDINAL = "排序字段";
	public static final String ALIAS_MENU_TEXT = "菜单文本";
	public static final String ALIAS_SUPER_ID = "父菜单ID";
	public static final String ALIAS_PERMISSION = "权限值";
	public static final String ALIAS_MENU_PATH = "菜单路径";
	public static final String ALIAS_ICON_IMAGE = "菜单图标";
	public static final String ALIAS_STATUS_CODE = "状态代码（01，初始化；02，已禁用；）";
	public static final String ALIAS_DATA_VERSION = "数据版本";
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=20)
	private java.lang.String menuId="";
	//@Length(max=3)
	private java.lang.String appId="";
	
	private java.lang.Integer ordinal=0;
	//@Length(max=50)
	private java.lang.String menuText="";
	//@Length(max=50)
	private java.lang.String superId="";
	
	private java.lang.Long permissions=0L;
	//@Length(max=200)
	private java.lang.String menuPath="";
	//@Length(max=20)
	private java.lang.String iconImage="";
	//@Length(max=2)
	private java.lang.String statusCode="";

	
	private java.lang.String parentMenuName="";
	
	
	private java.lang.Integer numId=0;
	//columns END

	
	public java.lang.Integer getNumId() {
		return numId;
	}

	public void setNumId(java.lang.Integer numId) {
		this.numId = numId;
	}

	public java.lang.String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(java.lang.String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	public static final String FORMAT_DATA_VERSION = "yyyy-MM-dd HH:mm:ss";
	
	
	public MenuitemModel(){
	}

	public MenuitemModel(
		java.lang.String menuId
	){
		this.menuId = menuId;
	}

	public void setMenuId(java.lang.String value) {
		this.menuId = value;
	}
	
	public java.lang.String getMenuId() {
		return this.menuId;
	}
	public void setAppId(java.lang.String value) {
		this.appId = value;
	}
	
	public java.lang.String getAppId() {
		return this.appId;
	}
	public void setOrdinal(java.lang.Integer value) {
		this.ordinal = value;
	}
	
	public java.lang.Integer getOrdinal() {
		return this.ordinal;
	}
	public void setMenuText(java.lang.String value) {
		this.menuText = value;
	}
	
	public java.lang.String getMenuText() {
		return this.menuText;
	}
	public void setSuperId(java.lang.String value) {
		this.superId = value;
	}
	
	public java.lang.String getSuperId() {
		return this.superId;
	}
	public void setPermission(java.lang.Long value) {
		this.permissions = value;
	}
	
	public java.lang.Long getPermission() {
		return this.permissions;
	}
	public void setMenuPath(java.lang.String value) {
		this.menuPath = value;
	}
	
	public java.lang.String getMenuPath() {
		return this.menuPath;
	}
	public void setIconImage(java.lang.String value) {
		this.iconImage = value;
	}
	
	public java.lang.String getIconImage() {
		return this.iconImage;
	}
	public void setStatusCode(java.lang.String value) {
		this.statusCode = value;
	}
	
	public java.lang.String getStatusCode() {
		return this.statusCode;
	}
/*	public String getDataVersionString() {
		return CommonUtil.getYYYYMMddHHmmss(this.dataVersion);
	}
*/


	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("MenuId",getMenuId())
			.append("AppId",getAppId())
			.append("Ordinal",getOrdinal())
			.append("MenuText",getMenuText())
			.append("SuperId",getSuperId())
			.append("Permission",getPermission())
			.append("MenuPath",getMenuPath())
			.append("IconImage",getIconImage())
			.append("StatusCode",getStatusCode())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMenuId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MenuitemModel == false) return false;
		if(this == obj) return true;
		MenuitemModel other = (MenuitemModel)obj;
		return new EqualsBuilder()
			.append(getMenuId(),other.getMenuId())
			.isEquals();
	}
}
