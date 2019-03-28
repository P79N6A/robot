package com.bossbutler.service.passwordchange;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.passwordchange.MobileVerificationMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.Passwordchange;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.AccountOut;
import com.bossbutler.util.CommonMobileMsgSend;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MacConfirm;
import com.bossbutler.util.WriteToPage;
import com.company.util.BeanUtility;
@Service
public class MobileVerificationService {
	@Autowired
	private MobileVerificationMapper  mobilemapper;
	@Autowired
	private AccountMapper accountMapper;
	public String querySendMessage(ControllerView view) throws Exception{
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		Passwordchange pojo = BeanUtility.jsonStrToObject(dataJson, Passwordchange.class, true);
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return	 WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		Passwordchange pojo1=mobilemapper.querymobile(pojo.getAccountId(),pojo.getPhone());
		String msg=pojo1.getCount();
		if(Integer.parseInt(msg)>0){
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		}else{
			return WriteToPage.setResponseMessage("{}", IfConstant.MOBILE_CODE_UNRIGHT, dateTime);
		}
	}
	public String updPassword(ControllerView view) throws Exception{
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		Passwordchange pojo = BeanUtility.jsonStrToObject(dataJson, Passwordchange.class, true);
		if (StringUtils.isNotBlank(pojo.getNewPassword())) {
			if (pojo.getNewPassword().length()<8) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_PASSWORD_NOT_LESS_BIT8, dateTime);
			}
			Account a = accountMapper.queryUserById(pojo.getAccountId());
			if (a == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
			}
			//验证原密码
			a = this.accountMapper.queryUser(a.getAccountCode()+"", pojo.getPassword());
			if (a == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.SETTING_PASSWORD_ERROR, dateTime);
			}
			//修改密码
			int row=mobilemapper.updatePassword(a.getAccountId(),pojo.getNewPassword());
			if (row <=0 ) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
			}
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		}
		Account account = accountMapper.queryUser(pojo.getLoginName(), null);
		int row=mobilemapper.updatePassword(account.getAccountId(),pojo.getPassword());
		if(row>0){
			AccountOut out = new AccountOut();
			BeanUtility.bindObject(out, account);
			// 生成返回实体json
			String resultData = BeanUtility.objectToJsonStr(out, false);
			// 登录成功
			return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
		}else{
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
	}
	/**
	 * 原接口
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String updPhone(ControllerView view) throws Exception{
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		Passwordchange pojo = BeanUtility.jsonStrToObject(dataJson, Passwordchange.class, true);
		if (!isMobile(pojo.getPhone())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.SETTING_PHONE_FORMAT_ERROR, dateTime);
		}
		Passwordchange pojo1=mobilemapper.querymobile(pojo.getAccountId(),pojo.getPhone());
		String msg=pojo1.getCount();
		//这里 判断是否已经绑定啦
		if(Integer.parseInt(msg)>0){
			return WriteToPage.setResponseMessage("{}", IfConstant.SETTING_PHONE_BINDING_MYSELF, dateTime);
		}
		Passwordchange pCount = mobilemapper.querymobile(null,pojo.getPhone());
		if(Integer.parseInt(pCount.getCount())>0){
			return WriteToPage.setResponseMessage("{}", IfConstant.SETTING_PHONE_BINDINGED, dateTime);
		}
		// 获得手机识别码
		String sn = pojo.getSn();
		String mobilenu = pojo.getPhone();
		String codeTemp = pojo.getContent();
		if (StringUtils.isBlank(sn) || StringUtils.isBlank(mobilenu) || StringUtils.isBlank(codeTemp)) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		// 返回验证码
		Object codeObj = CommonMobileMsgSend.getSendMobileCode(sn + mobilenu);
		if (codeObj == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.MOBILE_CODE_UNUSED, dateTime);
		}
		String[] codeStrArray = codeObj.toString().split(",");
		String code = codeStrArray[0];
		if (!codeTemp.equals(code)) {
			return WriteToPage.setResponseMessage("{}", IfConstant.MOBILE_CODE_UNRIGHT, dateTime);
		}
		int row=mobilemapper.updatephone(pojo.getAccountId(),pojo.getPhone());
		//更新emp表手机号
		this.mobilemapper.updateEmpPhone(pojo.getAccountId(),pojo.getPhone());
		//这里判断是修改成功了没
		if(row>0){
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		}else{
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
	}
	public String updatePhone(ControllerView view) throws Exception{
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		Passwordchange pojo = BeanUtility.jsonStrToObject(dataJson, Passwordchange.class, true);
		if (!isMobile(pojo.getPhone())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.SETTING_PHONE_FORMAT_ERROR, dateTime);
		}
		Passwordchange pojo1=mobilemapper.querymobile(pojo.getAccountId(),pojo.getPhone());
		String msg=pojo1.getCount();
		//这里 判断是否已经绑定啦
		if(Integer.parseInt(msg)>0){
			return WriteToPage.setResponseMessage("{}", IfConstant.SETTING_PHONE_BINDING_MYSELF, dateTime);
		}
		Passwordchange pCount = mobilemapper.querymobile(null,pojo.getPhone());
		if(Integer.parseInt(pCount.getCount())>0){
			return WriteToPage.setResponseMessage("{}", IfConstant.SETTING_PHONE_BINDINGED, dateTime);
		}		
		int row=mobilemapper.updatephone(pojo.getAccountId(),pojo.getPhone());
		//更新emp表手机号
		this.mobilemapper.updateEmpPhone(pojo.getAccountId(),pojo.getPhone());
		//这里判断是修改成功了没
		if(row>0){
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		}else{
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
	}
	/**
	   * 手机号验证
	   * @author ：shijing
	   * 2016年12月5日下午4:34:46
	   * @param  str
	   * @return 验证通过返回true
	   */
	  public static boolean isMobile(final String str) {
	      Pattern p = null;
	      Matcher m = null;
	      boolean b = false;
	      p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
	      m = p.matcher(str);
	      b = m.matches();
	      return b;
	  }
	  /**
	   * 电话号码验证
	   * @author ：shijing
	   * 2016年12月5日下午4:34:21
	   * @param  str
	   * @return 验证通过返回true
	   */
	  public static boolean isPhone(final String str) {
	      Pattern p1 = null, p2 = null;
	      Matcher m = null;
	      boolean b = false;
	      p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
	      p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
	      if (str.length() > 9) {
	         m = p1.matcher(str);
	         b = m.matches();
	      } else {
	          m = p2.matcher(str);
	         b = m.matches();
	      }
	      return b;
	  }
	  public static void main(String[] args) {
		System.out.println(isMobile("1234567"));
	  }
}
