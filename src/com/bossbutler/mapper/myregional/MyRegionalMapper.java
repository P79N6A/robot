package com.bossbutler.mapper.myregional;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.DevicePojo;
import com.bossbutler.pojo.MyArrangePojo;
import com.bossbutler.pojo.RegionalPojo;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.ArrangeModel;
import com.bossbutler.pojo.regional.Device;
import com.bossbutler.pojo.regional.RegionalArrangeRelationModel;
import com.bossbutler.pojo.regional.RegionalEmpRelationModel;
import com.bossbutler.pojo.regional.RegionalModel;
import com.bossbutler.pojo.regional.TransitModel;
import com.bossbutler.pojo.resource.ResourceModel;
import com.bossbutler.pojo.system.Account;

public interface MyRegionalMapper {
	List<RegionalPojo> querymyregional(@Param("accountId") String accountId);
	
	List<RegionalPojo> queryRegional(@Param("model") RegionalPojo model);

	List<MyArrangePojo> querymyarrange(MyArrangePojo myArrangePojo);

	List<DevicePojo> querymydevice(@Param("arrangeId") String arrangeId);
	
	List<DevicePojo> querymydeviceDEX(@Param("arrangeId") String arrangeId);
	
	/** 通行区域--节点一级（新） */
	List<MyArrangePojo> queryArrangeFirst(@Param("accountId") String accountId);

	List<MyArrangePojo> queryArrangeFirstNew(@Param("accountId") String accountId);
	List<RegionalModel> getTerminalRegionalByTerId(@Param("mackAddress") String mackAddress,
			@Param("statusCode") String statusCode);

	void setRegionalStatusCode(@Param("regionalid") String regionalid, @Param("statusCode") String statusCode,
			@Param("operatorId") String operatorId);

	void setRegionalNameById(@Param("regionalid") String regionalid, @Param("regional_name") String regional_name,
			@Param("operatorId") String operatorId);

/////////////////////////////////////////////////////////////////////////
	/**
	 * 名单结果表
	 * 
	 * @param tempDivices
	 * @return
	 */
	List<TransitModel> getTransitByDeviceAccId(@Param("divices") List<Device> tempDivices, @Param("accounts") List<Account> accounts,@Param("transitType") String transitType);

	List<TransitModel> queryTransitByTransitCode(@Param("transitCode") String transitCode, @Param("projectId") Long projectId);

	List<TransitModel> queryTransitByTyAcTcPi(@Param("transitType") String transitType,	@Param("accountCode") String accountCode,
			@Param("transitCode") String transitCode, @Param("projectId") Long projectId);
/////////////////////////////////////////////////////////////////////////

	/**
	 * 删除通行区域
	 * 
	 * @param regionalId
	 */
	int deleteById(@Param("regionalId") String regionalId);

	/**
	 * 删除通行区域与节点的关系
	 * 
	 * @param regionalId
	 */
	int deleteRegionalArrangeRelationById(@Param("regionalId") String regionalId);

	List<Account> getAccontByEmpId2(@Param("list") List<RegionalEmpRelationModel> regEmpList);
	List<TransitModel> getTransitByDeviceAccId2(@Param("divices") List<Device> divices,
			@Param("accounts") Account account, @Param("projectId") Long projectId);

	List<Device> getDevicebyProjectId(@Param("project") Project p);

	void createTransitList(List<TransitModel> newTransits);

	List<ResourceModel> getOrgResourceByOrgId(@Param("orgId") Long org_id);
	
	/**
	 * 方法描述 : 查询某个组织的房源
	 * 创建人 :  llc
	 * 创建时间: 2018年3月14日
	 * @param org_id
	 * @return
	 */
	List<ResourceModel> getResourceByOrgId(@Param("orgId") String orgId);

	ArrangeModel getParentArrange(Long arrangeId);

	List<Arrange> getArrangeByArrIdPrId(@Param("status_code") String status_code, @Param("projectId") Long projectId);

	List<Device> getPublicDevicebyProjectId(@Param("project") Project p);

	List<Device> getDeviceByArrangeIds(@Param("list") List<RegionalArrangeRelationModel> deleteList);

	List<TransitModel> queryTransitListByTyAcTcPi(
			@Param("accountCode")Integer accountCode, 
			@Param("transitType") String transitType,
			@Param("projectId") Long projectId,
			@Param("transitCode") String transitCode
			);
	/**
	 * 查询等同步名单
	 * @param divices
	 * @param account
	 * @param projectId
	 * @return
	 */
	List<TransitModel> getTransitListByDeviceAccId2(@Param("divices") List<Device> divices,
			@Param("accounts") Account account, @Param("projectId") Long projectId);


	
	/**
	 * 查询设备所关联的节点
	 * @param deviceId
	 * @return
	 */
	List<Arrange >getArrangebyDeviceId(@Param("deviceId") String deviceId);


}
