package com.bossbutler.common;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bossbutler.util.AESUtils;
import com.bossbutler.util.HFiveBase64Util;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.RedisConstant;
import com.bossbutler.util.WriteToPage;
import com.company.common.redis.active.CacheManager;
import com.company.util.BeanUtility;

/**
 * <p>
 * 拦截器H5页面请求检验;拦截拥有token的请求.
 * </p>
 * 
 * @author wq
 *
 */
public class AppHFiveManagerInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(AppHFiveManagerInterceptor.class);

	public static final String transitHeaderKey = "TokenP";

	/**
	 * <p>
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion().
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		/** 获取contextPath */
		String ctxPath = request.getContextPath();

		/** 获取uri */
		String uri = request.getRequestURI();

		/** 获取请求动作标识 */
		String actionName = uri.replaceFirst(ctxPath + "/", "");

		/** 验证tk，解密策略 */
		String params = request.getParameter("data");
		String headerKey = request.getHeader(transitHeaderKey);

		if (StringUtils.isBlank(params) || StringUtils.isBlank(headerKey)) {
			logger.warn("平台拦截器验证参数不合格:" + actionName);
			WriteToPage.writeToPage(response, null, IfConstant.PARA_FAIL, null);
			return false;
		}
		String dataJson = HFiveBase64Util.decodeData(params);
		if(StringUtils.isBlank(dataJson)) {
			logger.warn("平台拦截器验证参数不合格:" + actionName);
			WriteToPage.writeToPage(response, null, IfConstant.PARA_FAIL, null);
			return false;
		}
		Map<String, Object> dataMap = BeanUtility.jsonStrToObject(dataJson, Map.class, false);
		Object tkObj = dataMap.get("token");
		if(tkObj == null) {
			logger.warn("平台拦截器验证参数不合格:" + actionName);
			WriteToPage.writeToPage(response, null, IfConstant.PARA_FAIL, null);
			return false;
		}
		String tkEn = (String) tkObj;
		String tk = AESUtils.aesDecrypt(tkEn, headerKey);
		boolean hexists = CacheManager.hexists(RedisConstant.REDIS_TABLE_TOKEN_KEY, tk);
		if (!hexists) {
			logger.warn("平台拦截器验证tk不存在:" + actionName);
			WriteToPage.writeToPage(response, null, IfConstant.PARA_FAIL, null);
			return false;
		}
		Set<String> setKey = dataMap.keySet();
		for(String key : setKey) {
			System.out.println("key======" + key);
			System.out.println("value======" + dataMap.get(key));
			request.setAttribute(key, dataMap.get(key));
		}
		request.setAttribute("token", tk);
		return true;
	}

	/**
	 * <p>
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间.
	 * </p>
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * <p>
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等.
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion().
	 * </p>
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}