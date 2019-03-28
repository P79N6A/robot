package com.bossbutler.common;

import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT");
		filterChain.doFilter(servletRequest, servletResponse);
		WriteToPage.setResponseMessage(null, IfConstant.SERVER_SUCCESS);
	}

	@Override
	public void destroy() {
	}

}
