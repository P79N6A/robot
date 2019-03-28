package com.bossbutler.mapper;

import org.apache.ibatis.annotations.Param;

public interface TransitResultBizMapper {

	int selectTransitListResultBizCount(@Param("projectId") long projectId, @Param("accountCode") int accountCode, @Param("deviceCode") String deviceCode, @Param("transitType") String transitType,
			@Param("transitCode") String transitCode);

}