package com.bossbutler.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.UserMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.User;
import com.bossbutler.pojo.UserView;
import com.bossbutler.util.CommonMobileMsgSend;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MacConfirm;
import com.bossbutler.util.RedisConstant;
import com.bossbutler.util.WriteToPage;
import com.company.common.redis.active.CacheManager;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;

/**
 * <p>
 * 用户业务管理.
 * </p>
 * 
 * @author wq
 *
 */
@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 登录
	 * 
	 * @param view
	 * @param nocacheStr
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public String queryUserForLogin(ControllerView view, Logger logger) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		String mac = view.getMac();
		// 实体内容转为对象
		UserView userView = BeanUtility.jsonStrToObject(dataJson, UserView.class, true);
//		// 验证签名
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
//
		String dateTime = String.valueOf(new Date().getTime());
		if (!checkMac) {
			logger.warn("login参数mac验证不正确");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		// 设置登录令牌信息
		String oldToken = userView.getOldToken();
		String token = oldToken;
		// 登录用户验证
		User user = userMapper.queryUser(userView.getLoginName(), userView.getPwd());
		if (user == null) {//
			logger.warn("login该用户没有注册:" + userView.getLoginName());
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGIN_ERROR, dateTime);
		} else if (StringUtils.isNotBlank(oldToken)
				&& CacheManager.hexists(RedisConstant.REDIS_TABLE_TOKEN_KEY, oldToken)) {// 如果用户存在且客户端token存在继续使用
			user.setToken(oldToken);
		} else {// 如果用户存在没有oldToke，创建新的token并返回
			token = CommonUtil.generateUuid() + dateTime;
			user.setToken(token);
			//保存到redis
		}
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(user, true);
		// 更缓存中的token
		CacheManager.setHTableKeyVal(RedisConstant.REDIS_TABLE_TOKEN_KEY, token, view.getDateTime());
		// 登录成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String registerUser(ControllerView view, Logger logger) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		String mac = view.getMac();
		// 实体内容转为对象
		User user = BeanUtility.jsonStrToObject(dataJson, User.class, true);
		// 验证签名
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		String dateTime = String.valueOf(new Date().getTime());
		if (!checkMac) {
			logger.warn("queryMsgForSendMsg参数mac验证不正确");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		// 获得手机识别码
		String sn = user.getSn();
		String mobilenu = user.getPhone();
		String codeTemp = user.getVaildCode();
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
		 int i = this.userMapper.saveUser(user);
		 if (i<=0) {
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		 }
		// 验证成功
		String resultData = BeanUtility.objectToJsonStr(user, true);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String queryUserList(ControllerView view, Logger logger) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		String mac = view.getMac();
		// 实体内容转为对象
		User user = BeanUtility.jsonStrToObject(dataJson, User.class, true);
		// 验证签名
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		String dateTime = String.valueOf(new Date().getTime());
		if (!checkMac) {
			logger.warn("queryMsgForSendMsg参数mac验证不正确");
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		List<User> list = this.userMapper.queryUserList();
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(list, true);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

}