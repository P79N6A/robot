package com.bossbutler.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bossbutler.pojo.ControllerView;
import com.bossbutler.service.system.InvitationService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
/**
 * <p>
 * 邀请码控制类
 * </p>
 * @author hzhang
 */
@Controller
@RequestMapping("invitation")
public class InvitationController extends BaseController {

	private static Logger logger = Logger.getLogger(InvitationController.class);

	@Autowired
	private InvitationService invitationService;
	/**
	 * 根据邀请码获取注册号码
	 * @return
	 */
	@RequestMapping(value = "checkCode", method = RequestMethod.POST)
	@ResponseBody
	public String getMobile(@ModelAttribute ControllerView view) {

		try {
			return invitationService.queryInvitationByCode(view);
		} catch (Exception e) {
			logger.error("参数错误:"+view.getData(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR);
		}
	}
}