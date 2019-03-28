package com.bossbutler.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.pojo.third.ThirdDRProject;
import com.bossbutler.pojo.third.ThirdDRUserInf;

/**
 * <p>
 * 第三方用户业务管理.
 * </p>
 * 
 * @author wq
 *
 */
@Service
public class ThirdAccountService {

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private ProjectMapper projectMapper;

	/**
	 * @param accountId
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public ThirdDRUserInf queryUserForThird(String accountId, Logger logger) throws Exception {
		ThirdDRUserInf userInf = accountMapper.selectAccountById(accountId);
		if (userInf != null) {
			List<ThirdDRProject> projectList = projectMapper.selectProjectListByAccountId(Long.parseLong(accountId));
			userInf.setProjectList(projectList);
		} else {
			userInf = new ThirdDRUserInf();
		}
		return userInf;
	}

}
