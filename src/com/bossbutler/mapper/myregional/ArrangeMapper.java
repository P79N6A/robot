package com.bossbutler.mapper.myregional;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;

public interface ArrangeMapper extends IBaseMapper<Arrange, Arrange, Arrange> {

	/**
	 * 方法描述:动态查询arrange
	 * 创建人: llc
	 * 创建时间: 2019年1月16日 下午5:36:20
	 * @param arrange
	 * @return List<Arrange> 
	*/
	List<Arrange> queryDynamic(Arrange arrange);
	
	/**
	 * 方法描述:查询节点的指定父节点
	 * 创建人: llc
	 * 创建时间: 2019年1月16日 下午5:36:31
	 * @param arrange
	 * @return List<Arrange> 
	*/
	List<Arrange> queryParentDynamic(Arrange arrange);
	
	Arrange getArrangeById(String arrangeId);
	
	List<Arrange> getArrangeListByOrgIds(List<String> ids);
	
	List<Arrange> getArrangeByPId(String projectId);
	
	/**
	 * 查询当前节点所有的父节点
	 * 
	 * @param arrangeId
	 * @return
	 */
	Arrange getParentArrange(String arrangeId);
	
	/**
	 * 查询项目下相关的节点
	 * 
	 * @param status_code
	 * @param projectId
	 * @return
	 */
	List<Arrange> getArrangeByArrIdPrId(@Param("status_code") String status_code, @Param("projectId") Long projectId);
	
	
	
	/**
	 * 方法描述:查询个人关联房源所在的节点
	 * 创建人: llc
	 * 创建时间: 2018年11月20日 上午10:08:23
	 * @return List<Arrange>
	*/
	List<Arrange> getResArrByAccId(@Param("accountId")String accountId);
	
	
	/**
	 * 方法描述:查询某个账号的内部通行区域节点
	 * 创建人: llc
	 * 创建时间: 2018年11月20日 下午3:25:28
	 * @param accountId
	 * @return List<Arrange>
	*/
	List<Arrange> getInArrByAccId(@Param("accountId")String accountId);
	
	/**
	 * 方法描述:动态查询内部通行区域节点
	 * 创建人: llc
	 * 创建时间: 2018年11月20日 下午4:44:19
	 * @param accountId
	 * @return List<Arrange>
	*/
	List<Arrange> getInArrDynamic(Arrange arrange);
	
	/**
	 * 方法描述:动态查询通行区域节点
	 * 创建人: llc
	 * 创建时间: 2019年1月16日 下午5:57:51
	 * @param arrange
	 * @return List<Arrange> 
	*/
	List<Arrange> getArrRegDynamic(Arrange arrange);
	
	/**
	 * 方法描述:查找房源所在的节点
	 * 创建人: llc
	 * 创建时间: 2018年12月11日 下午4:59:32
	 * @param resourceId
	 * @return Arrange
	*/
	Arrange getArrangeByReId(String resourceId);
}
