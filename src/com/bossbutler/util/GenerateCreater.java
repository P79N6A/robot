package com.bossbutler.util;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.log4j.Logger;

import com.bossbutler.common.SpringContextHolder;
import com.bossbutler.common.ZkCuratorService;
import com.bossbutler.service.NoGeneratorService;

/**
 * 号码管理类
 * 
 * @author Hzhang
 */
/**
 * @author wangqiao
 *
 */
public class GenerateCreater {

	private static Logger logger = Logger.getLogger(GenerateCreater.class);

	private static String getNoGenerate(String usedCat) {
		String no = null;
		
		NoGeneratorService generatorService = SpringContextHolder.getBean("noGeneratorService");
		System.out.println(generatorService);
		try {
			synchronized (generatorService) {
				no = generatorService.getNoGeneratorByUsedCat(usedCat);
			}
				
		} catch (Exception e) {
			logger.error("主键生成失败：" + e.getMessage(), e);
		} 
		if (StringUtils.isBlank(no)) {
			new Exception();
			logger.error("未找到相应主键生成配置！");
		}
		return no;
	}

	/**
	 * account表
	 * 
	 * @return
	 */
	public static String getAccountId() {
		return getNoGenerate("AccountCode");
	}

	/**
	 * service_bill表
	 * 
	 * @return
	 */
	public static String getBillCode() {
		return getNoGenerate("BillCode");
	}

	/**
	 * invitation表
	 * 
	 * @return
	 */
	public static String getTransitCode() {
		return getNoGenerate("TransitCode");
	}

	/**
	 * Compaint表
	 * @return
	 */
	public static String getCompaintCode(){
		return getNoGenerate("ComplaintCode");
	}

	/**
	 * 生成32位编码
	 * 
	 * @return string
	 */
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid.toUpperCase();
	}

	/**
	 * 自定义规则生成32位编码
	 * 
	 * @return string
	 */
	public static String getUUIDByRules(String rules) {
		int rpoint = 0;
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int length = 32;
		for (int i = 0; i < length; i++) {
			if (rules != null) {
				rpoint = rules.length();
				int randNum = rand.nextInt(rpoint);
				generateRandStr.append(rules.substring(randNum, randNum + 1));
			}
		}
		return generateRandStr + "";
	}

}
