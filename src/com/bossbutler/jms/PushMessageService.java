package com.bossbutler.jms;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jms.core.JmsTemplate;

import com.bossbutler.common.SpringContextHolder;
import com.bossbutler.pojo.PushMessage;
import com.bossbutler.util.RedisConstant;
import com.company.common.redis.active.CacheManager;
import com.company.util.CommonUtil;

public class PushMessageService {

	/**
	 * 发送队列日志
	 * 
	 * @param pushAppTypeTemplate
	 *            推送手机应用模板函数库
	 * @param EToAppType
	 *            推送手机应用推送的目的应用
	 * @param pushTableKey
	 *            推送体表key
	 * @param pushTableUserKey
	 *            推送接收值表key
	 * @param greater100
	 *            true/false(true表示人数大于100; false表示人数小于等于100)
	 */
	private boolean sendPushQueueMessage(EPushAppType pushAppTypeTemplate, String messageDomain, String message,
			List<String> receivers) {

		String formateKey = CommonUtil.generateShortUuid() + new Date().getTime();
		String pushTableKey = String.format(RedisConstant.REDIS_TABLE_PUSH_MESSAGE_KEY, formateKey);
		String pushTableUserKey = String.format(RedisConstant.REDIS_TABLE_PUSH_MESSAGE_RECEIVER_KEY, formateKey);
		boolean greater100 = false;
		if (receivers.size() > 100) {
			greater100 = true;
		}

		try {
			CacheManager.setHTableKeyVal(pushTableKey, "messageDomain", messageDomain);
			CacheManager.setHTableKeyVal(pushTableKey, "message", message);
			for (int i = 0; i < receivers.size(); i++) {
				String receiver = receivers.get(i);
				if (StringUtils.isNotBlank(receiver)) {
					CacheManager.setListVal(pushTableUserKey, receiver);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		PushMessage pm = new PushMessage(pushTableKey, pushTableUserKey, greater100);
		pushAppTypeTemplate.getJmsTemplate().convertAndSend(pm);
		return true;
	}

	/**
	 * i包办推送任务发布
	 * 
	 * @param message
	 * @param messageDomain
	 * @param receivers
	 * @return
	 */
	public boolean iBaobanPushSendMessage(String messageDomain, String message, List<String> receivers) {
		if (StringUtils.isBlank(message) || StringUtils.isBlank(messageDomain) || receivers == null
				|| receivers.size() == 0) {
			return false;
		}
		return sendPushQueueMessage(EPushAppType.IBAOBAN, messageDomain, message, receivers);
	}

	/**
	 * 包办管家推送任务发布
	 * 
	 * @param messageDomain
	 * @param message
	 * @param receivers
	 * @return
	 */
	public boolean baobanManagerPushSendMessage(String messageDomain, String message, List<String> receivers) {
		if (StringUtils.isBlank(message) || receivers == null || receivers.size() == 0) {
			return false;
		}

		return sendPushQueueMessage(EPushAppType.BAOBANMANAGER, messageDomain, message, receivers);
	}
	
	/**
	 * 安荣物业推送任务发布
	 * 
	 * @param messageDomain
	 * @param message
	 * @param receivers
	 * @return
	 */
	public boolean aRPMPushSendMessage(String messageDomain, String message, List<String> receivers) {
		if (StringUtils.isBlank(message) || receivers == null || receivers.size() == 0) {
			return false;
		}
		
		return sendPushQueueMessage(EPushAppType.ARPM, messageDomain, message, receivers);
	}
	
	/**
	 * 中星城推送任务发布
	 * 
	 * @param messageDomain
	 * @param message
	 * @param receivers
	 * @return
	 */
	public boolean sHZXPushSendMessage(String messageDomain, String message, List<String> receivers) {
		if (StringUtils.isBlank(message) || receivers == null || receivers.size() == 0) {
			return false;
		}
		
		return sendPushQueueMessage(EPushAppType.SHZX, messageDomain, message, receivers);
	}
	
	/**
	 * 世纪商贸推送任务发布
	 * 
	 * @param messageDomain
	 * @param message
	 * @param receivers
	 * @return
	 */
	public boolean sJSMPushSendMessage(String messageDomain, String message, List<String> receivers) {
		if (StringUtils.isBlank(message) || receivers == null || receivers.size() == 0) {
			return false;
		}
		
		return sendPushQueueMessage(EPushAppType.SJSM, messageDomain, message, receivers);
	}

}

enum EPushAppType {
	IBAOBAN {

		@Override
		public JmsTemplate getJmsTemplate() {
			return SpringContextHolder.getBean("ibaobanPushQueueSendTemplate");
		}
	},
	BAOBANMANAGER {

		@Override
		public JmsTemplate getJmsTemplate() {
			return SpringContextHolder.getBean("baobanManagerPushQueueSendTemplate");
		}
	},
	ARPM {

		@Override
		public JmsTemplate getJmsTemplate() {
			return SpringContextHolder.getBean("aRPMPushQueueSendTemplate");
		}
	},
	SHZX {

		@Override
		public JmsTemplate getJmsTemplate() {
			return SpringContextHolder.getBean("sHZXPushQueueSendTemplate");
		}
	},
	SJSM {

		@Override
		public JmsTemplate getJmsTemplate() {
			return SpringContextHolder.getBean("sJSMPushQueueSendTemplate");
		}
	};
	// abstract修饰方法，强制每个枚举实现该方法
	public abstract JmsTemplate getJmsTemplate();
}