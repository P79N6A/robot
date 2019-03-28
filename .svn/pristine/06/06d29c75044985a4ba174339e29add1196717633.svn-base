package com.bossbutler.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.visitor.TransitLogMapper;
import com.bossbutler.pojo.third.ThirdDRTransitInf;

/**
 * <p>
 * 第三方用户业务管理.
 * </p>
 * 
 * @author wq
 *
 */
@Service
public class ThirdTransitService {

	private static final int pageSize = 10;

	@Autowired
	private TransitLogMapper transitLogMapper;

	public void queryTransitLogForThird(String pageNum, Map<String, Object> condition, Map<String, Object> returnMap,
			Logger logger) {
		Map<String, Object> page = new HashMap<>();
		int startIndex = (Integer.valueOf(pageNum) - 1) * pageSize;
		List<ThirdDRTransitInf> row = transitLogMapper.selectThirdTransitLogListByCondition(startIndex, pageSize,
				condition);
		int total = transitLogMapper.selectThirdTransitLogCountByCondition(condition);
		returnMap.put("page", page);
		returnMap.put("row", row);
		page.put("currentPageNum", pageNum);
		page.put("pageSize", pageSize);
		page.put("total", total);
		int totalPageNum = 0;
		if (total % pageSize == 0) {
			totalPageNum = total / pageSize;
		} else {
			totalPageNum = total / pageSize + 1;
		}
		page.put("totalPageNum", totalPageNum);
	}

}
