package com.bossbutler.util;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.company.common.model.HttpConstant;
import com.company.exception.AppException;
import com.company.util.BeanUtility;

/* 
 * 利用HttpClient进行post请求的工具类 
 */
public class HttpClientUtil {
	/** 默认返回字符串格式 */
	private static final String CUSTOM_RESULT = "{\"code\":\"%s\",\"data\":\"%s\"}";

	/**
	 * 统一返回格式
	 * 
	 * @param data
	 *            返回报文
	 * @param hc
	 *            状态枚举HttpConstant
	 * @return
	 */
	private static String getResultData(String data, HttpConstant hc) {
		String result;
		try {
			switch (hc) {
			case SERVER_SUCCESS:
				Map<String, String> rs = new HashMap<String, String>();
				rs.put("code", hc.getCode());
				rs.put("data", data);
				result = BeanUtility.objectToJsonStr(rs, true);
				break;

			default:
				result = String.format(CUSTOM_RESULT, hc.getCode(), hc.getMessage());
				break;
			}
		} catch (AppException e) {
			e.printStackTrace();
			result = String.format(CUSTOM_RESULT, HttpConstant.NET_FAIL.getCode(), HttpConstant.NET_FAIL.getMessage());
		}
		return result;
	}

	public static String doPost(String url, Map<String, String> map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				BufferedInputStream buffer = new BufferedInputStream(response.getEntity().getContent());
				byte[] bytes = new byte[1024];
				int line = 0;
				StringBuilder builder = new StringBuilder();
				while ((line = buffer.read(bytes)) != -1) {
					builder.append(new String(bytes, 0, line, "UTF-8"));
				}
				
//				HttpEntity resEntity = response.getEntity();
//				if (resEntity != null) {
//					result = EntityUtils.toString(resEntity, charset);
//				}
				result = getResultData(builder.toString(), HttpConstant.SERVER_SUCCESS);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
