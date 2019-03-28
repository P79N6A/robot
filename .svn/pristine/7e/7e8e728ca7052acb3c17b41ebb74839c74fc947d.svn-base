package com.bossbutler.util;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.bossbutler.pojo.BackView;
import com.bossbutler.pojo.SystemInfoModel;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;

/**
 * <p>
 * 返回数据.
 * </p>
 * 
 * @author wq
 * 
 */
public class WriteToPage {

	public static Logger logger = Logger.getLogger(WriteToPage.class);

	/** 默认返回字符串格式 */
	private static final String CUSTOM_RESULT = "{\"code\":\"%s\",\"data\":\"%s\",\"message\":\"%s\",\"systemInfo\":{\"mac\":\"\",\"dateTime\":\"\"}}";
	
	/**
	 * 未封装形式写出json
	 * <p>
	 * 非成功报文不加密，成功报文加密.
	 * </p>
	 * 
	 * @param response
	 * @param resultData
	 * @param ifc
	 * @param dateTime
	 *            时间戳
	 */
	public static void writeToPage(HttpServletResponse response, String resultData, IfConstant ifc, String dateTime) {
		if (StringUtils.isBlank(dateTime)) {
			dateTime = String.valueOf(new Date().getTime());
		}

		String message = StringUtils.EMPTY;
		PrintWriter out = null;
		try {
			// 处理返回信息
			message = setResponseMessage(resultData, ifc, dateTime);
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
		} catch (Exception e) {
			logger.error("接口平台返回的报文出错：" + e.getMessage(), e);
		} finally {
			logger.info("接口平台返回的报文为：" + message);
			if (out != null) {
				out.print(message);
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 处理返回信息
	 * <p>
	 * 非成功报文不加密，成功报文加密.
	 * </p>
	 * 
	 * @param resultData
	 * @param ifc
	 * @param dateTime
	 *            时间戳
	 * @return
	 * @throws Exception
	 */
	public static String setResponseMessage(String resultData, IfConstant ifc, String dateTime) {
		String message;
		try {

			String respCode = ifc.getCode();
			BackView backView = new BackView();
			SystemInfoModel systemInfo = new SystemInfoModel();
			switch (ifc) {
			case SERVER_SUCCESS:
				backView.setCode(respCode);
				backView.setData(resultData);
				backView.setMessage(ifc.getMessage());
				systemInfo.setDateTime(dateTime);
				systemInfo.setMac(MacConfirm.getMacForMobile(resultData, dateTime));
				backView.setSystemInfo(systemInfo);
				break;
			case CUSTOM:
				backView.setCode(respCode);
				backView.setData(CommonUtil.Object2String(resultData));
				backView.setMessage(CommonUtil.Object2String(resultData));
				systemInfo.setDateTime(dateTime);
				systemInfo.setMac(StringUtils.EMPTY);
				backView.setSystemInfo(systemInfo);
				break;
			default:
				backView.setCode(respCode);
				backView.setData(resultData);
				backView.setMessage(ifc.getMessage());
				systemInfo.setDateTime(dateTime);
				systemInfo.setMac(StringUtils.EMPTY);
				backView.setSystemInfo(systemInfo);
				break;
			}

			message = BeanUtility.objectToJsonStr(backView, true);
		} catch (Exception e) {
			logger.error("接口平台构造返回的报文出错：" + e.getMessage(), e);
			message = String.format(CUSTOM_RESULT, IfConstant.UNKNOWN_ERROR.getCode(), StringUtils.EMPTY,
					IfConstant.UNKNOWN_ERROR.getMessage());
		}
		return message;
	}
	
	/**
	 * 处理返回信息
	 * <p>
	 * 非成功报文不加密，成功报文加密.
	 * </p>
	 * 
	 * @param resultData
	 * @param ifc
	 * @param dateTime
	 *            时间戳
	 * @return
	 * @throws AppException 
	 * @throws Exception
	 */
	public static String setResponseMessage(Object resultData,String respCode,String message) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map.put("code", respCode);
			map.put("message",message);
			map.put("data", resultData);
			message = BeanUtility.objectToJsonStr(map, true);
		} catch (Exception e) {
			logger.error("接口平台构造返回的报文出错：" + e.getMessage(), e);
			map.put("code", IfConstant.UNKNOWN_ERROR.getCode());
			map.put("message",IfConstant.UNKNOWN_ERROR.getMessage());
			message = String.format(CUSTOM_RESULT, IfConstant.UNKNOWN_ERROR.getCode(),StringUtils.EMPTY,
					IfConstant.UNKNOWN_ERROR.getMessage());
		}
		
		return message;
	}

	public static String setResponseMessage(Object resultData, IfConstant ifc) {
		String message;
		try {
			String dateTime = String.valueOf(new Date().getTime());
			SystemInfoModel systemInfo = new SystemInfoModel();
			String respCode = ifc.getCode();
			BackView backView = new BackView();
			switch (ifc) {
			case SERVER_SUCCESS:
				String data;
				if (!(resultData instanceof String)) {
					data = BeanUtility.objectToJsonStr(resultData, true);
				} else {
					data = (String) resultData;
				}
				backView.setCode(respCode);
				backView.setData(data);
				backView.setMessage(ifc.getMessage());
				systemInfo.setDateTime(dateTime);
				systemInfo.setMac(MacConfirm.getMacForMobile(data, dateTime));
				backView.setSystemInfo(systemInfo);
				break;
			case CUSTOM:
				backView.setCode(respCode);
				backView.setData(CommonUtil.Object2String(resultData));
				backView.setMessage(CommonUtil.Object2String(resultData));
				break;
			default:
				backView.setCode(respCode);
				backView.setData(StringUtils.EMPTY);
				backView.setMessage(ifc.getMessage());
				systemInfo.setDateTime(dateTime);
				systemInfo.setMac(StringUtils.EMPTY);
				backView.setSystemInfo(systemInfo);
				break;
			}

			message = BeanUtility.objectToJsonStr(backView, true);
		} catch (Exception e) {
			logger.error("接口平台构造返回的报文出错：" + e.getMessage(), e);
			message = String.format(CUSTOM_RESULT, IfConstant.UNKNOWN_ERROR.getCode(), StringUtils.EMPTY,
					IfConstant.UNKNOWN_ERROR.getMessage());
		}
		return message;
	}
	
	public static String setResponseMessage(Object resultData, IfConstant ifc, boolean isNullToEmpty) {
		String message;
		try {
			String dateTime = String.valueOf(new Date().getTime());
			SystemInfoModel systemInfo = new SystemInfoModel();
			String respCode = ifc.getCode();
			BackView backView = new BackView();
			switch (ifc) {
			case SERVER_SUCCESS:
				String data;
				if (!(resultData instanceof String)) {
					data = BeanUtility.objectToJsonStr(resultData, isNullToEmpty);
				} else {
					data = (String) resultData;
				}
				backView.setCode(respCode);
				backView.setData(data);
				backView.setMessage(ifc.getMessage());
				systemInfo.setDateTime(dateTime);
				systemInfo.setMac(MacConfirm.getMacForMobile(data, dateTime));
				backView.setSystemInfo(systemInfo);
				break;
				
			default:
				backView.setCode(respCode);
				backView.setData(StringUtils.EMPTY);
				backView.setMessage(ifc.getMessage());
				systemInfo.setDateTime(dateTime);
				systemInfo.setMac(StringUtils.EMPTY);
				backView.setSystemInfo(systemInfo);
				break;
			}
			
			message = BeanUtility.objectToJsonStr(backView, isNullToEmpty);
		} catch (Exception e) {
			logger.error("接口平台构造返回的报文出错：" + e.getMessage(), e);
			message = String.format(CUSTOM_RESULT, IfConstant.UNKNOWN_ERROR.getCode(), StringUtils.EMPTY,
					IfConstant.UNKNOWN_ERROR.getMessage());
		}
		return message;
	}

	/**
	 * 处理返回信息
	 * <p>
	 * 非成功报文不加密，成功报文加密.
	 * </p>
	 * 
	 * @param resultData
	 * @param ifc
	 * @return
	 * @throws Exception
	 */
	public static String setResponseMessageForError(String message, String recode) {
		try {
			String dateTime = String.valueOf(System.currentTimeMillis());
			BackView backView = new BackView();
			SystemInfoModel systemInfo = new SystemInfoModel();
			backView.setCode(recode);
			backView.setData(StringUtils.EMPTY);
			backView.setMessage(message);
			systemInfo.setDateTime(dateTime);
			systemInfo.setMac(MacConfirm.getMacForMobile(null, dateTime));
			backView.setSystemInfo(systemInfo);
			message = BeanUtility.objectToJsonStr(backView, true);
		} catch (Exception e) {
			logger.error("接口平台构造返回的报文出错：" + e.getMessage(), e);
			message = String.format(CUSTOM_RESULT, IfConstant.UNKNOWN_ERROR.getCode(), StringUtils.EMPTY,
					IfConstant.UNKNOWN_ERROR.getMessage());
		}
		return message;
	}

}
