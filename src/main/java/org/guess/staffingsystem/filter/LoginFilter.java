package org.guess.staffingsystem.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		String pathInfo = req.getPathInfo();
		if (pathInfo != null && "" != pathInfo) {
			if (pathInfo.matches("/staff/loginIdIsExist.*")) {
				chain.doFilter(req, resp);
				return;
			}
		}

		resp.sendRedirect(req.getContextPath() + "/login.html");

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
