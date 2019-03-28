package com.bossbutler.mapper.news;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.Information.InformationLog;
import com.bossbutler.pojo.news.NewsLogModel;
import com.bossbutler.pojo.news.NewsModel;

public interface NewsLogMapper {
	

	
	/**
	 * 方法描述:读日志存取
	 * 创建人: llc
	 * 创建时间: 2018年11月11日 下午1:03:53
	 * @param model
	 * @return int
	*/
	int insert(@Param("model") NewsLogModel model);
	
	/**
	 * 方法描述:动态读取
	 * 创建人: llc
	 * 创建时间: 2018年11月11日 下午1:19:45
	 * @param model
	 * @return List<NewsLogModel>
	*/
	List<NewsLogModel> queryDynamic(@Param("model") NewsLogModel model);
	

}
