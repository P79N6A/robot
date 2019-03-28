package com.bossbutler.common;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import com.bossbutler.service.ConfigureDbService;
import com.bossbutler.util.SessionConst;
import com.bossbutler.util.enums.IdWorkerBuider;
import com.company.cnvalid.analysisxml.RegxRuleStrRuler;
import com.company.cnvalid.analysisxml.RegxRuler;
import com.company.cnvalid.analysisxml.RequireRuler;
import com.company.cnvalid.util.FormValidUtil;

public class InitDataListener implements InitializingBean, ServletContextAware {

	private Logger logger = Logger.getLogger(InitDataListener.class);

	@Autowired
	private ConfigureDbService configureDbService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.loadInitValidRuleConfig();
	}

	@Override
	public void setServletContext(ServletContext application) {
		Map<String, Integer> dbIdMap = configureDbService.getCenterIdWorkId(InitConf.getIdworkDistrictField(),
				InitConf.getIdworkStationField());
		try {
			Integer datacenterId = dbIdMap.get(SessionConst.APP_DATACENTERID_KEY);
			Integer workerId = dbIdMap.get(SessionConst.APP_WORKERID_KEY);
			IdWorkerBuider.IDWORKER.build(workerId.longValue(), datacenterId.longValue());
		} catch (Exception e) {
			logger.error("初始化IdWorker出错", e);
			throw new RuntimeException(e);
		}
//		try {
//			WsClient.buildInstance("helloWorld", IHelloWorld.class);
//		} catch (Exception e) {
//			logger.error("初始化WsClient出错", e);
//			throw new RuntimeException(e);
//		}
	}

	/**
	 * 将配置规则写入缓存服务器
	 * 
	 * @throws Exception
	 */
	private void loadInitValidRuleConfig() throws Exception {
		FormValidUtil.buildValidMap("formvalidation.xml", "com.bossbutler.util.FormValidMethod", new RequireRuler(),
				new RegxRuleStrRuler(), new RegxRuler());
	}

}
