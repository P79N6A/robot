package com.bossbutler.mapper.project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.org.Org;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.third.ComboProjectIdName;
import com.bossbutler.pojo.third.ThirdDRProject;
import com.bossbutler.pojo.visitor.VisitorIn;

public interface ProjectMapper extends IBaseMapper<Project, Project, Project> {

	Project getProjectByEmpId(@Param("empId") String empId);

	List<Project> queryProjectByVisitor(VisitorIn param);

	List<Org> queryOrgByProjectId(@Param("projectId") String projectId, @Param("orgName") String orgName);

	List<Map<String,String>> getOrgListByProId(@Param("projectId") String projectId);
	
	List<ThirdDRProject> selectProjectListByAccountId(@Param("accountId") long accountId);

	List<ComboProjectIdName> selectProjectIdNameByAccountId(@Param("accountId") long accountId);

	long selectCountByAccountIdProjectId(@Param("accountId") long accountId, @Param("projectId") long projectId);

	/**
	 * 获取组织对应的房源
	 */
	String getResourceNameByOrgIds(@Param("orgId") String orgId);
	/**
	 * 获取项目配置信息
	 * @param params
	 * @return
	 */
	String getProjectProfileByParam(Map<String,String> params);
	
	List<Map<String,String>> getProfileByParam(Map<String,Object> params);

	Project findByProjectCode(@Param("projectCode") String projectCode);
	
	
	

}