package com.bossbutler.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.regional.RegionalEmpRelationModel;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.AccountTransit;

public interface AccountTransitMapper extends IBaseMapper<AccountTransit, AccountTransit, AccountTransit>{
	/**
	 * 根据帐号类型获取对象
	* @Title: findByTypeAccountId
	* @Description: TODO
	* @param @param accountId
	* @param @param transittypeqr
	* @param @return
	* @return AccountTransit
	* @throws
	 */
	AccountTransit findByTypeAccountId(@Param("accountId") String accountId, @Param("transitType") String transittypeqr);
	/**
	 * 些卡是否已经绑定
	 * @param icCode
	 * @return
	 */
	void update(AccountTransit transit);
	/**
	 * 删除
	 * @param transitId
	 * @return
	 */
	int delete(long transitId);
	Account hasIcCode(@Param("icCode")String icCode);
	/**
	 * 通过用户列表 查找所有AccountM账号
	 * @param list
	 * @return
	 */
	List<Account> getAccontByEmpId(@Param("list")List<RegionalEmpRelationModel> list);

	List<Account> getAccontByEmpId2(@Param("list")List<RegionalEmpRelationModel> list);

}
