package com.bossbutler.mapper.project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.org.OrgProjectRelation;
import com.bossbutler.pojo.project.ProjectMedia;

public interface ProjectMediaMapper extends IBaseMapper<ProjectMedia, ProjectMedia, OrgProjectRelation> {

	 
	int addProjectMedia(Map<String,Object> params);
	
	int deleteProjectMediaById(String media_id);
	
	/**
	 * 获取账号下同一媒体主类、媒体类别的唯一数据
	 * @param params
	 * @return
	 */
	List<ProjectMedia> getMediaByProId(@Param("project_id") String project_id, @Param("media_type") String media_type, @Param("main_classify") String main_classify);
	
	ProjectMedia getMediaById(String media_id);
	
	
	int updateDynamic(ProjectMedia model);

	int deleteByProjectId(long accountId);
	
	 
}