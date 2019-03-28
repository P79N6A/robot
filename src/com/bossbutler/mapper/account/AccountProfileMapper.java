package com.bossbutler.mapper.account;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.account.AccountProfileModel;


public interface AccountProfileMapper {
	/**
	 * 方法描述:获取账号配置信息
	 * 创建人: llc
	 * 创建时间: 2018年11月8日 下午2:44:37
	 * @param params
	 * @return String
	*/
	String getAccountProfileByParam(@Param("accountId") String accountId,@Param("type") String type);
	
	/**
	 * 方法描述:动态查询
	 * 创建人: llc
	 * 创建时间: 2018年11月9日 上午10:09:00
	 * @param model
	 * @return List<AccountProfileModel>
	*/
	List<AccountProfileModel> queryDynamic(@Param("model") AccountProfileModel model);
	
	/**
	 * 方法描述:动态保存
	 * 创建人: llc
	 * 创建时间: 2018年11月8日 下午6:16:34
	 * @param model
	 * @return int
	*/
	int insertDynamic(@Param("model") AccountProfileModel model);
	
	/**
	 * 方法描述:更新
	 * 创建人: llc
	 * 创建时间: 2018年11月9日 上午9:55:53
	 * @param model
	 * @return int
	*/
	int update(@Param("model") AccountProfileModel model);

}
