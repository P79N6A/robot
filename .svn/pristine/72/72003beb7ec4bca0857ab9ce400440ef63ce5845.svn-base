package com.bossbutler.controller.third;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bossbutler.common.InitConf;
import com.company.common.model.HttpConstant;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.DateUtil;
import com.company.util.HttpUtil;

/**
 * <p>
 * 第三方接口模拟.
 * </p>
 *
 * @author wq
 * 
 */
@RestController
@RequestMapping("third/dr/")
public class DRController extends DRBaseController {

	private static Logger logger = Logger.getLogger(DRController.class);

	/**
	 * 跳转至相应页面
	 *
	 * @param
	 * @param param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "receive", method = RequestMethod.POST)
	public Map<String, String> nsPathPage(@RequestParam Map<String, String> param, HttpServletRequest request)
			throws Exception {
		String data = param.get("data");
		String mac = param.get("mac");
		String dateTime = param.get("dateTime");
		String dataJson = buildResponseData(request, data, mac, dateTime);
		System.out.println("dataJson:" + dataJson);

		Map<String, Object> returnMap = new HashMap<>();
		Map<String, Object> business = new HashMap<>();
		returnMap.put("result", 1);
		returnMap.put("info", "bbwOK");
		returnMap.put("business", business);
		business.put("bizUrl", "http://www.testurl.com");
		business.put("bizID", "123456");
		business.put("bizToken", CommonUtil.MD5("123456"));
		return buildRequest(request, BeanUtility.objectToJsonStr(returnMap, false));
	}

	/**
	 * 跳转至相应页面
	 *
	 * @param
	 * @param param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "query/detail/transit", method = RequestMethod.POST)
	public ModelAndView queryDetailTransit(@RequestParam Map<String, String> param, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		request.setAttribute("title", "点融应用请求包办服务·Get统计信息");
		request.setAttribute("sendTitle", "发送到包办服务请求数据解析");
		request.setAttribute("recTitle", "接收包办服务响应数据解析");

		String businessCode = request.getParameter("businessCode");
		String pageNum = request.getParameter("pageNum");
		String accountID = request.getParameter("accountID");
		String projectID = request.getParameter("projectID");
		String beginDate = request.getParameter("beginDate");
		Map<String, Object> returnMap = new HashMap<>();
		Map<String, Object> condition = new HashMap<>();
		returnMap.put("businessCode", businessCode);
		returnMap.put("pageNum", Integer.valueOf(pageNum));
		returnMap.put("condition", condition);
		condition.put("accountID", accountID);
		condition.put("projectID", projectID);
		condition.put("beginDate", beginDate);
		condition.put("endDate", DateUtil.getCurrentDate());

		Map<String, String> paramMap = buildRequest(request, BeanUtility.objectToJsonStr(returnMap, false));
		logger.debug("send message is :" + paramMap);
		String getDRUrl = InitConf.getSelfhost() + "/third/serve/auth/query/statistics/inf/6ebd9acf58e84517896ba64b308a90f3";
		String responseData = HttpUtil.get(getDRUrl, paramMap);

		// 解析post响应数据
		Map<String, String> resultDataMap = BeanUtility.jsonStrToObject(responseData, Map.class, false);
		String code = resultDataMap.get("code");
		if (!HttpConstant.SERVER_SUCCESS.getCode().equals(code)) {
			mv.setViewName("/error");
			return mv;
		}
		String resultData = resultDataMap.get("data");
		request.setAttribute("responseData", resultData);
		Map<String, String> resultMap = BeanUtility.jsonStrToObject(resultData, Map.class, false);
		buildResponseData(request, resultMap.get("data"), resultMap.get("mac"), resultMap.get("dateTime"));
		mv.setViewName("/showRM");
		return mv;
	}

}
