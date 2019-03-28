package com.bossbutler.util;

import java.util.HashMap;
import java.util.Map;

//对接口进行测试  
public class TestMain {

	private String url = "https://ltdev.uranome.com/gw/api/third/baoban/userinfo/receiver/";
	private String charset = "utf-8";
	private HttpClientUtil httpClientUtil = null;

	public TestMain() {
		httpClientUtil = new HttpClientUtil();
	}

	public void test() {
		String httpOrgCreateTest = url + "httpOrg/create";
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("authuser", "*****");
		createMap.put("authpass", "*****");
		createMap.put("orgkey", "****");
		createMap.put("orgname", "****");
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest, createMap, "utf-8");
		System.out.println("result:" + httpOrgCreateTestRtn);
	}

	public static void main(String[] args) {
		TestMain main = new TestMain();
		main.test();
	}
}