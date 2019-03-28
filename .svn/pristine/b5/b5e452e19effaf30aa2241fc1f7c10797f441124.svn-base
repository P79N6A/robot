package com.bossbutler.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bossbutler.common.InitConf;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;

/**
 * <p>
 * 登录获取配置信息.
 * </p>
 * 
 * @author wq
 *
 */
@RestController
@RequestMapping("conf")
public class ConfigController extends BaseController {

	private static Logger logger = Logger.getLogger(ConfigController.class);

	/**
	 * 是否需要定位功能
	 * 
	 * @param ControllerView
	 * @return
	 */
	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		try {
			String data = "{\"flag\":\"" + InitConf.getAppLocationFlag() + "\",\"offset\":\"" + InitConf.getAppLocationOffset() + "\"}";
			return WriteToPage.setResponseMessage(data, IfConstant.SERVER_SUCCESS, null);
		} catch (Exception e) {
			logger.error("是否需要定位功能异常:", e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}

}
