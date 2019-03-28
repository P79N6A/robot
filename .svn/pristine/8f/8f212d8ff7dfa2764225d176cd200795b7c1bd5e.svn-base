package com.bossbutler.service.redisCache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.Information.InformationMapper;

@Service
public class AccountRedisCache {
	private static Logger logger = Logger.getLogger(AccountRedisCache.class);
	@Autowired
	InformationMapper informationMapper;
	/**
	 * 根据accountId 获取用户信息
	 * @param accountId
	 * @return
	 */
	public  Map<String,Object> getInformationCountByAccount(String accountId, String appCodeName) {
		Map<String,Object> ret = new HashMap<>();
		Integer sysInfoCount = 0;//系统消息
		Integer proInfoCount = 0;//项目通知
		Integer billInfoCount = 0;//账单提醒
		Integer leaseInfoCount = 0;//租约到期
		Integer totalCount = 0;//租约到期
		try {
//			String redisKey01=String.format(RedisConstant.REDIS_MESSAGE_KEY, accountId,InformationType.InformationType01);
//			sysInfoCount = (int) CacheManager.get(redisKey01);
//			String redisKey02=String.format(RedisConstant.REDIS_MESSAGE_KEY, accountId,InformationType.InformationType02);
//			proInfoCount = (int) CacheManager.get(redisKey02);
//			String redisKey03=String.format(RedisConstant.REDIS_MESSAGE_KEY, accountId,InformationType.InformationType03);
//			billInfoCount = (int) CacheManager.get(redisKey03);
//			String redisKey04=String.format(RedisConstant.REDIS_MESSAGE_KEY, accountId,InformationType.InformationType04);
//			leaseInfoCount = (int) CacheManager.get(redisKey04);
//			totalCount +=sysInfoCount+proInfoCount+billInfoCount+leaseInfoCount;
//			ret.put("sysInfoCount", sysInfoCount);
//			ret.put("proInfoCount", proInfoCount);
//			ret.put("billInfoCount", billInfoCount);
//			ret.put("leaseInfoCount", leaseInfoCount);
//			ret.put("totalCount", totalCount);
			// 业务查询较慢暂停查询-start
//			sysInfoCount = this.getCountByTypeAi(InformationType.InformationType01, accountId, appCodeName);
//			proInfoCount = this.getCountByTypeAi(InformationType.InformationType02, accountId, appCodeName);
//			billInfoCount = this.getCountByTypeAi(InformationType.InformationType03, accountId, appCodeName);
//			leaseInfoCount = this.getCountByTypeAi(InformationType.InformationType04, accountId, appCodeName);
			// 业务查询较慢暂停查询-end
			totalCount +=sysInfoCount+proInfoCount+billInfoCount+leaseInfoCount;
			ret.put("sysInfoCount", sysInfoCount);
			ret.put("proInfoCount", proInfoCount);
			ret.put("billInfoCount", billInfoCount);
			ret.put("leaseInfoCount", leaseInfoCount);
			ret.put("totalCount", totalCount);
		} catch (Exception e) {
			logger.error("AccountRedisCache.getInformationCountByAccount >>>", e);
			e.printStackTrace();
			ret.put("sysInfoCount", 0);
			ret.put("proInfoCount", 0);
			ret.put("billInfoCount", 0);
			ret.put("leaseInfoCount", 0);
			ret.put("totalCount", 0);
			return ret;
		}
		return ret;
	}

	private Integer getCountByTypeAi(String infoType,String accountId, String appCodeName){
		return Integer.parseInt(informationMapper.countUnReadByTypeAi(infoType,accountId, appCodeName));
	}

}
