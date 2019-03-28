package com.bossbutler.mapper.news;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.news.NewsModel;

public interface NewsMapper extends IBaseMapper<NewsModel, NewsModel, NewsModel> {
	
	
	/**
	 * 方法描述:动态查询
	 * 创建人: llc
	 * 创建时间: 2018年11月8日 上午10:31:18
	 * @param model
	 * @return List<NewsModel>
	*/
	List<NewsModel> queryDynamic(@Param("model") NewsModel model);
	

	/**
	 * 方法描述:按照id 查找新闻
	 * 创建人: llc
	 * 创建时间: 2018年11月11日 下午1:04:10
	 * @param newsId
	 * @return NewsModel
	*/
	NewsModel findById(@Param("newsId") String newsId);
	
	

}