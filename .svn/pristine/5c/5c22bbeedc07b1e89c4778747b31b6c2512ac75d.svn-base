package com.bossbutler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.account.RAppAccountPushModel;

public interface RAppAccountPushMapper {

	int selectCountByAppCodeNameAndAccountId(@Param("accountId") String accountId, @Param("appCodeName") String appCodeName);

	/**
	 * 方法描述:查询个人菜单权限
	 * 创建人: llc
	 * 创建时间: 2018年11月14日 上午9:49:12
	 * @param model
	 * @return List<RAppAccountPushModel>
	*/
	List<RAppAccountPushModel> getRAppByAccountId(RAppAccountPushModel model);
}
