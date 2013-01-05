package org.guess.staffingsystem.test;

import java.util.List;

import org.guess.staffingsystem.bean.Department;
import org.guess.staffingsystem.bean.Staff;
import org.guess.staffingsystem.bean.Staff.Sex;
import org.guess.staffingsystem.dao.DepartmentDao;
import org.guess.staffingsystem.dao.StaffDao;
import org.guess.staffingsystem.util.RandomUtil;
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
		for (int i = 0; i < 100; i++) {
			Department department = departmentDao.get((int) (Math.random() * 3));
			Staff staff = new Staff();
			staff.setCompanyDate(TimeTools.getCurrentTimeNoSeconds());
			staff.setDepartment(department);
			staff.setLoginId(RandomUtil.getUsername());
			staff.setPassword(RandomUtil.getUsername());
			staff.setPosition("员工");
			if (i%2 == 0) {
				staff.setSex(Sex.男);
			}else {
				staff.setSex(Sex.女);
			}
			staff.setStaffName(RandomUtil.getNickname());
			staff.setSalary((int) (Math.random() * 100000));
			staff.setPhoneNumber(RandomUtil.getPhone());
			staff.setAddress(RandomUtil.getAddress());
			System.out.println(i);
			staffDao.add(staff);
		}
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
//		System.out.println(Staff.class.getDeclaredField("sex").getType().getName());
		String LoginId = "rguess";
		List<Staff> list = staffDao.list("from Staff where loginId='"+LoginId+"'");
		for(Staff staff:list){
			System.out.println(staff.getSex().toString());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Sex.class);
	}

}
