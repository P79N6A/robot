package com.bossbutler.mapper.visitor;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.third.ThirdDRTransitInf;
import com.bossbutler.pojo.visitor.TransitLog;
import com.bossbutler.pojo.visitor.TransitLogMyDto;

public interface TransitLogMapper extends IBaseMapper<TransitLog, TransitLog, TransitLog> {

	List<TransitLog> queryPassageByPageList(TransitLog params, PagingBounds page);

	List<TransitLogMyDto> queryMyByAccountDate(TransitLog params);

	List<TransitLogMyDto> queryMyInfoByAccountDate(TransitLog params);

	/**
	 * 访客通行记录
	 * 
	 * @param params
	 * @return
	 */
	List<TransitLogMyDto> queryByVisitorId(@Param("visitorId") String visitorId);

	List<ThirdDRTransitInf> selectThirdTransitLogListByCondition(@Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize, @Param("condition") Map<String, Object> condition);

	int selectThirdTransitLogCountByCondition(@Param("condition") Map<String, Object> condition);

}
