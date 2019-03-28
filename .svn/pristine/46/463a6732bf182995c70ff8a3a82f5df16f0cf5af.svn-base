package com.bossbutler.util.enums;

public enum StatusCodeEnum {

	Pending("01","待发布"),
	already("02","已发布"),
	read("03","已阅读"),
	reply("04","已回复"),
	resolved("05","已解决");
	
	
	 // 成员变量  
    private String name;  
    private String index;  

	private StatusCodeEnum(String index,String name)

	{
        this.name = name;  
        this.index = index;  
	}

	 // 普通方法  
    public static String getName(String index) {  
        for (StatusCodeEnum c : StatusCodeEnum.values()) {  
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

