package com.bossbutler.pojo.system;

import java.util.List;

public class AppMenuList {
	
	 private String title;
	    private String code;
	    private List<AppMenuItem> menus;
	    public void setTitle(String title) {
	         this.title = title;
	     }
	     public String getTitle() {
	         return title;
	     }

	    public void setCode(String code) {
	         this.code = code;
	     }
	     public String getCode() {
	         return code;
	     }
		public List<AppMenuItem> getMenus() {
			return menus;
		}
		public void setMenus(List<AppMenuItem> menus) {
			this.menus = menus;
		}

	  

}
