package com.bossbutler.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bossbutler.pojo.ControllerView;
import com.bossbutler.service.UserService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;

/**
 * <p>
 * 不需要参数token的系统功能.
 * </p>
 * 
 * @author wq
 *
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

	private static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * 用户登录
	 * 
	 * @param ControllerView
	 *            data实体为user
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String login(@ModelAttribute ControllerView view) {
		try {
			return userService.queryUserList(view, logger);
		} catch (Exception e) {
			logger.error("login参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}

}