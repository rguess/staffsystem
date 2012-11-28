package org.guess.staffingsystem.dao.impl;

import org.guess.staffingsystem.bean.Department;
import org.guess.staffingsystem.core.ObjectDaoImpl;
import org.guess.staffingsystem.dao.DepartmentDao;

@SuppressWarnings("rawtypes")
public class DepartmentDaoImpl extends ObjectDaoImpl<Department, Integer>
		implements DepartmentDao {

	public DepartmentDaoImpl(Class persistentClass) {
		super(persistentClass);
	}
	

}
