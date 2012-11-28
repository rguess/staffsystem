package org.guess.staffingsystem.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.guess.staffingsystem.bean.Staff;
import org.guess.staffingsystem.dao.StaffDao;
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("staffName");
		String password = request.getParameter("password");
		List<Staff> list = staffDao.list("from Staff where loginId ='" + username
				+ "'");
		if (!list.isEmpty()) {
			Staff staff = list.get(0);
			if (password.equals(staff.getPassword())) {
				response.sendRedirect(request.getContextPath()
						+ "/pages/staff.html");
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
