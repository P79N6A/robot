package com.bossbutler.util;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.bossbutler.common.AppHFiveManagerInterceptor;
import com.bossbutler.pojo.AppHFiveBackView;
import com.bossbutler.pojo.AppHFiveSystemInfoModel;
import com.company.util.BeanUtility;

/**
 * <p>
 * h5返回数据.
 * </p>
 * 
 * @author wq
 * 
 */
public class WriteToPageAppHFiveM {

	public static Logger logger = Logger.getLogger(WriteToPageAppHFiveM.class);

	/**
	 * 处理返回信息
	 * <p>
	 * 非成功报文不加密，成功报文加密.
	 * </p>
	 * 
	 * @param response
	 * @param resultData
	 * @param ifc
	 * @param token
	 * @return
	 */
	public static AppHFiveBackView setResponseMessage(HttpServletResponse response, Object resultDataObj,
			IfConstant ifc, String token) {
		AppHFiveBackView appHFiveBackView = new AppHFiveBackView();
		try {
			String dateTime = new Date().getTime() + "";
			response.setHeader(AppHFiveManagerInterceptor.transitHeaderKey, dateTime);
			appHFiveBackView.setCode(ifc.getCode());
			appHFiveBackView.setMsg(ifc.getMessage());
			
			AppHFiveSystemInfoModel appHFiveSystemInfoModel = new AppHFiveSystemInfoModel();
			appHFiveSystemInfoModel.setToken(AESUtils.aesEncrypt(token, dateTime));
			appHFiveSystemInfoModel.setData(resultDataObj);
			String message = BeanUtility.objectToJsonStr(appHFiveSystemInfoModel, true);
			switch (ifc) {
			case CUSTOM:
				appHFiveBackView.setMsg(resultDataObj+"");
				break;
			default:
				break;
			}

			appHFiveBackView.setData(HFiveBase64Util.encodeData(message));
		} catch (Exception e) {
			logger.error("接口平台构造返回的报文出错：" + e.getMessage(), e);
			appHFiveBackView.setCode("FFFFFFFFF");
			appHFiveBackView.setMsg("报文错误，请联系管理员！");
			appHFiveBackView.setData(StringUtils.EMPTY);
		}
		return appHFiveBackView;
	}

}
