package com.bossbutler.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bossbutler.util.IfConstant;
import com.bossbutler.util.RedisConstant;
import com.bossbutler.util.WriteToPage;
import com.bossbutler.util.enums.IdWorkerBuider;
import com.company.common.redis.active.CacheManager;
import com.company.util.IdWorker;

/**
 * <p>
 * controller基础公共类.
 * </p>
 * 
 * @author wq
 *
 */
public class BaseController {

	private static Logger logger = Logger.getLogger(BaseController.class);

	/**
	 * 抛错处理类.
	 * 
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	public String exception(HttpServletRequest request, Exception e) {
		logger.error("controller类请求报错:" + e.getMessage(), e);
		return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
	}

	public IdWorker getIdWorker(final HttpServletRequest request) throws Exception {
		return IdWorkerBuider.IDWORKER.getIdWorker();
	}

	/**
	 * 放入缓存一段时间
	 * 
	 * @param formatStrKey
	 * @param paramVals
	 * @param senconds
	 */
	public void writeRedisCache(String formatStrKey, Map<String, String> paramVals, int senconds) {
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

	/**
	 * 读取缓存
	 * 
	 * @param formatStrKey
	 * @param fields
	 * @return
	 */
	public Map<String, String> readRedisCache(String formatStrKey, String... fields) {
		try {
			if (!CacheManager.exists(formatStrKey)) {
				return null;
			}
			Map<String, String> paramVals = new HashMap<>();
			for (int i = 0; i < fields.length; i++) {
				Object valObj = CacheManager.getHTableKeyVal(formatStrKey, fields[i]);
				if (valObj == null) {
					return null;
				} else {
					paramVals.put(fields[i], (String) valObj);
				}
			}
			return paramVals;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过redis前缀生成唯一主键
	 * 
	 * @param befKey
	 * @return
	 */
	public String createAppHFiveUniRedisKey(String afKey) {
		return String.format(RedisConstant.REDIS_TABLE_HTML_FIVE_PAGE_BEFORE_KEY, afKey);
	}

}
