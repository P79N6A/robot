package com.bossbutler.mapper.resource;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.resource.ResourceModel;

public interface ResourceMapper extends IBaseMapper<ResourceModel, ResourceModel, ResourceModel> {
	
	
	/**
	 * 方法描述:查询合同下的房源
	 * 创建人: llc
	 * 创建时间: 2018年5月17日 下午2:49:30
	 * @param contractId
	 * @return List<ResourceModel>
	*/
	List <ResourceModel> queryByConId(@Param("contractId")String contractId);
	
	/**
	 * 方法描述:查询有效的房源
	 * 创建人: llc
	 * 创建时间: 2018年9月27日 下午3:01:16
	 * @return List<ResourceModel>
	*/
	List<ResourceModel> getResourceByOrgIds(@Param("model") ResourceModel model);
	
	
}
