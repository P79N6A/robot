package com.bossbutler.util;

import org.apache.commons.lang.StringUtils;

import com.bossbutler.common.SpringContextHolder;
import com.bossbutler.pojo.MobileMsgLog;
import com.bossbutler.pojo.SendMobileMessage;
import com.bossbutler.service.ConfigureService;
import com.company.common.redis.active.CacheManager;

/**
 * 系统session管理
 * 
 * @author wq
 * 
 */
public class CommonMobileMsgSend {

	/**
	 * 短信验证码发送
	 * 
	 * @param unkey
	 *            手机唯一识别码
	 * @param mobilenu
	 *            手机号
	 * @param sessionContent
	 *            session 内容（cookieId|DES）
	 * @param msgTemplateId
	 *            短信模版
	 * @param minute
	 *            有效期分钟
	 * @param msgArray
	 *            参数数组（业务名称，验证码，有限期限分钟）
	 * @throws Exception
	 */
	public static void sendMobileCode(String unkey, String mobilenu, String[] msgArray, String msgTemplateId,
			MobileMsgLog mobilelog, String sessionContent, int minute, String appCodeName) throws Exception {
		if (minute <= 0) {
			return;
		}
		CacheManager.setExpire(SessionConst.MOBILE_SESSION + unkey, minute * 60, sessionContent);
		sendMobileMsg(mobilenu, msgTemplateId, msgArray, mobilelog, appCodeName);
	}

	/**
	 * 获得短信验证码
	 * 
	 * @param unkey
	 *            手机唯一识别码
	 * @return
	 * @throws Exception
	 */
	public static Object getSendMobileCode(String unkey) throws Exception {
		return CacheManager.getExpire(SessionConst.MOBILE_SESSION + unkey);
	}

	/**
	 * 设置mobile 短信，特定模版
	 * 
	 * @param mobilenu
	 * @param msgTemplateId
	 * @param msgArray
	 * @param mobilelog
	 * @throws Exception
	 */
	public static void sendMobileMsg(String mobileNu, String msgTemplateId, String[] msgArray, MobileMsgLog mobilelog, String appCodeName)
			throws Exception {

		if (StringUtils.isNotBlank(msgTemplateId)) {
			SendMobileMessage sendMobileMessage = new SendMobileMessage();
			sendMobileMessage.setMobileNu(mobileNu);
			sendMobileMessage.setMsgArrayStr(StringUtils.join(msgArray, ","));
			sendMobileMessage.setMsgTemplateId(msgTemplateId);
			sendMobileMessage.setOrgId(mobilelog.getOrgId());
			sendMobileMessage.setReceiverName(mobilelog.getReceiverName());
			sendMobileMessage.setAppCodeName(appCodeName);
			getConfigureService().setMobileSendlog(sendMobileMessage);
		}
	}

	private static ConfigureService getConfigureService() {
		return SpringContextHolder.getBean("configureService");
	}

}
