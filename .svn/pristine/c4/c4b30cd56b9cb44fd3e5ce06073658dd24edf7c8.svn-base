package com.bossbutler.service.passthrough;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bossbutler.mapper.myregional.DeviceMapper;
import com.bossbutler.mapper.visitor.VisitorApplyMapper;
import com.bossbutler.pojo.MyvisitorPojo;
import com.bossbutler.pojo.VisitorInfoPojo;
import com.bossbutler.pojo.visitor.VisitorApply;
import com.bossbutler.util.RedisConstant;
import com.bossbutler.util.enums.VisitorPersonType;
import com.company.common.redis.active.CacheManager;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;

@Service
public class PassFilterThroughService {

	private static Logger logger = Logger.getLogger(PassFilterThroughService.class);

	private static final String TOP_LEVEL_TRANSIT_CACHE_KEY_FORMAT = "bbw-pmpserver-transit-access-%s";

	/**
	 * 时间格式模板
	 */
	private static final SimpleDateFormat simpleFormat = new SimpleDateFormat(DateUtil.PATTERN_STANDARD);

	@Autowired
	private DeviceMapper deviceMapper;
	
	@Autowired
	private VisitorApplyMapper visitorApplyMapper;

	/**
	 * 判断是否超出顶级节点限制
	 * 通行规则：
	 * 1.顶没有或者已经有出的时候，子进不去
	 * 2.有进才有出
	 * 3.顶级节点有进无出的时候，子不限制，直到有出了完成一个周期
	 * 
	 * @param projectId 项目id
	 * @param transitCode 通行code
	 * @param deviceCodes 通行设备code集合
	 * @param deviceCode 当前设备code
	 * @param isIntype 是否为进方向
	 * @param intCount 限制进个数
	 * @param outCount 限制出个数
	 * @param inOutType 进出策略（01,03,04开头操作必须是进）
	 * @param endDateTimeStr 邀请结束时间yyyy-MM-dd HH:mm:ss
	 * @return 是否需要开
	 */
	public boolean judgeFilterVisitorPower(String projectId, String transitCode, String[] deviceCodes,
			String deviceCode, boolean isIntype, int intCount, int outCount, String inOutType, String endDateTimeStr) {
		// 检测是否存在顶级节点缓存，cache key:bbw-pmpserver-transit-access-%transitCode%
		String cacheTableKey = String.format(TOP_LEVEL_TRANSIT_CACHE_KEY_FORMAT, transitCode);
		try {
			StringBuilder sbuidler = new StringBuilder();
			for(String dv : deviceCodes) {
				sbuidler.append(dv).append(",");
			}

			logger.info("judgeFilterVisitorPower===projectId:" + projectId + "|transitCode:" + transitCode + "|deviceCodes:" + sbuidler.toString() + "|deviceCode:" +
						deviceCode + "|isIntype:" + isIntype + "|intCount:" + intCount + "|outCount:" + outCount);
			logger.info("judgeFilterVisitorPower===CacheManager.exists(cacheTableKey):" + cacheTableKey + "|flag:" + CacheManager.exists(cacheTableKey));

			if(CacheManager.exists(cacheTableKey)) {// 缓存存在，检测次数
				// 获得顶级节点
				String topLevelDevicesCache = (String) CacheManager.getHTableKeyVal(cacheTableKey, "field—top-level");
				logger.info("judgeFilterVisitorPower===topLevelDevicesCache:" + topLevelDevicesCache);
				// 获得通行次数，判断是否开启：field-int-count;field-out-count
				int intCountCache = (Integer) CacheManager.getHTableKeyVal(cacheTableKey, "field-int-count");
				int outCountCache = (Integer) CacheManager.getHTableKeyVal(cacheTableKey, "field-out-count");
				logger.info("judgeFilterVisitorPower===topLevelDevicesCache.contains(deviceCode,):" + topLevelDevicesCache.contains(deviceCode + ","));
				logger.info("judgeFilterVisitorPower===outCountCache:" + outCountCache);
				logger.info("judgeFilterVisitorPower===intCountCache:" + intCountCache);
				if(topLevelDevicesCache.contains(deviceCode + ",")) {// 如果设备包含在顶级节点，判断缓存通行是否通行
					return checkTransitCount(true, isIntype, intCount, outCount, inOutType, intCountCache, outCountCache);
				} else {// 如果子节点
					return checkTransitCount(false, isIntype, intCount, outCount, inOutType, intCountCache, outCountCache);
				}
			} else {// 缓存不存在，创建缓存（有效时间到晚上23点59分59秒）
				// 获得顶级节点缓存，并设置缓存：field—top-level
				List<Map<String, Object>> topLevelArrangeIdDeviceCodeMapList = deviceMapper.selectTopLevelArrangeIdByDeviceCodes(deviceCodes, projectId);
				StringBuilder topLevelDeviceCodeAndSerialNumberSb = new StringBuilder();
				String topLevelArrangeId = topLevelArrangeIdDeviceCodeMapList.get(0).get("arrangeId").toString();
				for(Map<String, Object> item : topLevelArrangeIdDeviceCodeMapList) {
					if(topLevelArrangeId.equals(item.get("arrangeId").toString())) {
						topLevelDeviceCodeAndSerialNumberSb.append(item.get("deviceCode").toString()).append(",")
											.append(item.get("serialNumber").toString()).append(",");
					}
				}
				String topLevelDevicesCacheStr = topLevelDeviceCodeAndSerialNumberSb.toString();
				CacheManager.setHTableKeyVal(cacheTableKey, "field—top-level", topLevelDevicesCacheStr);
				// 设置通行次数0，等待开门成功响应增加1：field-int-count;field-out-count
				CacheManager.setHTableKeyVal(cacheTableKey, "field-int-count", 0);
				CacheManager.setHTableKeyVal(cacheTableKey, "field-out-count", 0);
				// 设置有效时间到晚上23点59分59秒
				CacheManager.expire(cacheTableKey, getCurrentDayCompSeconds(endDateTimeStr));
				if(topLevelDevicesCacheStr.contains(deviceCode + ",")) {
					return checkTransitCount(true, isIntype, intCount, outCount, inOutType, 0, 0);
				} else {
					return checkTransitCount(false, isIntype, intCount, outCount, inOutType, 0, 0);
				}
			}
		} catch (Exception e) {
			logger.error("缓存管理失败", e);
		}
		return false;
	}
	/**
	 * @param isTopLevel 是否为顶级节点
	 * @param isIntype 是否为进
	 * @param intCount 进门限制
	 * @param outCount 出门限制
	 * @param inOutType 进出策略（02,03,04开头操作必须是进）
	 * @param inted 已经进的次数
	 * @param outed 已经出的次数
	 * @return
	 */
	private boolean checkTransitCount(boolean isTopLevel, boolean isIntype, int intCount, int outCount, String inOutType,
			int inted, int outed) {
		boolean result = false;
		if(isTopLevel) {// 顶级节点
			if("01".equals(inOutType)) {// 进**** :当进一次后，以后不限制（根据次数来）
				if(isIntype) {// 进
					result = true;
				} else {// 出
					if(inted > 0) {
						result = true;
					}
				}
			} else if("02".equals(inOutType)) {// **** :进出根据次数不做任何限制
				result = true;
//				if(isIntype) {// 进
//					result = true;
//				} else {// 出
//					if(inted > 0) {
//						result = true;
//					}
//				}
			} else if("03".equals(inOutType)) {// 进出进出进出… :以进开头，以出或者进结尾
				if(isIntype) {// 进
					if(inted == outed) {
						result = true;
					}
				} else {// 出
					if((inted - 1) == outed) {
						result = true;
					}
				}
			} else if("04".equals(inOutType)) {// 进进进进… :(次数为in参数)，然后出出出出…（次数为out参数），进用完，才能用出
				if(isIntype) {// 进
					if(outed == 0) {
						result = true;
					}
				} else {// 出
					if(inted > 0) {
						result = true;
					}
				}
			}
			if(result) {
				if(isIntype && (intCount == 0 || inted < intCount)) {
					result = true;
				} else if(!isIntype && (outCount == 0 || outed < outCount)) {
					result = true;
				} else {
					result = false;
				}
			}
		} else {// 子节点属于先进后出
			if("01".equals(inOutType)) {// 进**** :当进一次后，以后不限制（根据次数来）
				if(isIntype) {// 进
					if(inted > 0) {
						result = true;
					}
				} else {// 出
					if(inted > 0) {
						result = true;
					}
				}
			} else if("02".equals(inOutType)) {// **** :进出根据次数不做任何限制
				result = true;
//				if(isIntype) {// 进
//					if(inted > 0) {
//						result = true;
//					}
//				} else {// 出
//					if(inted > 0) {
//						result = true;
//					}
//				}
			} else if("03".equals(inOutType)) {// 进出进出进出… :以进开头，以出或者进结尾
				if(isIntype) {// 进
					if(inted > 0 && (inted - 1) == outed) {
						result = true;
					}
				} else {// 出
					if(inted > 0 && (inted - 1) == outed) {
						result = true;
					}
				}
			} else if("04".equals(inOutType)) {// 进进进进… :(次数为in参数)，然后出出出出…（次数为out参数），进用完，才能用出
				if(isIntype) {// 进
					if(inted > 0 && outed == 0) {
						result = true;
					}
				} else {// 出
					if(inted > 0 && outed > 0) {
						result = true;
					}
				}
			}
			if(result) {
				if(isIntype && (intCount == 0 || inted <= intCount)) {
					result = true;
				} else if(!isIntype && (outCount == 0 || outed < outCount)) {
					result = true;
				} else {
					result = false;
				}
			}
		}
		return result;
	}
	/**
	 * 顶级节点计数，子节点则判断顶级节点是否需要计数
	 * 
	 * @param projectId 项目id
	 * @param transitCode 通行code
	 * @param deviceCode 当前设备code
	 * @param isIntype 是否为进方向
	 * @return
	 */
	public boolean judgeFilterVisitorPowerCount(String projectId, String transitCode, String deviceCode, boolean isIntype) {
		
		// 检测是否存在顶级节点缓存，cache key:bbw-pmpserver-transit-access-%transitCode%
		String cacheTableKey = String.format(TOP_LEVEL_TRANSIT_CACHE_KEY_FORMAT, transitCode);
		try {
			if(CacheManager.exists(cacheTableKey)) {// 缓存存在，检测次数
				// 获得顶级节点
				String topLevelDevicesCache = (String) CacheManager.getHTableKeyVal(cacheTableKey, "field—top-level");
				if(topLevelDevicesCache.contains(deviceCode + ",")) {// 如果设备包含在顶级节点，判断缓存通行是否通行
					// 获得通行次数，判断是否开启：field-int-count;field-out-count
					if(isIntype) {
						int intCountCache = (Integer) CacheManager.getHTableKeyVal(cacheTableKey, "field-int-count");
						CacheManager.setHTableKeyVal(cacheTableKey, "field-int-count", intCountCache + 1);
					} else {
						int outCountCache = (Integer) CacheManager.getHTableKeyVal(cacheTableKey, "field-out-count");
						CacheManager.setHTableKeyVal(cacheTableKey, "field-out-count", outCountCache + 1);
					}
				} else {// 如果设备不包含在顶级节点，设置节点通行
					if(isIntype) {
						int intCountCache = (Integer) CacheManager.getHTableKeyVal(cacheTableKey, "field-int-count");
						if(intCountCache == 0) {
							CacheManager.setHTableKeyVal(cacheTableKey, "field-int-count", 1);
						}
					}
//					else {
//						int outCountCache = (Integer) CacheManager.getHTableKeyVal(cacheTableKey, "field-out-count");
//						if(outCountCache == 0) {
//							CacheManager.setHTableKeyVal(cacheTableKey, "field-out-count", 1);
//						}
//					}
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("缓存管理失败", e);
		}
		return false;
	}

	/**
	 * 判断是否超出顶级节点并计数，清理计数：
	 * 只处理二维码以及特定项目
	 * 
	 * @param projectId 项目id
	 * @param transitType 通行类型00为二维码，01为ic，02为密码
	 * @param transitCode 通行code
	 * @param deviceCodes 通行设备code集合
	 * @return
	 */
	public boolean judgeFilterVisitorPowerClearCache(String projectId, String transitType, String transitCode, String[] deviceCodes) {
		
		// 检测是否存在顶级节点缓存，cache key:bbw-pmpserver-transit-access-%transitCode%
		String cacheTableKey = String.format(TOP_LEVEL_TRANSIT_CACHE_KEY_FORMAT, transitCode);
		String formatStrKey = String.format(RedisConstant.REDIS_PASS_THROUGH_VISITOR_LIST_KEY_SUB, transitCode + transitType);
		try {
			if(CacheManager.exists(formatStrKey)) {
				CacheManager.delExpire(formatStrKey);
			}
			if(CacheManager.exists(cacheTableKey)) {// 缓存存在，检测次数
				CacheManager.delExpire(cacheTableKey);
			}
			return true;
		} catch (Exception e) {
			logger.error("缓存管理失败", e);
		}
		return false;
	}

	/**
	 * 获得当前时间到当天晚上的秒差
	 * 
	 * @return
	 * @throws ParseException
	 */
	private int getCurrentDayCompSeconds(String toDateTime) throws ParseException {
		if(StringUtils.isBlank(toDateTime)) {
			toDateTime = DateUtil.getCurrentDate() + " 23:59:59";
		}
		long from = new Date().getTime();
		long to = simpleFormat.parse(toDateTime).getTime();
		return (int) ((to - from) / 1000);
	}
	
	public void visitorApplyCache(String applyId) throws Exception{
		try{
			if(StringUtils.isNotBlank(applyId)){
				VisitorApply queryModel=new VisitorApply();
				queryModel.setRelationId(new Long(applyId));
				List<VisitorApply> list=visitorApplyMapper.queryDynamic(queryModel);
				if(list !=null && list.size()>0){
					VisitorApply visitorApply=list.get(0);
					if (visitorApply !=null) {
						String afterCacheFlag = "00";
						String formatStrKey = String.format(RedisConstant.REDIS_PASS_THROUGH_VISITOR_LIST_KEY_SUB,visitorApply.getTransitCode() + afterCacheFlag);
						String transitDevices = visitorApply.getTransitDevices();
						String trafficStrategyJson = visitorApply.getTrafficStrategy();
						if (StringUtils.isNotBlank(trafficStrategyJson) && StringUtils.isNotBlank(transitDevices)) {
							Map<String, String> trafficStrategyMap = BeanUtility.jsonStrToObject(trafficStrategyJson, Map.class, false);
							String inOutType = trafficStrategyMap.get("inOutType");
							String traTimeType = trafficStrategyMap.get("traTimeType");
							String out = trafficStrategyMap.get("out");
							String in = trafficStrategyMap.get("in");
							String beginDateStr = DateUtil.format(visitorApply.getBeginDate(),"yyyy-MM-dd");
							String beginTimeStr =DateUtil.format(visitorApply.getBeginTime(),"HH:mm:ss"); 
							String endDateStr =  DateUtil.format(visitorApply.getEndDate(),"yyyy-MM-dd");
							String endTimeStr =DateUtil.format(visitorApply.getEndTime(),"HH:mm:ss"); 
							Map<String, String> paramVals = new HashMap<>();
							paramVals.put("inOutType", inOutType);
							paramVals.put("traTimeType", traTimeType);
							paramVals.put("out", out);
							paramVals.put("in", in);
							paramVals.put("beginDate", beginDateStr);
							paramVals.put("endDate", endDateStr);
							paramVals.put("beginTime", beginTimeStr);
							paramVals.put("endTime", endTimeStr);
							paramVals.put("transitDevices", transitDevices);
							paramVals.put("limitCount",visitorApply.getLimitCount() == null ? "0" : String.valueOf(visitorApply.getLimitCount()));
							paramVals.put("transitCount", "0");
							String personName = visitorApply.getVisitorName();
							String personType = VisitorPersonType.getTransitCodeByDbCode(visitorApply.getSourceType());
							String personId = visitorApply.getVisitorId() + "";
							paramVals.put("personName", personName);
							paramVals.put("personType", personType);
							paramVals.put("personId", personId);
							String projectId = visitorApply.getProjectId()+"";
							paramVals.put("projectId", projectId);
							String relationId = visitorApply.getRelationId()+"";
							paramVals.put("relationId", relationId);
							if(CacheManager.exists(formatStrKey)) {
								CacheManager.delExpire(formatStrKey);
							}
							writeVisitorsCache(formatStrKey, paramVals,getCurrentDayCompSeconds(endDateStr+" "+endTimeStr));
							
							String[] dvs = StringUtils.split(transitDevices, ",");
							for(int i = 0; i < dvs.length; i++) {
								dvs[i] = StringUtils.trim(dvs[i]).substring(2);
							}
							String cacheTableKey = String.format(TOP_LEVEL_TRANSIT_CACHE_KEY_FORMAT, visitorApply.getTransitCode());
							List<Map<String, Object>> topLevelArrangeIdDeviceCodeMapList = deviceMapper.selectTopLevelArrangeIdByDeviceCodes(dvs,visitorApply.getProjectId()+"");
							StringBuilder topLevelDeviceCodeAndSerialNumberSb = new StringBuilder();
							String topLevelArrangeId = topLevelArrangeIdDeviceCodeMapList.get(0).get("arrangeId").toString();
							for(Map<String, Object> item : topLevelArrangeIdDeviceCodeMapList) {
								if(topLevelArrangeId.equals(item.get("arrangeId").toString())) {
									topLevelDeviceCodeAndSerialNumberSb.append(item.get("deviceCode").toString()).append(",").append(item.get("serialNumber").toString()).append(",");
								}
							}
							if(CacheManager.exists(cacheTableKey)) {// 缓存存在，检测次数
								CacheManager.delExpire(cacheTableKey);
							}
							String topLevelDevicesCacheStr = topLevelDeviceCodeAndSerialNumberSb.toString();
							CacheManager.setHTableKeyVal(cacheTableKey, "field—top-level", topLevelDevicesCacheStr);
							// 设置通行次数0，等待开门成功响应增加1：field-int-count;field-out-count
							CacheManager.setHTableKeyVal(cacheTableKey, "field-int-count", 0);
							CacheManager.setHTableKeyVal(cacheTableKey, "field-out-count", 0);
							// 设置有效时间到晚上23点59分59秒
							CacheManager.expire(cacheTableKey, getCurrentDayCompSeconds(endDateStr+" "+endTimeStr));
							
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 通行日志放入缓存
	 */
	private void writeVisitorsCache(String formatStrKey, Map<String, String> paramVals, int senconds) {
		try {
			Set<String> setKeys = paramVals.keySet();
			for (String k : setKeys) {
				CacheManager.setHTableKeyVal(formatStrKey, k, paramVals.get(k));
			}
			CacheManager.expire(formatStrKey, senconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

