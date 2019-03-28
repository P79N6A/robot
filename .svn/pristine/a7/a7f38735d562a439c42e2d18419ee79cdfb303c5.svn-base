package com.bossbutler.service.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.myregional.DeviceMapper;
import com.bossbutler.pojo.regional.DeviceStatus;
import com.bossbutler.pojo.remote.DeviceControlModel;

@Service
public class DeviceService {

	@Autowired
	private DeviceMapper deciveMapper;

	/**
	 * 获取设备对象
	 * 
	 * @param deviceCode
	 * @return
	 * @throws Exception
	 */
	public DeviceControlModel getDeviceControlProjectRelation(String deviceCode) throws Exception {
		return deciveMapper.selectDeviceControlModelByDeviceCode(deviceCode);
	}

	/**
	 * 获得设备通行以及门磁状态
	 * 
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	/*public List<DeviceStatus> getDeviceStatusByProjectId(long projectId) throws Exception {
		return deciveMapper.selectDeviceStatusByProjectId(projectId);
	}*/

	/**
	 * 获得设备通行以及门磁状态
	 * 
	 * @param projectId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<DeviceStatus> getDeviceStatusByAIdProjectIdStatus(long projectId, int status) throws Exception {
		List<DeviceStatus> conList=deciveMapper.selectControllerStatusByProjectId(projectId,status);
		List<DeviceStatus> devList=deciveMapper.selectDeviceStatusByProjectId(projectId,status);
		Map<String,DeviceStatus> conMap=new HashMap<String,DeviceStatus>();
		List<DeviceStatus> resultList=new ArrayList<DeviceStatus>();
		for(DeviceStatus ds:conList){
			conMap.put(ds.getdCode(),ds);
		}
		for(DeviceStatus ds:devList){
			if(conMap.get(ds.getcCode())!=null){
				resultList.add(conMap.get(ds.getcCode()));
				conMap.remove(ds.getcCode());
			}
			resultList.add(ds);
		}
		return resultList;
		
	}

}
