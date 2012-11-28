package org.guess.staffingsystem.service;

import java.util.List;

import javax.ws.rs.GET;
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
	public List<Department> getAll(){
		return departmentDao.getList(null, null);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Department getById(@PathParam("id") int id){
		
		return departmentDao.get(id);
	}
	
	@GET
	@Path("/getDepartmentName")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Department> getListName(){
		
		return departmentDao.list("select department.name from Department department");
	}
	
	

}
