package com.bossbutler.util.enums;

public enum ComplaintInfoTypeEnum {

	Complaints("01","投诉"),  //投诉
	Advice ("02","建议");//建议
	
	
	 // 成员变量  
    private String name;  
    private String index;  

	private ComplaintInfoTypeEnum(String index,String name)

	{
        this.name = name;  
        this.index = index;  
	}

	 // 普通方法  
    public static String getName(String index) {  
        for (ComplaintInfoTypeEnum c : ComplaintInfoTypeEnum.values()) {  
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