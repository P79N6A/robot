package com.bossbutler.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.org.RoleModel;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.Employee;
import com.bossbutler.pojo.third.ThirdDRUserInf;

public interface AccountMapper {

	Account queryUser(@Param("name") String name, @Param("pwd") String pwd);

	int saveAccount(Account account);
	
	Account queryUserQuery(@Param("name") String name);
	
	Account queryUserByPhone(@Param("phone") String phone);
	/**
	 * 根据ID查询用户
	 * @param phone
	 * @return
	 */
	Account queryUserById(@Param("id") String id);
	/**
	 * 根据参数查询AcountID
	 * @param params
	 * @return
	 */
	List<String> queryAccountIdByParams(Map<String,Object> param);
	
	List<Account> getAccountListByList(List<Long> idList);
	
	List<Employee> checkPower(@Param("accountId")String accountId);

	List<Project> getProjects(@Param("list")List<Long> orgList);

	List<RoleModel> getRolePermissions(@Param("erpRole")Long erpRole,@Param("org_id") long org_id);
	
	int updateLoginTime(@Param("accountId") String accountId,@Param("logInfo") String logInfo);

	//Project quertyProject(@Param("code")String code);

	// 判断是是否为管理组织 0301
	int queryByAccountId(@Param("accountId") String accountId);

	Account selectAccountByQrTransitCode(@Param("transitCode") String transitCode);

	ThirdDRUserInf selectAccountById(@Param("accountId") String accountId);

	Account selectAccountByAccountId(@Param("accountId") String accountId);
	
	

}
