package com.bossbutler.mapper.myinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.CompanyPojo;
import com.bossbutler.pojo.MyCompany;
import com.bossbutler.pojo.MyCompanyPojo;
import com.bossbutler.pojo.MyinfoMorePojo;
import com.bossbutler.pojo.MyselfinfoPojo;

public interface MyinfoMenuMapper {
	
	MyselfinfoPojo queryinfo(@Param("accountId") String accountId);

	MyinfoMorePojo querymore(@Param("accountId") String accountId);

	List<CompanyPojo> querylist(@Param("accountId") String accountId);

	MyCompanyPojo queryCompany(@Param("companyId") String companyId);

	int updateMyinfo(MyinfoMorePojo myinfo);
	
	List<MyCompany> queryCompanies(String accountId);
	
	int updateSex(MyinfoMorePojo myinfo);

	int updateLoginId(MyinfoMorePojo pojo);
}
