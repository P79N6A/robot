package com.bossbutler.util.enums;

public enum TransitTypeEnum {
	//00，qr；01，ic；02，密码；）

			Frist("00","二维码"),
			Second ("01","IC卡"),
			Third("02","密码");
			
			
			 // 成员变量  
		    private String name;  
		    private String index;  

			private TransitTypeEnum(String index,String name)
			{
		        this.name = name;  
		        this.index = index;  
			}

			 // 普通方法  
		    public static String getName(String index) {  
		        for (TransitTypeEnum c : TransitTypeEnum.values()) {  
		            if (c.getIndex().equals(index)) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // get set 方法  
		    public String getName() {  
		        return name;  
		    }  
		    public void setName(String name) {  
		        this.name = name;  
		    }  
		    public String getIndex() {  
		        return index;  
		    }  
		    public void setIndex(String index) {  
		        this.index = index;  
		    }  
}
