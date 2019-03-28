package com.bossbutler.mapper.myregional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.regional.Device;
import com.bossbutler.pojo.regional.DeviceQuery;
import com.bossbutler.pojo.regional.DeviceStatus;
import com.bossbutler.pojo.remote.DeviceControlModel;

public interface DeviceMapper extends IBaseMapper<Device, Device, DeviceQuery> {
	/**
	 * 方法描述 :根据项目ID和 创建人 : hzhang 创建时间: 2016年9月2日 下午8:46:34
	 * 
	 * @param projcetId
	 *            arrangeType
	 * @param l
	 * @return 设备列表
	 */
	List<Device> queryByArrangeTypeProjectID(@Param("projectId") String projcetId,
			@Param("arrangeType") String arrangeType, @Param("accountId") long accountId);

	List<Device> queryByEntranceTypeProjectId(@Param("projectId") String projcetId,
			@Param("entranceType") String arrangeType, @Param("arrangeIds") String arrangeIds,
			@Param("accountId") String accountId);

	List<Device> queryByEntranceTypeProjectIdNotAccountId(@Param("projectId") String projcetId,
			@Param("entranceType") String entranceType, @Param("arrangeIds") String arrangeIds,@Param("orgId")String orgId);

	List<Device> queryByProjectIdOrOrgId(@Param("projectId") String projcetId, @Param("orgId") String orgId);

	String queryArrangeByAccountId(@Param("accountId") long accountId);

	/**
	 * 组织对应的节点
	 * 
	 * @param orgId
	 * @return
	 */
	String queryArrangeByOrgId(@Param("orgId") String orgId);

	DeviceControlModel selectDeviceControlModelByDeviceCode(@Param("deviceCode") String deviceCode);

	List<DeviceStatus> selectDeviceStatusByProjectId(@Param("projectId") long projcetId,@Param("status") int status);
	
	List<DeviceStatus> selectControllerStatusByProjectId(@Param("projectId") long projcetId,@Param("status") int status);

	List<DeviceStatus> selectDeviceStatusByProjectIdStatus(@Param("projectId") long projectId,
			@Param("status") int status);

	List<Device> queryByArrangeId(@Param("arrangeId")String arrangeId);
	
	/**
	 * 方法描述:动态查询
	 * 创建人: llc
	 * 创建时间: 2018年10月17日 上午11:11:44
	 * @param device
	 * @return List<Device>
	*/
	List<Device> queryDynamic(Device device);

	void updateKeepTime(@Param("deviceCode") String deviceCode, @Param("keepEndDate") Date keepEndDate);
	

	public List<Map<String, Object>> selectTopLevelArrangeIdByDeviceCodes(@Param("deviceCodes") String[] deviceCodes, @Param("projectId") String projectId);


}
