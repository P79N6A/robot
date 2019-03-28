package com.bossbutler.controller;

import com.bossbutler.pojo.remote.DeviceControlModel;
import com.bossbutler.pojo.remote.DeviceControllerInfo;
import com.bossbutler.service.remote.DeviceService;
import com.bossbutler.util.RedisConstant;
import com.company.common.redis.active.CacheManager;

/**
 * <p>
 * controller基础设备缓存公共类.
 * </p>
 * 
 * @author wq
 */
public class BaseDeviceCacheController extends BaseController {

	/**
	 * 获得主控板信息
	 * 
	 * @param deviceCode
	 * @param deviceService
	 * @return
	 * @throws Exception
	 */
	protected DeviceControllerInfo getDeviceControllerInfo(String deviceCode, DeviceService deviceService)
			throws Exception {
		DeviceControllerInfo result = readDeviceControllerMapCache(
				RedisConstant.REDIS_TABLE_DEVICE_CONTROL_WHITE_LIST_KEY, deviceCode);
		if (result == null) {
			DeviceControlModel deviceControlModel = deviceService.getDeviceControlProjectRelation(deviceCode);
			if (deviceControlModel == null) {
				return null;
			}
			String projectId = deviceControlModel.getProjectId();
			String controllerCode = deviceControlModel.getControllerCode();
			String controllerType = deviceControlModel.getControllerType();
			int channel = Integer.valueOf(deviceControlModel.getChannel());
			// 写入缓存
			boolean isOnline = false;
			if ("02".equals(deviceControlModel.getStatusCode()) && "02".equals(deviceControlModel.getDstatusCode())) {
				isOnline = true;
			}
			writeDeviceControllerMapCache(RedisConstant.REDIS_TABLE_DEVICE_CONTROL_WHITE_LIST_KEY, deviceCode,
					deviceControlModel.getDeviceCacheVal(isOnline));
			// 返回消息对象
			return new DeviceControllerInfo(controllerCode, controllerType, channel, projectId);
		}
		return result;
	}

	/**
	 * 远程设备映射放入缓存
	 */
	private void writeDeviceControllerMapCache(String formatStrKey, String deviceCode, String cacheVal) {
		try {
			CacheManager.setHTableKeyVal(formatStrKey, deviceCode, cacheVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 远程设备映射清理缓存
	 * 
	 * @param deviceCode
	 */
	public void deleteDeviceControllerInfoCache(String deviceCode) {
		try {
			CacheManager.delHTableKeyVal(RedisConstant.REDIS_TABLE_DEVICE_CONTROL_WHITE_LIST_KEY, deviceCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缓存格式controllerCode_controllerType_isOnline_projectId_channel_deviceType
	 * 
	 * 远程设备映射放入缓存
	 */
	private DeviceControllerInfo readDeviceControllerMapCache(String formatStrKey, String deviceCode) {
		try {
			Object controllerInfObj = CacheManager.getHTableKeyVal(formatStrKey, deviceCode);
			if (controllerInfObj == null) {
				return null;
			}
			String[] controllerStrArr = ((String) controllerInfObj).split("_", -1);
			if (controllerStrArr.length < 5) {
				return null;
			}
			return new DeviceControllerInfo(controllerStrArr[0], controllerStrArr[1],
					Integer.valueOf(controllerStrArr[4]), controllerStrArr[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
