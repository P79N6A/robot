package com.bossbutler.controller.third;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bossbutler.pojo.third.ThirdDRProject;
import com.bossbutler.pojo.third.ThirdDRUserInf;
import com.bossbutler.service.ThirdAccountService;
import com.bossbutler.service.ThirdTransitService;
import com.bossbutler.util.HttpClientUtil;
import com.company.common.model.HttpConstant;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;
import com.company.util.HttpUtil;

/**
 * 第三方对接接口（点融）
 */
@RestController
@RequestMapping("third/serve/auth/")
public class ThirdServeController extends BaseThirdServeDRController {

	private static Logger logger = Logger.getLogger(ThirdServeController.class);

	/**
	 * 商户授权
	 * 
	 * @param request
	 * @param pathCode
	 *            商户号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/send/message/{serveCode}", method = RequestMethod.POST)
	public ModelAndView serveAuth(HttpServletRequest request, @PathVariable("serveCode") String serveCode) {
		ModelAndView mv = new ModelAndView();
		request.setAttribute("title", "包办移动端 授权并Post用户信息到点融应用");
		request.setAttribute("sendTitle", "发送到点融服务请求数据解析");
		request.setAttribute("recTitle", "接收点融服务响应数据解析");
		try {
			if (!EMerchants.DIAN_RONG.getCode().equals(serveCode)) {
				mv.setViewName("/error");
				return mv;
			}

			String accountId = request.getParameter("accountId");
			String isAuth = request.getParameter("isAuth");
			String postDRUrl = request.getParameter("reUrl");

			// post用户数据到点融服务接口
			String responseData = postDrUserData(request, accountId, postDRUrl, isAuth);

			// 解析post响应数据
			Map<String, String> resultDataMap = BeanUtility.jsonStrToObject(responseData, Map.class, false);
			String code = resultDataMap.get("code");
			if (!HttpConstant.SERVER_SUCCESS.getCode().equals(code)) {
				mv.setViewName("/error");
				return mv;
			}
			String resultData = resultDataMap.get("data");
			request.setAttribute("responseData", resultData);
			Map<String, Object> resultMap = BeanUtility.jsonStrToObject(resultData, Map.class, false);
			buildResponseData(request, resultMap.get("data").toString(), resultMap.get("mac").toString(), resultMap.get("dateTime").toString());
			mv.setViewName("/showRM");
		} catch (Exception e) {
			logger.error("通行日志获取:" + e.getMessage(), e);
			mv.setViewName("/error");
		}
		return mv;
	}

	/**
	 * Get统计信息
	 * 
	 * @param request
	 * @param serveCode
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/query/statistics/inf/{serveCode}", method = RequestMethod.GET)
	public Map<String, String> statisticsPageRow(HttpServletRequest request,
			@PathVariable("serveCode") String serveCode) {
		try {
			if (!EMerchants.DIAN_RONG.getCode().equals(serveCode)) {
				Map<String, String> returnMap = new HashMap<>();
				returnMap.put("result", "0");
				returnMap.put("info", "服务器执行失败请联系管理员！");
				return returnMap;
			}

			String data = request.getParameter("data");
			String mac = request.getParameter("mac");
			String dateTime = request.getParameter("dateTime");
			System.out.println("data" + data);
			System.out.println("mac" + mac);
			System.out.println("dateTime" + dateTime);
			String responseJsonData = buildResponseData(request, data, mac, dateTime);
			Map<String, Object> resultMap = BeanUtility.jsonStrToObject(responseJsonData, Map.class, false);
			System.out.println("responseJsonData:" + responseJsonData);
			System.out.println("resultMap:" + resultMap);

			String businessCode = (String) resultMap.get("businessCode");
			System.out.println("businessCode:" + businessCode);

			String pageNum = Integer.toString((Integer) resultMap.get("pageNum"));
			System.out.println("pageNum:" + pageNum);

			Map<String, Object> condition = (Map) resultMap.get("condition");
			System.out.println("condition:" + condition);

			String dataJson = getDRPageRowData(1, "bbwOK", responseJsonData);
			return buildRequest(request, dataJson);
		} catch (Exception e) {
			logger.error("通行日志获取:" + e.getMessage(), e);
			Map<String, String> returnMap = new HashMap<>();
			returnMap.put("result", "2");
			returnMap.put("info", "服务执行异常请联系管理员！");
			return returnMap;
		}
	}

	/**
	 * Post用户信息·实例
	 * 
	 * @param request
	 * @param accountId
	 * @param postDRUrl
	 * @param isAuth
	 * @return
	 */
	private String postDrUserData(HttpServletRequest request, String accountId, String postDRUrl, String isAuth) {
		String result = "{}";

		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("accountId", accountId);
			dataMap.put("authFlag", isAuth);
			ThirdDRUserInf userInf = new ThirdDRUserInf();
			dataMap.put("userInfo", userInf);
			if ("1".equals(isAuth)) {
				userInf.setAccountCode(10200050);
				userInf.setAccountMobile("151121212456");
				userInf.setAccountName("杨潇");
				userInf.setAccountGender("男");
				userInf.setRegisterDate(DateUtil.getCurrentDate());
				List<ThirdDRProject> projectList = new ArrayList<ThirdDRProject>();
				userInf.setProjectList(projectList);
				ThirdDRProject p1 = new ThirdDRProject();
				p1.setOrgID(795220493593615001L);
				p1.setOrgName("五行旗特种作战旅");
				p1.setProjectAddr("安徽省黄山光明顶");
				p1.setProjectID(846745686094778358L);
				p1.setProjectName("明教");
				p1.setIsAdmin(1);

				ThirdDRProject p2 = new ThirdDRProject();
				p2.setOrgID(795220493593615021L);
				p2.setOrgName("武当对外交流办事处");
				p2.setProjectAddr("湖北省十堰市武当山");
				p2.setProjectID(846745686094778368L);
				p2.setProjectName("武当");
				p2.setIsAdmin(0);

				projectList.add(p1);
				projectList.add(p2);
			}
			String dataJson = BeanUtility.objectToJsonStr(dataMap, true);
			Map<String, String> paramMap = buildRequest(request, dataJson);
			logger.debug("send message is :" + paramMap);
			if (postDRUrl.startsWith("https")) {
				result = HttpClientUtil.doPost(postDRUrl, paramMap, "utf-8");
			} else {
				result = HttpUtil.post(postDRUrl, paramMap);
			}
		} catch (Exception e) {
			logger.error("发送点融消息异常:" + e.getMessage(), e);
		}
		System.out.println("==============" + result);
		return result;
	}

	// @Autowired
	private ThirdAccountService thirdAccountService;

	// @Autowired
	private ThirdTransitService thirdTransitService;

	/**
	 * Post用户信息·实例
	 * 
	 * @param request
	 * @param accountId
	 * @param postDRUrl
	 * @param isAuth
	 * @return
	 */
	private String postDrUserDataDB(HttpServletRequest request, String accountId, String postDRUrl, String isAuth) {
		String result = "{}";
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("accountId", accountId);
			dataMap.put("authFlag", isAuth);

			ThirdDRUserInf userInf = null;
			if ("1".equals(isAuth)) {
				userInf = thirdAccountService.queryUserForThird(accountId, logger);
			} else {
				userInf = new ThirdDRUserInf();
			}
			dataMap.put("userInfo", userInf);

			String dataJson = BeanUtility.objectToJsonStr(dataMap, true);
			Map<String, String> paramMap = buildRequest(request, dataJson);
			logger.debug("send message is :" + paramMap);
			if (postDRUrl.startsWith("https")) {
				result = HttpClientUtil.doPost(postDRUrl, paramMap, "utf-8");
			} else {
				result = HttpUtil.post(postDRUrl, paramMap);
			}
		} catch (Exception e) {
			logger.error("发送点融消息异常:" + e.getMessage(), e);
		}
		logger.debug("==============result:" + result);
		return result;
	}

	/**
	 * Get统计信息·实例
	 * 
	 * @param resultCode
	 * @param info
	 * @return
	 * @throws Exception
	 */
	private String getDRPageRowData(int resultCode, String info, String responseJsonData) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		Map<String, Object> page = new HashMap<>();
		List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
		returnMap.put("result", resultCode);
		returnMap.put("info", info);
		returnMap.put("page", page);
		returnMap.put("row", row);
		page.put("currentPageNum", 1);
		page.put("pageSize", 10);
		page.put("total", 2);
		page.put("totalPageNum", 1);
		Map<String, Object> c1 = new HashMap<>();
		row.add(c1);
		Map<String, Object> c2 = new HashMap<>();
		row.add(c2);
		c1.put("transitID", "805938866119905280");
		c1.put("transitTime", "2017-10-01 09:00:01");
		c1.put("transitType", "00");
		c1.put("arrangeName", "大堂闸机");
		c1.put("entranceType", "01");

		c2.put("transitID", "805938866119905281");
		c2.put("transitTime", "2017-10-01 09:02:01");
		c2.put("transitType", "00");
		c2.put("arrangeName", "一层门禁");
		c2.put("entranceType", "01");

		return BeanUtility.objectToJsonStr(returnMap, false);
	}

	/**
	 * Get统计信息·实例
	 * 
	 * @param resultCode
	 * @param info
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String getDRPageRowDataDB(int resultCode, String info, String responseJsonData) throws Exception {

		Map<String, Object> resultMap = BeanUtility.jsonStrToObject(responseJsonData, Map.class, false);
		String businessCode = (String) resultMap.get("businessCode");
		System.out.println("businessCode:" + businessCode);

		String pageNum = Integer.toString((Integer) resultMap.get("pageNum"));
		System.out.println("pageNum:" + pageNum);

		Map<String, Object> condition = (Map) resultMap.get("condition");
		System.out.println("condition:" + condition);

		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("result", resultCode);
		returnMap.put("info", info);
		thirdTransitService.queryTransitLogForThird(pageNum, condition, returnMap, logger);
		return BeanUtility.objectToJsonStr(returnMap, false);
	}

}
