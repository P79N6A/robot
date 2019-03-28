package com.bossbutler.service.myinfo;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.myinfo.MyinfoMenuMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.pojo.CompanyPojo;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.MyCompanyPojo;
import com.bossbutler.pojo.MyinfoMorePojo;
import com.bossbutler.pojo.MyselfinfoPojo;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.image.ImageService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.company.exception.AppException;
import com.company.util.BeanUtility;

@Service
public class MyinfoMenuService extends BasicService {
	@Autowired
	private MyinfoMenuMapper mymapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private ImageService imageService;

	public String queryMyinfoMore(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		MyselfinfoPojo pojo = BeanUtility.jsonStrToObject(dataJson, MyselfinfoPojo.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		// 验证参数结束
		MyinfoMorePojo pojo1 = mymapper.querymore(pojo.getAccountId());
		if (pojo1 == null)
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_NOT_EXIST_EMP, dateTime);
		//用户头像图片
		pojo1.setHeadpath(imageService.getAccountHeadPath(pojo.getAccountId()));
		pojo1.setScore("4.9");
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String queryMyinfo(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		MyselfinfoPojo in = BeanUtility.jsonStrToObject(dataJson, MyselfinfoPojo.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		// 验证参数结束
		MyselfinfoPojo pojo = mymapper.queryinfo(in.getAccountId());
		if (pojo == null){
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_NOT_EXIST_EMP, dateTime);
		}
		pojo.setCompanies(mymapper.queryCompanies(in.getAccountId()));
		//用户头像图片
		pojo.setHeadpath(imageService.getAccountHeadPath(pojo.getAccountId()));
		String resultData = BeanUtility.objectToJsonStr(pojo, true);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String queryMyCompany(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		CompanyPojo pojo = BeanUtility.jsonStrToObject(dataJson, CompanyPojo.class, true);
		if (pojo.getCompanyId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		MyCompanyPojo pojo1 = mymapper.queryCompany(pojo.getCompanyId());
		if (pojo1 == null)
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String changeMyinfo(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		MyinfoMorePojo pojo = BeanUtility.jsonStrToObject(dataJson, MyinfoMorePojo.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getName())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_LOGIN_NAME_NULL, dateTime);
		}
		// 验证参数结束
		if (StringUtils.isNotBlank(pojo.getSex())) {
			if ("女".equals(pojo.getSex())) {
				pojo.setSex("0");
			} else if ("男".equals(pojo.getSex())) {
				pojo.setSex("1");
			} else {
				return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
			}
		}
		int row = mymapper.updateMyinfo(pojo);
		if (row > 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
	}
	public String updateSex(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		MyinfoMorePojo pojo = BeanUtility.jsonStrToObject(dataJson, MyinfoMorePojo.class, true);
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getSex())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_SEX_NULL, dateTime);
		}
		int row = mymapper.updateSex(pojo);
		if (row > 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
	}
	public String updateLoginId(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		MyinfoMorePojo pojo = BeanUtility.jsonStrToObject(dataJson, MyinfoMorePojo.class, true);
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getName())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_LOGIN_ID_NULL, dateTime);
		}
		if (pojo.getName().length()>20) {
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_FORMAT_GREATER20, dateTime);
		}
		String regEx="^[A-Z,a-z,0-9]*$";
		boolean result=Pattern.compile(regEx).matcher(pojo.getName()).find();
		if (!result) {
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_FORMAT_ERROR);
		}
		Account a = accountMapper.queryUser(pojo.getName(), null);
		if (a != null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_EXISTED);
		}
		int row = mymapper.updateLoginId(pojo);
		if (row > 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
	}
}
