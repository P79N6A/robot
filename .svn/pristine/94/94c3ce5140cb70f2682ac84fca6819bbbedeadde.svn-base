package com.bossbutler.common;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * restTemplate拦截器，添加头信息token
 * 
 * @author wangqiao
 */
public class RestTrackInterceptor implements ClientHttpRequestInterceptor {

	private Logger logger = Logger.getLogger(RestTrackInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		HttpHeaders headers = request.getHeaders();

		// 加入自定义字段
		headers.add("cntoken", InitConf.getMsgMqpmHostRestTransitToken());
		logger.info("已经添加了头信息header-cntoken" + InitConf.getMsgMqpmHostRestTransitToken());
		// 保证请求继续被执行
		return execution.execute(request, body);
	}
}
