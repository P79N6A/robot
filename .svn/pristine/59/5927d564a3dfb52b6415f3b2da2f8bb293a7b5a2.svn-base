package com.bossbutler.jms;

import org.apache.log4j.Logger;

import com.bossbutler.common.SpringContextHolder;
import com.bossbutler.pojo.LogMessage;
import com.bossbutler.service.jms.LogManagerService;

/**
 * <p>
 * 消息消费端.
 * </p>
 * 
 * @author wangqiao
 */
public class LogMessageConsumer {

	private static final Logger logger = Logger.getLogger(LogMessageConsumer.class);

	public void receive(LogMessage logMessage) {
		LogManagerService logManagerService = SpringContextHolder.getBean("logManagerService");
		try {
			int count = logManagerService.saveLog(logMessage);
			logger.debug("server端写入日志" + count + "条");
		} catch (Exception e) {
			logger.error("server端写入日志异常", e);
		}
	}

}
