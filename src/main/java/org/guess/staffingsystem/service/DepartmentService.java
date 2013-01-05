package org.guess.staffingsystem.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.guess.staffingsystem.bean.Department;
import org.guess.staffingsystem.dao.DepartmentDao;

@Path("/department")
public class DepartmentService {

	private DepartmentDao departmentDao;

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Department> getAll() {
		return departmentDao.getList(null, null);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Department getById(@PathParam("id") int id) {

		return departmentDao.get(id);
	}

	@GET
	@Path("/getDepartmentName")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Department> getListName() {

		return departmentDao
				.list("select department.name from Department department");
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteById(@PathParam("id") int id) {

		departmentDao.deleteById(id);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	public String addDepartment(@FormParam("name") String name,
			@FormParam("id") int id,
			@FormParam("introduction") String introduction) {
		
		Department department = new Department();
		department.setId(id);
		department.setName(name);
		department.setIntroduction(introduction);
		departmentDao.add(department);
		return "success";
	}

}
