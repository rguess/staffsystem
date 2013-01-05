package org.guess.staffingsystem.test;

import java.util.List;

import org.guess.staffingsystem.bean.Department;
import org.guess.staffingsystem.bean.Staff;
import org.guess.staffingsystem.bean.Staff.Sex;
import org.guess.staffingsystem.dao.DepartmentDao;
import org.guess.staffingsystem.dao.StaffDao;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestDepartment {

	ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	DepartmentDao departmentDao = context.getBean(DepartmentDao.class);
	StaffDao staffDao = context.getBean(StaffDao.class);
	
	
	@Test
	public void testAdd(){
		Department department = new Department();
		department.setName("外交部");
		department.setIntroduction("非常NB啊");
		departmentDao.add(department);
		System.out.println("success");
	}
	
	@Test
	public void testCount(){
		System.out.println(departmentDao.count(null, null));
	}
	
//	@Test
//	public void testFindByStaff(){
//		Staff staff = staffDao.get(1);
//		Department department = staff.getDepartment();
//		System.out.println(department.getIntroduction());
//	}
	
	@Test
	public void testFindByName(){
		Department department = departmentDao.list("from Department where name='技术部'").get(0);
		System.out.println(department.getIntroduction());
	}
	
	@Test
	public void testEnum(){
		System.out.println(Sex.valueOf("男"));
	}
	
	@Test
	public void testGetDepartmentName(){
		List<Department> list = departmentDao.list("select department.name from Department department");
		System.out.println(list.get(0));
	}
	
	@Test
	public void testQuery(){
		List<Staff> list = staffDao.getBySome(0, 5, null, null, "未分配",null);
		System.out.println(list.size());
		System.out.println(list.get(0).getLoginId());
	}
	
}
