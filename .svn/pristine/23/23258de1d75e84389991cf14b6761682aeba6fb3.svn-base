package com.bossbutler.mapper.mingdan;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.TransitList;



public interface NameListMapper {
	
	 List<Map<String,Object>> getOrgListByProId(@Param("projectId") String projectId);
	
	 List<Map<String,Object>> getArrangeListByOrgIds(List<String> ids);
	
	 String getParentArrange(String arrangeId);
	
	 List<Map<String,Object>> getArrangeByArrIdPrId(@Param("status_code") String status_code, @Param("projectId") Long projectId);

	 /**
		 * 根据节点id 查询设备
		 * @param arrId
		 * @return
		 */
	 List<Map<String,Object>> getDeviceByArrIds(List<String> arrId);
	 
	 List<Map<String,Object>> getArrangeByPId(String projectId);
	 
	// 获取该组织内部通行区域对应的所有节点
	 List<Map<String,Object>> getArrangeInsideTreeAll(@Param("org_id") String org_id);
	 
	 /**
		 * 组织下查看应该下发的名单(分页)
		 * @param orgId
		 * @param deviceId
		 * @return
		 */
		List<TransitList> getRegionalByOrgId(@Param("orgId") String orgId,@Param("arrIdStr") String arrIdStr,
					@Param("deviceId") String deviceId,
					@Param("projectId") String projectId);
		
	 

		/**
		 * 私有设备下查看应该下发的名单(分页)
		 * @param orgId
		 * @param deviceId
		 * @return
		 */
		List<TransitList> getRegionalPriByDeviceId(@Param("orgId") String orgId,@Param("deviceId") String deviceId,
					@Param("projectId") String projectId);
		
		/**
		 * 公共设备下查看应该下发的名单(分页)
		 * @param orgId
		 * @param deviceId
		 * @return
		 */
		List<TransitList> getRegionalPubByDeviceId(@Param("orgId") String orgId, @Param("deviceId") String deviceId,
					
					@Param("projectId") String projectId);
		
		List<TransitList> getSyncedList(@Param("keyword") String keyword, @Param("projectId") String projectId,
				@Param("orgId") String orgId, @Param("deviceId") String deviceId);
		
		List<TransitList> getInvalidList(@Param("keyword") String keyword, @Param("projectId") String projectId,
				@Param("orgId") String orgId, @Param("deviceId") String deviceId);
		
		List<TransitList> getNoList(@Param("keyword") String keyword, @Param("projectId") String projectId,
				@Param("orgId") String orgId, @Param("deviceId") String deviceId);
		
		List<TransitList> synchCountGrDevice(@Param("projectId")String projectId);
		List<TransitList> synchCountGrArrange(@Param("projectId")String projectId);
}
