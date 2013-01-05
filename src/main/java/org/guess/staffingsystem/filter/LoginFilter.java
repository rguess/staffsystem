package org.guess.staffingsystem.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.guess.staffingsystem.util.CookieUtil;

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
			if (pathInfo.matches("/staff/loginIdIsExist.*")
					|| pathInfo.matches("/staff/getUserBySession.*")) {
				chain.doFilter(req, resp);
				return;
			}
		}

		HttpSession session = req.getSession(true);
		String LoginId = (String) session.getAttribute("user_key");
		if (LoginId == null || "".equals(LoginId)) {
			System.out.println("==========验证session失败");
			Cookie cookie = CookieUtil.getCookie(req, "user_key");
			if (cookie != null) {
				System.out.println("验证cookie------------------------");
				session.setAttribute("user_key",
						CookieUtil.getByUsernameByCookie(req, "user_key"));
				chain.doFilter(request, response);
				return;
			} else {
				resp.sendRedirect(req.getContextPath() + "/LoginServlet");
			}
		} else {
			System.out.println("验证session成功");
			chain.doFilter(req, resp);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
