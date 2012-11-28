package org.guess.staffingsystem.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.guess.staffingsystem.bean.Department;
import org.guess.staffingsystem.bean.Staff;
import org.guess.staffingsystem.bean.Staff.Sex;
import org.guess.staffingsystem.dao.DepartmentDao;
import org.guess.staffingsystem.dao.StaffDao;
import org.guess.staffingsystem.util.TimeTools;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestStaff {
	
	ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	DepartmentDao departmentDao = context.getBean(DepartmentDao.class);
	StaffDao staffDao = context.getBean(StaffDao.class);
	
	@Test
	public void testAdd(){
		Department department = departmentDao.get(1);
		Staff staff = new Staff();
		staff.setCompanyData(TimeTools.getCurrentTimeNoSeconds());
		staff.setDepartment(department);
		staff.setLoginId("rguess");
		staff.setPassword("rguess");
		staff.setPosition("部长");
		staff.setSex(Sex.男);
		staff.setStaffName("你好");
		staff.setSalary(10000);
		staff.setPhoneNumber("15108276486");
		staff.setAddress("成都");
		staffDao.add(staff);
	}
	
	@Test
	public void testfindByDepartment(){
		String hql = "from Staff where department_id = 1";
		List<Staff> list = staffDao.list(hql);
		for(Staff staff:list){
			System.out.println(staff.getPassword());
		}
	}
	
	@Test
	public void testFindByMap(){
		String department = "技术部";
		List<Staff> list = staffDao.getBySome(0,20,null, null, null,null);
		for(Staff staff:list){
			System.out.println(staff.getSex().toString());
		}
	}
	
	@Test
	public void testCountFindBySome() throws NoSuchFieldException, SecurityException{
//		System.out.println(staffDao.countGetBySome(null, null, null));
		System.out.println(Staff.class.getDeclaredField("sex").getType().getName());
	}

}
