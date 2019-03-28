package com.bossbutler.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bossbutler.common.AppElementManagerInterceptor;
import com.bossbutler.exception.TransitListException;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.apk.upgrade.ApkNoticeDto;
import com.bossbutler.pojo.apk.upgrade.ApkNoticeVo;
import com.bossbutler.service.apk.notice.ApkNoticeUpgradeService;
import com.bossbutler.service.system.AccountService;
import com.bossbutler.service.system.InvitationService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.company.util.BeanUtility;
/**
 * <p>
 * 不需要参数token的系统功能.
 * </p>
 * 
 * @author wq
 *
 */
@RestController
@RequestMapping("")
public class LoginController extends BaseController {

	private static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired  AccountService accountService;

	@Autowired
	private InvitationService invitationService;

	@Autowired
	private ApkNoticeUpgradeService apkNoticeUpgradeService;

	/**
	 * 用户登录
	 * @param ControllerView
	 * data实体为user
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, @ModelAttribute ControllerView view) {
		try {
			String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
			return accountService.queryUserForLogin02(view, logger,appCodeName);
		} catch (Exception e) {
			logger.error("login参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	
	
	/**
	 * 用户登录
	 * @param ControllerView
	 * data实体为user
	 * @return
	 */
	@RequestMapping(value = "/login02", method = RequestMethod.POST)
	public String login02(HttpServletRequest request, @ModelAttribute ControllerView view) {
		try {
			String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
			return accountService.queryUserForLogin02(view, logger,appCodeName);
		} catch (Exception e) {
			logger.error("login参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	/**
	 * 用户注册
	 * @param ControllerView
	 *            data实体为user
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, @ModelAttribute ControllerView view) {
		try {
			String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
			String registerUser = accountService.registerUser(view, logger, appCodeName);
			//accountService.synTransit(view);
			return registerUser;
		} catch(TransitListException e){
			return WriteToPage.setResponseMessageForError("有等同步名单，请稍后重试！", IfConstant.UNKNOWN_ERROR.getCode());
		} catch (Exception e) {
			logger.error("login参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	/**
	 * 用户邀请码登录
	 * 
	 * @param loginView
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inviLogin", method = RequestMethod.POST)
	@ResponseBody
	public String inviLogin(HttpServletRequest request, @ModelAttribute ControllerView view) {
		try {
			String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
			String inviLogin = accountService.inviLogin(view, logger, appCodeName);
			//accountService.synTransit(view);
			return inviLogin;
		} catch(TransitListException e){
			return WriteToPage.setResponseMessageForError("有等同步名单，请稍后重试！", IfConstant.UNKNOWN_ERROR.getCode());
		} catch (Exception e) {
			logger.error("login参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	/**
	 * 用户邀请码登录下一步
	 * 
	 * @param loginView
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inviLoginNext", method = RequestMethod.POST)
	@ResponseBody
	public String inviLoginNext(HttpServletRequest request, @ModelAttribute ControllerView view) {
		try {
			String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
			return accountService.inviLoginNext(view, logger, appCodeName);
		} catch (Exception e) {
			logger.error("login参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	/**
	 * 用户登录
	 * 
	 * @param ControllerView
	 *            data实体为user
	 * @return
	 */
	@RequestMapping(value = "/check/account/exist", method = RequestMethod.POST)
	public String checkAccountExit(@ModelAttribute ControllerView view) {
		try {
			return accountService.checkAccountExist(view, logger);
		} catch (Exception e) {
			logger.error("login参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	/**
	 * 访客机登录  返回项目列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/machinelogin", method = RequestMethod.POST)
	@ResponseBody
	public String getContents(HttpServletRequest request) {
		try {
			String data = request.getParameter("data");
			Map<String, String> parmap = BeanUtility.jsonStrToObject(data, Map.class, false);
			String name = parmap.get("name");
			String password = parmap.get("password");
			String mackAddress = parmap.get("mackAddress");
			return accountService.checMechinekUser(name,password,mackAddress);
		} catch (Exception e) {
			logger.error("login参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("请正确输入项目代码", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	/**
	 * 手持机登录  返回项目列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/handset/login", method = RequestMethod.POST)
	@ResponseBody
	public String handsetLogin(HttpServletRequest request,@ModelAttribute ControllerView view) {
		try {
			String data = request.getParameter("data");
			Map<String, String> parmap = BeanUtility.jsonStrToObject(data, Map.class, false);
			String name = parmap.get("name");
			String password = parmap.get("password");
			String mackAddress = parmap.get("mackAddress");
			return accountService.checMechinekUser(name,password,mackAddress);
		} catch (Exception e) {
			logger.error("login参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("请正确输入项目代码", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	
	/**
	 * ios升级兼容版本· 1.1.0 后不再使用
	 * @param view
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/version/ios", method = RequestMethod.POST)
	@Deprecated
	public String checkVersionIOS(@ModelAttribute ControllerView view) {
		String url ="itms-apps://itunes.apple.com/app/id1150851306?mt=8";
		try {
			Map<String, String> parmap = BeanUtility.jsonStrToObject(view.getData(), Map.class, false);
			String version = parmap.get("version");
			String appCode = parmap.get("appCode");
			logger.error("manage Apk: loginController: checkVersionIOS请求参数版本：version=" + version + "|appCode=" + appCode);
			if("IBB".equals(appCode)){
				String newVersion = "1.1.0";// 升级分界版本
				if (StringUtils.isBlank(version) || StringUtils.isBlank(appCode)) {
					return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, null);
				}
				ApkNoticeVo vo = new ApkNoticeVo();
				vo.setAppSystem("02");
				vo.setAppVersion(version);
				vo.setCode(appCode);
				ApkNoticeDto dto = apkNoticeUpgradeService.getHighestNoticeTypeTbApkNotice(vo);
				HashMap<String, String> ret = new HashMap<>();
				if(dto != null) {
					version = version.replace(".", "");
					newVersion = dto.getVersionName();
					newVersion = newVersion.replace(".", "");
					if (Integer.parseInt(version) >= Integer.parseInt(newVersion)) {
						return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
					}
					if ("IBB".equals(appCode)) {
						url ="https://itunes.apple.com/cn/app/%E7%88%B1%E5%8C%85%E5%8A%9E/id1150851301?mt=8&t=" + System.currentTimeMillis();
					}
					ret.put("versionName", dto.getVersionName());
					ret.put("url", url);
					ret.put("describe", dto.getNoticeContent());
				}
				return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(ret, true), IfConstant.SERVER_SUCCESS, "" + System.currentTimeMillis());
			} else {// 非IBB
				String newVersion = "1.0.8";
				if (StringUtils.isBlank(version) || StringUtils.isBlank(appCode)) {
					return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, null);
				}
				version = version.replace(".", "");
				newVersion = newVersion.replace(".", "");
				if (Integer.parseInt(version)>=Integer.parseInt(newVersion)) {
					return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
				}
				if ("IBB".equals(appCode)) {
					url ="itms-apps://itunes.apple.com/app/id1150851301?mt=8";
				}
				HashMap<String, String> ret = new HashMap<>();
				ret.put("versionName", newVersion);
				ret.put("url", url);
				ret.put("describe", "修改 页面 Bug ");
				return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(ret, true), IfConstant.SERVER_SUCCESS, "" + System.currentTimeMillis());
			}
		} catch (Exception e) {
			logger.error("检查版本报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}

	@RequestMapping(value = "/refresh/account", method = RequestMethod.POST)
	public String refreshAccount(HttpServletRequest request, @ModelAttribute ControllerView view) {
		try {
			String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
			return accountService.refreshAccount02(view, appCodeName);
		} catch (Exception e) {
			logger.error("刷新用户信息出错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	
	@RequestMapping(value = "/refresh/account02", method = RequestMethod.POST)
	public String refreshAccount02(HttpServletRequest request, @ModelAttribute ControllerView view) {
		try {
			String appCodeName = request.getAttribute(AppElementManagerInterceptor.globalAppNameParamKey).toString();
			return accountService.refreshAccount02(view, appCodeName);
		} catch (Exception e) {
			logger.error("刷新用户信息出错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}
	}
	
}