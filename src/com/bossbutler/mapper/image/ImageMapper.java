package com.bossbutler.mapper.image;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.ImagePojo;
import com.bossbutler.pojo.bill.BillMedia;
import com.bossbutler.pojo.system.AccountMedia;

public interface ImageMapper {
	
	void insertmedia(ImagePojo pojo);

	ImagePojo queryordinal(@Param("mainClassify") String main_classify, @Param("mediaType") String mediaType);

	AccountMedia findMediaByAccountId(@Param("accountId")String accountId,
			@Param("mainClassify") String mainClassify, @Param("mediaType") String mediaType);

	List<BillMedia> findMediaByBillId(@Param("billId")String accountId,
			@Param("mainClassify") String mainClassify, @Param("mediaType") String mediaType);

	
}
