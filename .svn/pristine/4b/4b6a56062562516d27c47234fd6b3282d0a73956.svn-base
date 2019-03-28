package com.bossbutler.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.MobileCodeView;
import com.bossbutler.pojo.MobileMsg;
import com.bossbutler.pojo.MobileMsgLog;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.util.CommonMobileMsgSend;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MacConfirm;
import com.bossbutler.util.MobileConstant;
import com.bossbutler.util.WriteToPage;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.IdWorker;

/**
 * 短信管理
 * 
 * @author wq
 *
 */
@Service
public class MobileMsgService extends BasicService {

	@Autowired
	private AccountMapper accountMapper;
	/**
	 * 发送手机验证码
	 * 
	 * @param view
	 * @param nocacheStr
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public String querySendCode(ControllerView view, Logger logger, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		//		String mac = view.getMac();
		// 实体内容转为对象
		MobileCodeView mobileCodeView = BeanUtility.jsonStrToObject(dataJson, MobileCodeView.class, true);
		// 验证签名
		//		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		String dateTime = String.valueOf(new Date().getTime());
		//		if (!checkMac) {
		//			logger.warn("queryMsgForSendMsg参数mac验证不正确");
		//			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		//		}
		if (StringUtils.isBlank(mobileCodeView.getPhone())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_PHONE_NULL, dateTime);
		}
		//校验是否手机号注册过
		if (StringUtils.isNotBlank(mobileCodeView.getIsCheckReg()) 
				&& "true".equals(mobileCodeView.getIsCheckReg())) {
			Account account = this.accountMapper.queryUserByPhone(mobileCodeView.getPhone());
			if (account == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PHONE_NOT_REGISTER, dateTime);
			}else{
				mobileCodeView.setAccountId(account.getAccountId()+"");
			}
		}else if(StringUtils.isNotBlank(mobileCodeView.getIsCheckReg()) 
				&& "false".equals(mobileCodeView.getIsCheckReg())){
			Account account = this.accountMapper.queryUserByPhone(mobileCodeView.getPhone());
			if (account != null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PHONE_REGISTERED, dateTime);
			}
		}
		// 获得手机识别码
		String sn = mobileCodeView.getSn();
		String mobilenu = mobileCodeView.getPhone();
		if (StringUtils.isBlank(sn) || StringUtils.isBlank(mobilenu)) {
			logger.warn("queryMsgForSendMsg参数sn或num为空");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		// 发送并返回验证码
		String rand = CommonUtil.getForRand();
		long now = new Date().getTime();
		String nu = rand + "," + now;

		MobileMsgLog mobilelog = new MobileMsgLog();
		mobilelog.setLogId(getIdWorker().nextId());
		mobilelog.setReceiverMobile(mobilenu);
		mobilelog.setSendTime(new Date());

		CommonMobileMsgSend.sendMobileCode(sn + mobilenu, mobilenu, new String[] { MobileConstant.REGISTER, rand, "5" }, "01",
				mobilelog, nu, 5, appCodeName);
		mobileCodeView.setContent(rand);
		if(mobileCodeView==null){
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		}	
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(mobileCodeView, true);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 验证发送的手机验证码
	 * 
	 * @param view
	 * @param nocacheStr
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public String queryCheckSendCode(ControllerView view, Logger logger) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		String mac = view.getMac();
		// 实体内容转为对象
		MobileCodeView mobileCodeView = BeanUtility.jsonStrToObject(dataJson, MobileCodeView.class, true);
		// 验证签名
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		String dateTime = String.valueOf(new Date().getTime());
		if (!checkMac) {
			logger.warn("queryMsgForSendMsg参数mac验证不正确");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		// 获得手机识别码
		String sn = mobileCodeView.getSn();
		String mobilenu = mobileCodeView.getPhone();
		String codeTemp = mobileCodeView.getContent();
		if (StringUtils.isBlank(sn) || StringUtils.isBlank(mobilenu) || StringUtils.isBlank(codeTemp)) {
			logger.warn("queryMsgForSendMsg参数sn|num|code为空");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}

		// 返回验证码
		Object codeObj = CommonMobileMsgSend.getSendMobileCode(sn + mobilenu);
		if (codeObj == null) {
			logger.warn("queryMsgForSendMsg缓存验证码过期");
			return WriteToPage.setResponseMessage("{}", IfConstant.MOBILE_CODE_UNUSED, dateTime);
		}
		String[] codeStrArray = codeObj.toString().split(",");
		String code = codeStrArray[0];
		if (!codeTemp.equals(code)) {
			logger.warn("queryMsgForSendMsg缓存验证码匹配错误");
			return WriteToPage.setResponseMessage("{}", IfConstant.MOBILE_CODE_UNRIGHT, dateTime);
		}
		// 验证成功
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String querySendMessage(ControllerView view, Logger logger, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		String mac = view.getMac();
		// 实体内容转为对象
		MobileMsg mobileMsg = BeanUtility.jsonStrToObject(dataJson, MobileMsg.class, true);
		// 验证签名
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		String dateTime = String.valueOf(new Date().getTime());
		if (!checkMac) {
			logger.warn("queryMsgForSendMsg参数mac验证不正确");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		// 获得手机号码
		String mobilenu = mobileMsg.getPhone();
		if (StringUtils.isBlank(mobilenu)) {
			logger.warn("querySendMessage参数num为空");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR);
		}

		MobileMsgLog mobilelog = new MobileMsgLog();
		mobilelog.setLogId(getIdWorker().nextId());
		mobilelog.setReceiverMobile(mobilenu);
		mobilelog.setReceiverName(mobileMsg.getName());
		mobilelog.setSendTime(new Date());
		mobilelog.setOrgId(mobileMsg.getOrgId());
		CommonMobileMsgSend.sendMobileMsg(mobilenu, mobileMsg.getTemplateType(), mobileMsg.getContents(), mobilelog, appCodeName);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS);
	}
	/**
	 * 发送手机验证码
	 * 
	 * @param mobileCodeView
	 * @param idWorker
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public String querySendCode(MobileCodeView mobileCodeView, IdWorker idWorker, Logger logger,String account_id, String appCodeName) throws Exception {

		// 获得手机识别码
		String mobilenu = mobileCodeView.getPhone();
		String sn = mobileCodeView.getSn();
		if (StringUtils.isBlank(mobilenu) && StringUtils.isBlank(sn)) {
			logger.warn("queryMsgForSendMsg参数num为空");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR);
		}

		// 发送并返回验证码
		String rand = CommonUtil.getForRand();
		long now = new Date().getTime();
		String nu = rand + "," + now;

		MobileMsgLog mobilelog = new MobileMsgLog();
		mobilelog.setLogId(idWorker.nextId());
		mobilelog.setReceiverMobile(mobilenu);
		mobilelog.setReceiverName(StringUtils.EMPTY);
		mobilelog.setSendTime(new Date());
		mobilelog.setOrgId(0);

		CommonMobileMsgSend.sendMobileCode(sn + mobilenu, mobilenu,
				new String[] { "", rand, "5" }, "01", mobilelog, nu, 5, appCodeName);
		mobileCodeView.setContent(rand);

		return WriteToPage.setResponseMessage("{accountId:"+account_id+",rand:\"\"}", IfConstant.SERVER_SUCCESS);
	}

	public String querySendCode(ControllerView view, String type, Logger logger, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		MobileCodeView mobileCodeView = BeanUtility.jsonStrToObject(dataJson, MobileCodeView.class, true);
		String dateTime = String.valueOf(new Date().getTime());
		if (StringUtils.isBlank(mobileCodeView.getPhone())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_PHONE_NULL, dateTime);
		}
		String actionType = MobileConstant.REGISTER;
		Account account = this.accountMapper.queryUserByPhone(mobileCodeView.getPhone());
		switch (type) {
		case "retrieve":
			actionType = MobileConstant.RETRIEVE;
			if (account == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PHONE_NOT_REGISTER, dateTime);
			}else{
				mobileCodeView.setAccountId(account.getAccountId()+"");
			}
			break;
		case "binding":
			actionType = MobileConstant.BINDING;
			if (account != null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PHONE_REGISTERED, dateTime);
			}		
			break;
		case "unbundling":
			actionType = MobileConstant.UNBUNDLING;
//			if (account != null) {
//				return WriteToPage.setResponseMessage("{}", IfConstant.PHONE_REGISTERED, dateTime);
//			}		
			break;
		case "invitation":
			actionType = MobileConstant.INVITATION;
//			if (account != null) {
//				return WriteToPage.setResponseMessage("{}", IfConstant.PHONE_REGISTERED, dateTime);
//			}		
			break;
		case "visitor":
			actionType = MobileConstant.VISITOR;
//			if (account != null) {
//				return WriteToPage.setResponseMessage("{}", IfConstant.PHONE_REGISTERED, dateTime);
//			}		
			break;
		default:
			break;
		}
		// 获得手机识别码
		String sn = mobileCodeView.getSn();
		String mobilenu = mobileCodeView.getPhone();
		if (StringUtils.isBlank(sn) || StringUtils.isBlank(mobilenu)) {
			logger.warn("queryMsgForSendMsg参数sn或num为空");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		// 发送并返回验证码
		String rand = CommonUtil.getForRand();
		long now = new Date().getTime();
		String nu = rand + "," + now;

		MobileMsgLog mobilelog = new MobileMsgLog();
		mobilelog.setLogId(getIdWorker().nextId());
		mobilelog.setReceiverMobile(mobilenu);
		mobilelog.setSendTime(new Date());

		CommonMobileMsgSend.sendMobileCode(sn + mobilenu, mobilenu, new String[] { actionType, rand, "5" }, "01",
				mobilelog, nu, 5, appCodeName);
		mobileCodeView.setContent(rand);
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(mobileCodeView, true);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String querySendMessage(MobileMsg mobileMsg, IdWorker idWorker,
			Logger logger, String appCodeName) throws Exception {
		// 获得手机号码
		String mobilenu = mobileMsg.getPhone();
		if (StringUtils.isBlank(mobilenu)) {
			logger.warn("querySendMessage参数num为空");
			return WriteToPage.setResponseMessage(null, IfConstant.UNKNOWN_ERROR);
		}
		MobileMsgLog mobilelog = new MobileMsgLog();
		mobilelog.setLogId(idWorker.nextId());
		mobilelog.setReceiverMobile(mobilenu);
		mobilelog.setReceiverName(mobileMsg.getName());
		mobilelog.setSendTime(new Date());
		mobilelog.setOrgId(mobileMsg.getOrgId());
		CommonMobileMsgSend.sendMobileMsg(mobilenu, mobileMsg.getTemplateType(), mobileMsg.getContents(), mobilelog, appCodeName);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS);
		
	}

}