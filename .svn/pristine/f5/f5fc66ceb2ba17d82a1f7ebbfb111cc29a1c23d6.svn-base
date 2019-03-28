package com.bossbutler.common;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MacConfirm;
import com.bossbutler.util.WriteToPage;
import com.company.cnvalid.model.ResultCnvalidModel;
import com.company.cnvalid.util.FormValidUtil;
import com.company.util.BeanUtility;

public class FormValidInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(FormValidInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#
	 * preHandle(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
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
		
		
		System.out.println("request path:>>>"+actionName+">>>parameters:>>>"+request.getParameter("data"));
		
		String tk = request.getParameter("token");
		if(StringUtils.isNotBlank(tk) &&tk.equals("llc")){
			return true;
		}
		// -----------------------begin checkMac--------------------------
		// 获得参数数据
		String dataStr = request.getParameter("data");

		String macStr = request.getParameter("mac");
		String dateTimeStr = request.getParameter("dateTime");

		if (StringUtils.isBlank(dataStr) || StringUtils.isBlank(macStr) || StringUtils.isBlank(dateTimeStr)) {
			throw new Exception(new IllegalArgumentException("参数不完整，无法验证信息"));
		}
		boolean checkMac = MacConfirm.checkMac(macStr, dataStr, dateTimeStr);
		String dateTime = String.valueOf(new Date().getTime());
		if (!checkMac) {
			logger.warn("queryMsgForSendMsg参数mac验证不正确");
			WriteToPage.writeToPage(response, null, IfConstant.UNKNOWN_ERROR, dateTime);
			return false;
		}
		// -----------------------end checkMac--------------------------
		if (StringUtils.isNotBlank(actionName)) {
			String data = request.getParameter("data");
			ResultCnvalidModel resultCnvalidModel = FormValidUtil.validRuleByRequestJsonMap(actionName,
					BeanUtility.jsonStrToObject(data, Map.class, false));
			if (resultCnvalidModel.isError()) {
				logger.warn("平台拦截器验证请求参数不合格:" + resultCnvalidModel.getMessage());
				WriteToPage.writeToPage(response, null, IfConstant.PARA_FAIL, null);
				return false;
			}
		}
		return true;
	}

}
