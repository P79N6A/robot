package com.bossbutler.mapper.customerservice;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.ImagePojo;
import com.bossbutler.pojo.ServerImgPojo;

/**
 * 名称 : 用于工单的的图片上传 描述 : 创建人 : xbj 创建时间: 2016年9月5日 下午5:32:05
 * 
 */
public interface CustomerServerMapper {
	
	ImagePojo queryordinal(@Param("main_classify") String main_classify, 
			@Param("mediaType") String mediaType,@Param("billId") String billId);

	int insertsermidia(ServerImgPojo pojo);
}
