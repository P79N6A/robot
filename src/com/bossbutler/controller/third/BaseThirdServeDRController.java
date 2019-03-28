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
public class BaseThirdServeDRController extends BaseController {

	private static final String versionStr = "baoBanw";

	/**
	 * 发送加密，接收验签
	 */
	private static String ApublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDHL/2YOvGZq5dYBNFzfRQNWyJiOEMh0MTvzA3yMtZinpdflEv5+cBl5QthJbRZYaUR1pxTEQhwLSMsgWEAVCY9L6GoSYFmQ5YOycgnxYt29K7Gi9AwA8nrdEsaCis011jZ68dTJoux65u5tpET5ztABrv38wECPprbcC8X12KdQIDAQAB";

	/**
	 * 发送签名，接收解密
	 */
	private static String BprivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK2gZ13a+/fRYQhv7x9URtsI9W8aBhXtrsP1UFhc/gKo0aWGz4ma9bEA4x1T8mn1ZUQn6CuqvA5BCf2miPeJUNACYMed3zboRVQ19wrG4dC11/dIpX0rqT4OrEB50s0N1XJXNG13xI0JWPO0xvSffEDcrGwfLxxpfWW7JUsLpbxhAgMBAAECgYEAkE3hu2LWykhOMln9zvcXkNJoUne5mR4bg6hup5JaV1GCNOXPtdmB3CQVnvl3X9Nh3/ye/TQA6oPpztRLwBhzfZr2+ZyfsSKHMUGfkZ0B9+HtWi8n93ditexf1bQCBuDLPr7Kooyn9tZLqH6xsQXo4JMfEHeuDkY9fCO8EnL30RUCQQD6IRGsmcZAXJP/j9IyjWuL5xJ1GrOBmeBpBA3CYytGdGET4HxLnr7VDfeaIjwtrvoQ7lRDgXBFLnpStrXx5rhXAkEAsbOo5boQpkp2lGg6+nX+1AewHUTjObsn5zGgzbSfZGEj3XgsEz/xsmTn5IvlltLX+yvQlcO+VEIMpQ4k1VeeBwJBAJusASf+/JgKBkJD7Hpx8cE8qwwe9iFu92/kifu1ZLWQsOqCCFm4DuTisHtJW1LDV/c5nU/2Oz2TU6Yu+MX45jkCQHOBi0xfyEi+6BsJMSTyI8TghmpQB77T11kg2hw7xstxlOV1InkUvF8v1cep439I55jc3wUqKgIMuLlk2y5NeGsCQQC9/rEk3HxxcwpIhqGFKH5Mt1w0KgGIUxM7Ml+jhltyIKHH3c+sFxQtTCwyadnqO+PiinpCaItPQE0ArdLoTUU8";

	/**
	 * 创建第三方请求
	 * 
	 * @param dataJson
	 * @return
	 * @throws Exception
	 */
	protected Map<String, String> buildRequest(HttpServletRequest request, String dataJson) throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		String dateTime = Long.toString(new Date().getTime());
		String dataSource = dataJson + dateTime + versionStr;
		byte[] dataEnBts = RSAUtils.encryptByPublicKey(dataSource.getBytes("UTF-8"), ApublicKey);
		String dataEnStr = ByteUtility.bytesToHexString(dataEnBts);
		String mac = RSAUtils.sign(dataEnBts, BprivateKey);

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
	 * @param request
	 * @param data
	 * @param mac
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	protected String buildResponseData(HttpServletRequest request, String data, String mac, String dateTime)
			throws Exception {

		request.setAttribute("src_res_data", data);
		request.setAttribute("src_res_mac", mac);
		request.setAttribute("src_res_dateTime", dateTime);
		System.err.println("私钥解密");
		byte[] dataEncodedStrBts = RSAUtils.hexStringToByteArray(data);
		byte[] dataDecodedData = RSAUtils.decryptByPrivateKey(dataEncodedStrBts, BprivateKey);
		String dataDecodedDataStr = ByteUtility.bytesToHexString(dataDecodedData);
		System.out.println("解密data后数据: " + dataDecodedDataStr);

		String dataJsonSalt = new String(dataDecodedData, "UTF-8");
		System.out.println("解密后数据: " + dataJsonSalt);
		request.setAttribute("dec_res_data_salt", dataJsonSalt);
		String deDataJson = StringUtils.substringBeforeLast(dataJsonSalt, dateTime + versionStr);
		System.out.println("解密后数据: " + deDataJson);
		request.setAttribute("dec_res_data", deDataJson);

		System.err.println("公钥验证签名");
		boolean status = RSAUtils.verify(dataEncodedStrBts, ApublicKey, mac);
		System.err.println("验证结果:\r" + status);
		request.setAttribute("dec_mac_status", status);
		return deDataJson;
	}

}
