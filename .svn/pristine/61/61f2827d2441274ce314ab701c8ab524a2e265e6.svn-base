package com.bossbutler.mapper.passwordchange;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.Passwordchange;

public interface MobileVerificationMapper {
	Passwordchange querymobile(@Param("accountId") String accountId,@Param("mobile") String mobile);
	int updatePassword(@Param("accountId") String accountId,@Param("password") String password);
	int updatephone(@Param("accountId") String accountId,@Param("phone") String phone);
	int updateEmpPhone(@Param("accountId") String accountId,@Param("phone") String phone);
}
