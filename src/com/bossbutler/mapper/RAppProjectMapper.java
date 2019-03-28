package com.bossbutler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RAppProjectMapper {

	/**
	 * 通过account id获得应用名称appCodeName
	 * 
	 * @param accountId
	 * @return
	 */
	List<String> selectAppCodeNameByAccountId(@Param("accountId") String accountId);

	/**
	 * 通过project id获得应用名称appCodeName
	 * 
	 * @param projectId
	 * @return
	 */
	String selectAppCodeNameByProjectId(@Param("projectId") long projectId);

}
