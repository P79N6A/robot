package com.bossbutler.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.system.Invitation;

public interface InvitationMapper extends IBaseMapper<Invitation, Invitation, Invitation> {

	Invitation queryInvitationByCode(@Param("code") String code);

	/**
	 * 更新邀请码状态及时间
	 * @param params
	 * @return
	 */
	int updateInvitationForReg(Map<String,Object> params);
	
}