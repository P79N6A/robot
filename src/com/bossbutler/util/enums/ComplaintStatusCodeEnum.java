package com.bossbutler.util.enums;

/**
 * 状态代码（01，待发布；02，已发布；03, 已阅读 ；04，已回复；05，已解决；06，已评价；07，已撤销；）
 * 
 *
 */
public enum ComplaintStatusCodeEnum {

	Default("01","待发布"),  //待发布
	Published ("02","已发布"),//已发布
	Read ("03","已阅读"),//已发布
	Replay ("04","已回复"),//已发布
	Solve ("05","已解决"),//已发布
	Discussion ("06","已评价"),//已发布
	Cancel ("07","已撤销");//已发布

	
	
	 // 成员变量  
    private String name;  
    private String index;  

	private ComplaintStatusCodeEnum(String index,String name)
	{
        this.name = name;  
        this.index = index;  
	}

	 // 普通方法  
    public static String getName(String index) {  
        for (ComplaintStatusCodeEnum c : ComplaintStatusCodeEnum.values()) {  
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