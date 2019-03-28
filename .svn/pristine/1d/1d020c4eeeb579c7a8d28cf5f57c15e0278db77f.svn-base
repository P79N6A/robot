package com.bossbutler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.ConfigureMapper;
import com.bossbutler.pojo.MobileMsgTemplate;
import com.bossbutler.pojo.SendMobileMessage;

@Service
public class ConfigureService {

	@Autowired
	private JmsTemplate sendMobileMessageTemplate;

	@Autowired
	private ConfigureMapper configureMapper;

	public MobileMsgTemplate getMobileTemplateConfs(String ttype, String appCodeDescrib) {
		return configureMapper.selectMobileTemplateConfs(ttype, appCodeDescrib);
	}

	public void setMobileSendlog(SendMobileMessage sendMobileMessage) {
		sendMobileMessageTemplate.convertAndSend(sendMobileMessage);
	}

}
