package com.bossbutler.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.company.mobile.model.AppCodeNameEnum;

/**
 * <p>
 * 拦截器app请求检验;拦截拥有bbw_application_name参数信息的请求.
 * 在每个app前端向后台请求时的请求参数中增加一个k-v格式的属性： bbw_application_name:IBB/ARPM/SHZX/SJSM（无则默认为IBB）
 * 用于后台判断app应用的来源
 * </p>
 * 
 * @author wq
 *
 */
public class AppElementManagerInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(AppElementManagerInterceptor.class);

	private static final String appNameParamKey = "bbw_application_name";

	public static final String globalAppNameParamKey = "globalAppNameParamVal";

	/**
	 * <p>
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion().
	 * </p>
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String appNameParamVal = request.getParameter(appNameParamKey);

		logger.info("app应用头信息获得应用名称:uri:" + request.getRequestURI() + "|appNameParamVal:" + appNameParamVal);

		request.setAttribute(globalAppNameParamKey, AppCodeNameEnum.getAdapterCodeName(appNameParamVal));
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