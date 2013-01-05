package org.guess.staffingsystem.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.guess.staffingsystem.bean.Staff;
import org.guess.staffingsystem.dao.StaffDao;
import org.guess.staffingsystem.util.AESencrp;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private StaffDao staffDao;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		staffDao = (StaffDao) ctx.getBean("staffDao");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath()+"/login.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("staffName");
		String password = request.getParameter("password");
		List<Staff> list = staffDao.list("from Staff where loginId ='" + username+ "'");
		if (!list.isEmpty()) {
			Staff staff = list.get(0);
			if (password.equals(staff.getPassword())) {
				
				String ip = request.getRemoteAddr().toString();
				String browser = request.getHeader("user-agent");
				//设置session
				HttpSession session = request.getSession();
				session.setAttribute("user_key", staff.getLoginId());
				//设置cookie
				String secret;
				try {
					secret = AESencrp.encrypt(ip + ":" + browser);
					System.out.println(secret);
					Cookie c = new Cookie("user_key", username + "####" + secret);
					c.setMaxAge(60 * 60 * 24 * 365);
					c.setPath("/");
					response.addCookie(c);
					response.sendRedirect(request.getContextPath()
							+ "/pages/staff.html");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				response.sendRedirect(request.getContextPath()
						+ "/login.html?password=error");
			}
		} else {
			response.sendRedirect(request.getContextPath()
					+ "/login.html?LoginId=error");
		}

	}

}
