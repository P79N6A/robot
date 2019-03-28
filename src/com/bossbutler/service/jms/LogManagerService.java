package com.bossbutler.service.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.LogManagerMapper;
import com.bossbutler.pojo.LogMessage;

/**
 * <p>
 * 日志记录.
 * </p>
 * 
 * @author wangqiao
 */
@Service
public class LogManagerService {

	@Autowired
	LogManagerMapper logManagerMapper;

	public int saveLog(LogMessage logMessage) throws Exception {
		return logManagerMapper.insert(logMessage);
	}

}
