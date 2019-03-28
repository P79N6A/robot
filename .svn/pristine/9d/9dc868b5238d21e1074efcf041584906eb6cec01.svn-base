package com.bossbutler.service.system;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.TransitResultBizMapper;

/**
 * <p>
 * 设备通行名单管理.
 * </p>
 * 
 * @author wangqiao
 *
 */
@Service
public class TransitResultBizService{

	@Autowired
	private TransitResultBizMapper transitListResultBizMapper;

	public int queryTransitListResultBizCount(long projectId, int accountCode, String deviceCode, String transitType,
			String transitCode) {
		return transitListResultBizMapper.selectTransitListResultBizCount(projectId, accountCode, deviceCode, transitType, transitCode);
	}

}