package com.bossbutler.mapper.org;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.org.OrgProFileModel;

public interface OrgProFileMapper extends IBaseMapper<OrgProFileModel,OrgProFileModel,OrgProFileModel>{
	
	int insert(@Param("model") OrgProFileModel model);
	int update(@Param("model") OrgProFileModel model);
	List<OrgProFileModel> getOrgProfile(@Param("model") OrgProFileModel model);
	int deleteById(Long profileId); 
}
