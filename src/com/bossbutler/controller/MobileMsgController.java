package com.bossbutler.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bossbutler.common.AppElementManagerInterceptor;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.service.MobileMsgService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;

@RestController
@RequestMapping("mobile")
public class MobileMsgController extends BaseController {

	private static Logger logger = Logger.getLogger(MobileMsgController.class);

	@Autowired
	private MobileMsgService mobileMsgService;
	/**发送短信
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/send/message", method = RequestMethod.POST)
	public String sendMessage(HttpServletRequest request, @ModelAttribute ControllerView view) {
		String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
		try {
//			MobileMsg mobileMsg = new MobileMsg();
//			mobileMsg.setPhone("18615203792");
//			mobileMsg.setName("xbj");
//			mobileMsg.setTemplateType("05");
//			mobileMsg.setContents(new String[] { "用户你好", "2016年7月17日11:58:55", "1231231231hello哈哈" });
			return mobileMsgService.querySendMessage(view,logger, appCodeName);
		} catch (Exception e) {
			logger.error("发送短信出错:" + e.getMessage(), e);
			String str = e.getMessage();
			str = str.substring(str.lastIndexOf("=")+1,str.indexOf("]"));
			return WriteToPage.setResponseMessageForError(str, IfConstant.UNKNOWN_ERROR.getCode());
		}
	}

	/**
	 * 发送手机验证码
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/send/code", method = RequestMethod.POST)
	public String sendCode(HttpServletRequest request, @ModelAttribute ControllerView view) {
		String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
			try {
				return mobileMsgService.querySendCode(view, logger, appCodeName);
			} catch (Exception e) {
				logger.error("发送短信出错:" + e.getMessage(), e);
				String str = e.getMessage();
				str = str.substring(str.lastIndexOf("=")+1,str.indexOf("]"));
				return WriteToPage.setResponseMessageForError(str, IfConstant.UNKNOWN_ERROR.getCode());
			}
	}
	/**
	 * 发送手机验证码
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/send/code/{type}", method = RequestMethod.POST)
	public String sendCodeType(HttpServletRequest request, @ModelAttribute ControllerView view,@PathVariable("type") String type) {
		String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
		try {
			return mobileMsgService.querySendCode(view,type,logger, appCodeName);
		} catch (Exception e) {
			logger.error("发送短信出错:" + e.getMessage(), e);
			String str = e.getMessage();
			str = str.substring(str.lastIndexOf("=")+1,str.indexOf("]"));
			return WriteToPage.setResponseMessageForError(str, IfConstant.UNKNOWN_ERROR.getCode());
		}
	}

	/**
	 * 验证发送的手机验证码
	 * 
	 * @param view
	 * @return
	 */
	@RequestMapping(value = "/check/send/code", method = RequestMethod.POST)
	public String checkSendCode(@ModelAttribute ControllerView view) {
		try {
			return mobileMsgService.queryCheckSendCode(view, logger);
		} catch (Exception e) {
			logger.error("checkSendCode参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}
}