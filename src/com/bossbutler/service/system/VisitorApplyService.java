package com.bossbutler.service.system;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.visitor.VisitorApplyMapper;

/**
 * <p>
 * 设备通行名单管理.
 * </p>
 * 
 * @author wangqiao
 *
 */
@Service
public class VisitorApplyService{

	@Autowired
	private VisitorApplyMapper visitorApplyMapper;

	public int queryVisitorApplyResultCount(long projectId, String applyId, String deviceTransit, String transitCode) {
		return visitorApplyMapper.selectVisitorApplyResultCount(projectId, applyId, deviceTransit, transitCode);
	}


}