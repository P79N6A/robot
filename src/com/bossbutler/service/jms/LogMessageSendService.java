package com.bossbutler.service.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.bossbutler.pojo.LogMessage;

/**
 * 消息组件
 * 
 * @author wq
 */
@Service
public class LogMessageSendService {

	@Autowired
	private JmsTemplate logQueueSendTemplate;

	public void sendLogMessage(final LogMessage message) {
		logQueueSendTemplate.convertAndSend(message);
	}

}
