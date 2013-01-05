package org.guess.staffingsystem.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 员工实体类
 * @author rguess
 *
 */
@Entity
@Table(name="t_staff")
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="staffname",length=20)
	private String staffName;
	
	@Column(name="loginId",length=20)
	private String loginId;
	
	@Column(name="password",length=20)
	private String password;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH },fetch = FetchType.EAGER,optional=true)  
	@JoinColumn(name = "department_id",nullable=true)  
	private Department department;
	
	@Column(name="sex")
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	@Column(name="salary")
	private double salary;
	
	@Column(name="address")
	private String address;
	
	@Column(name="phone")
	private String phoneNumber;
	
	/*
	 * 职位
	 */
	@Column(name="position")
	private String position;
	
	/*
	 * 进公司日期
	 */
	@Column(name="companyData")
	private String companyDate;

	public enum Sex{
		男,女;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCompanyDate() {
		return companyDate;
	}

	public void setCompanyDate(String companyDate) {
		this.companyDate = companyDate;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", staffName=" + staffName + ", loginId="
				+ loginId + ", password=" + password + ", department="
				+ department + ", sex=" + sex + ", salary=" + salary
				+ ", address=" + address + ", phoneNumber=" + phoneNumber
				+ ", position=" + position + ", companyData=" + companyDate
				+ "]";
	}
}
