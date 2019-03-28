package com.bossbutler.util.enums;



/**
 * 状态代码（01，阅读；02，回复；03，解决；）
 *
 */
public enum ComplaintDisponseStatusCodeEnum {
	Read ("01","阅读"),
	Replay("02","已回复"), 
	Solve ("03","解决");
	
	 // 成员变量  
    private String name;  
    private String index;  

	private ComplaintDisponseStatusCodeEnum(String index,String name)
	{
        this.name = name;  
        this.index = index;  
	}

	 // 普通方法  
    public static String getName(String index) {  
        for (ComplaintDisponseStatusCodeEnum c : ComplaintDisponseStatusCodeEnum.values()) {  
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
