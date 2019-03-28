package com.bossbutler.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>
 * 保留过滤器，用于tomcat等 启动之前执行部署初始化.
 * </p>
 * 
 * @author wangqiao
 *
 */
public class InitFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// 这里我们可以做一些项目部署前的工作，比如项目目录的创建，其他相关服务的启动。。。
	}

}
