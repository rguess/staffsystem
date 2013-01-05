package org.guess.staffingsystem.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.guess.staffingsystem.bean.SexConverter;
import org.guess.staffingsystem.bean.Staff;
import org.guess.staffingsystem.bean.Staff.Sex;
import org.guess.staffingsystem.dao.DepartmentDao;
import org.guess.staffingsystem.dao.StaffDao;
import org.guess.staffingsystem.util.CookieUtil;
import org.guess.staffingsystem.util.TimeTools;

@Path("/staff")
public class StaffService {

	private StaffDao staffDao;

	private DepartmentDao departmentDao;

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Staff> getAll() {

		return staffDao.getList(null, null);
	}

	@DELETE
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}")
	public void deleteById(@PathParam("id") int id) {

		staffDao.deleteById(id);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/count")
	public long countPage(@Context UriInfo uriInfo) {
		String department = null;
		String sex = null;
		MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
		Map<String, String> likeCondition = new HashMap<String, String>();
		Map<String, String> equalCondition = new HashMap<String, String>();
		if (params.getFirst("staffName") != null
				&& !"".equals(params.getFirst("staffName"))) {
			likeCondition.put("staffName", params.getFirst("staffName"));
		}
		if (params.getFirst("address") != null
				&& !"".equals(params.getFirst("address"))) {
			likeCondition.put("address", params.getFirst("address"));
		}
		if (params.getFirst("phoneNumber") != null
				&& !"".equals(params.getFirst("phoneNumber"))) {
			equalCondition.put("phoneNumber", params.getFirst("phoneNumber"));
		}
		if (params.getFirst("position") != null
				&& !"".equals(params.getFirst("position"))) {
			equalCondition.put("position", params.getFirst("position"));
		}
		if (params.getFirst("department") != null) {
			department = params.getFirst("department");
		}
		if (params.getFirst("sex") != null) {
			sex = params.getFirst("sex");
		}
		return staffDao.countGetBySome(equalCondition, likeCondition,
				department, sex);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listForPage")
	public List<Staff> listForPage(@Context UriInfo uriInfo) {
		String department = null;
		String sex = null;
		MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
		int offset = Integer.valueOf(params.getFirst("pageIndex"));
		int length = Integer.valueOf(params.getFirst("pagesize"));

		Map<String, String> likeCondition = new HashMap<String, String>();
		Map<String, String> equalCondition = new HashMap<String, String>();
		if (params.getFirst("staffName") != null
				&& !"".equals(params.getFirst("staffName"))) {
			likeCondition.put("staffName", params.getFirst("staffName"));
		}
		if (params.getFirst("address") != null
				&& !"".equals(params.getFirst("address"))) {
			likeCondition.put("address", params.getFirst("address"));
		}
		if (params.getFirst("phoneNumber") != null
				&& !"".equals(params.getFirst("phoneNumber"))) {
			equalCondition.put("phoneNumber", params.getFirst("phoneNumber"));
		}
		if (params.getFirst("position") != null
				&& !"".equals(params.getFirst("position"))) {
			equalCondition.put("position", params.getFirst("position"));
		}
		if (params.getFirst("department") != null) {
			department = params.getFirst("department");
		}
		if (params.getFirst("sex") != null) {
			sex = params.getFirst("sex");
		}
		return staffDao.getBySome(offset, length, equalCondition,
				likeCondition, department, sex);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Staff getById(@PathParam("id") int id) {

		return staffDao.get(id);
	}

	@SuppressWarnings("unchecked")
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String addStaff(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		Staff staff = new Staff();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					if ("department".equals(item.getFieldName())) {
						if ("暂不分配".equals(item.getString("utf-8"))) {

						} else {
							staff.setDepartment(departmentDao.list(
									"from Department where name ='"
											+ item.getString("utf-8") + "'")
									.get(0));
						}
					} else {
						ConvertUtils.register(new SexConverter(), Sex.class);
						BeanUtils.copyProperty(staff, item.getFieldName(),
								item.getString("utf-8"));
					}
				} else {
					 File f = new File("src/main/webapp/image/"
					 + staff.getLoginId()
					 + item.getName().substring(
					 item.getName().lastIndexOf(".")));
					 f.createNewFile();
					 item.write(f);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		staff.setCompanyDate(TimeTools.getCurrentTimeNoSeconds());
		System.out.println(staff.getLoginId());
		staffDao.add(staff);
		return "success";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getByDpartment/{id}")
	public List<Staff> getByDepartment(@PathParam("id") int id) {
		String hql = "from Staff where department_id = " + id + "";
		return staffDao.list(hql);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/loginIdIsExist/{loginId}")
	public String CheckLoginIdIsExit(@PathParam("loginId") String loginId) {
		List<Staff> list = staffDao.list("from Staff where loginId='" + loginId
				+ "'");
		if (list.isEmpty()) {
			return "no";
		} else {
			return "yes";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUserBySession")
	public Staff getLoginId(@Context HttpServletRequest request) {

		String loginId = CookieUtil.getByUsernameByCookie(request, "user_key");
		if(loginId == null){
			return null;
		}
		Staff staff = staffDao.list(
				"from Staff where loginId='" + loginId + "'").get(0);
		return staff;
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("/loginOut")
	public String LoginOut(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		HttpSession session = request.getSession();
		session.removeAttribute("user_key");
		Cookie cookie = new Cookie("user_key", null);
		cookie.setMaxAge(0);
		// cookie.setPath("/");
		response.addCookie(cookie);
		return "success";
	}

}
