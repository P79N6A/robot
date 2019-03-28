package com.bossbutler.mapper.org;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.OrgModel;


public interface OrgMapper {


	OrgModel getOrgById(String orgId);
	
	List<OrgModel> getOrgByAccId(String accountId);
	
	
	List<OrgModel> getOrgByAccIdAndPId(@Param("accountId")String accountId,@Param("projectId")String projectId);
	
	OrgModel getOrgByRId(String resourceId);
	
}