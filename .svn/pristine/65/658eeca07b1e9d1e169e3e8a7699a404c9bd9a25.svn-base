package com.bossbutler.mapper.org;

import java.util.List;
import java.util.Map;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.OrgModel;
import com.bossbutler.pojo.org.OrgMediaModel;
import com.bossbutler.pojo.org.OrgProjectRelation;

public interface OrgMediaMapper extends IBaseMapper<OrgProjectRelation, OrgProjectRelation, OrgProjectRelation> {

	 
	int addOrgMedia(Map<String,Object> params);
	
	int deleteOrgMediaById(String media_id);
	
	/**
	 * 获取机构下同一媒体主类、媒体类别的唯一数据
	 * @param params
	 * @return
	 */
	Map<String,Object> getDistinctMedia(Map<String,Object> params);
	
	Map<String,Object> getOrgMediaById(String media_id);
	
	int updateMediaNoById(Map<String,Object> params);
	
	
    int insertDynamic(OrgMediaModel model);
    
    int updateDynamic(OrgMediaModel model);
    
    int deleteBatch(List<Long> idList);
    
    OrgMediaModel getEntityById(String mediaId);
    
    OrgMediaModel getEntityDynamic(OrgMediaModel model);

	OrgModel getOrgById(String string);
}