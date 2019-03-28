package com.bossbutler.controller.third;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.bossbutler.controller.BaseController;
import com.bossbutler.util.RSAUtils;
import com.common.cnnetty.gateway.util.ByteUtility;

/**
 * 第三方对接接口（点融）
 *
 */
public class DRBaseController extends BaseController {

	private static final String versionStr = "baoBanw";

	/**
	 * 接收解密，发送签名
	 */
	private String AprivateKey = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMMcv/Zg68Zmrl1gE0XN9FA1bImI4QyHQxO/MDfIy1mKel1+US/n5wGXlC2EltFlhpRHWnFMRCHAtIyyBYQBUJj0voahJgWZDlg7JyCfFi3b0rsaL0DADyet0SxoKKzTXWNnrx1Mmi7Hrm7m2kRPnO0AGu/fzAQI+mttwLxfXYp1AgMBAAECgYEAjXMSV/P7+mIGlvtH/ZtKO7v/a9YoEYUeQ/qfYlHX2ppiC9W/1hMxjh3t9vHgxP3tiyIPl4PLAQ1L15RYG1M4seY7a9YEZENc06R6ri7u2I1Ez5vbLXeZnTZv+DnirwXNDBgtJg5ebecO0pIJ4LoRuKVmSjzsXYJIpt8NPeoReCECQQDutzQ8Y21AfCyTthYVIgOySGIWSblEGHRaK8w3xprD0rADyH8d27QzoufLycFCkCL9/UEh5tZFpHb7I92IlAGLAkEA0T1SNqL8pwQaJX1Ew9EJd3H3mDSendlc/ML++jpHozJ/drsic9wyvGirLgy/NEMhNtbG6AQod+l/KS7XCm4j/wJBAKeH96bx+HirLPW1cqX+Ky3x0rTlpJn/t5iY6Ee0oGUR9emRynR//mXGzHqe63z1fzlVhWJviK1zkPUL5Ku3g6UCQQCXQp45UzAUSPnh2iIo8OfICoJz7K4x7F21d/29xP3es3XDzlqEqU2gOIZvHtXmoRTUwQU/2ymqLgk4K11Xc/HfAkEA0EZpu/PXsQzAfSGnQrLbhaX038MoqxVSV9jDoFju5qFcMBaTjCkA2c9fJ/cbiEQxRs+1U2mJglyuoCPTH7+mwA==";

	/**
	 * 接收验签，发送加密
	 */
	private String BpublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtoGdd2vv30WEIb+8fVEbbCPVvGgYV7a7D9VBYXP4CqNGlhs+JmvWxAOMdU/Jp9WVEJ+grqrwOQQn9poj3iVDQAmDHnd826EVUNfcKxuHQtdf3SKV9K6k+DqxAedLNDdVyVzRtd8SNCVjztMb0n3xA3KxsHy8caX1luyVLC6W8YQIDAQAB";

	/**
	 * 创建第三方请求
	 * 
	 * @param request
	 * @param dataJson
	 * @return
	 * @throws Exception
	 */
	protected Map<String, String> buildRequest(HttpServletRequest request, String dataJson) throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		String dateTime = Long.toString(new Date().getTime());
		String dataSource = dataJson + dateTime + versionStr;
		byte[] dataEnBts = RSAUtils.encryptByPublicKey(dataSource.getBytes("UTF-8"), BpublicKey);
		String dataEnStr = ByteUtility.bytesToHexString(dataEnBts);
		String mac = RSAUtils.sign(dataEnBts, AprivateKey);

		paramMap.put("dateTime", dateTime);
		paramMap.put("mac", mac);
		paramMap.put("data", dataEnStr);

		request.setAttribute("src_data", dataJson);
		request.setAttribute("src_data_salt", dataSource);
		request.setAttribute("src_dateTime", dateTime);
		request.setAttribute("src_mac", mac);
		request.setAttribute("enc_data", dataEnStr);
		request.setAttribute("requestData", paramMap);
		return paramMap;
	}

	/**
	 * 解析第三方响应
	 * 
	 * @param data
	 * @param mac
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	protected String buildResponseData(HttpServletRequest request, String data, String mac, String dateTime) throws Exception {
		request.setAttribute("src_res_data", data);
		request.setAttribute("src_res_mac", mac);
		request.setAttribute("src_res_dateTime", dateTime);
		System.err.println("私钥解密");
		byte[] dataEncodedStrBts = RSAUtils.hexStringToByteArray(data);
		byte[] dataDecodedData = RSAUtils.decryptByPrivateKey(dataEncodedStrBts, AprivateKey);
		String dataDecodedDataStr = ByteUtility.bytesToHexString(dataDecodedData);
		System.out.println("解密data后数据: " + dataDecodedDataStr);

		String dataJsonSalt = new String(dataDecodedData, "UTF-8");
		System.out.println("解密后数据: " + dataJsonSalt);
		request.setAttribute("dec_res_data_salt", dataJsonSalt);
		String deDataJson = StringUtils.substringBeforeLast(dataJsonSalt, dateTime + versionStr);
		System.out.println("解密后数据: " + deDataJson);
		request.setAttribute("dec_res_data", deDataJson);

		System.err.println("公钥验证签名");
		boolean status = RSAUtils.verify(dataEncodedStrBts, BpublicKey, mac);
		System.err.println("验证结果:\r" + status);
		request.setAttribute("dec_mac_status", status);
		return deDataJson;
	}

}
