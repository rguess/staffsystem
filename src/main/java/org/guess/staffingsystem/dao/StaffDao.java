package org.guess.staffingsystem.dao;

import java.util.List;
import java.util.Map;

import org.guess.staffingsystem.bean.Staff;
import org.guess.staffingsystem.core.ObjectDao;

public interface StaffDao extends ObjectDao<Staff,Integer>{

	public List<Staff> getBySome(final int offset,final int length, final Map<String, String> equalCondition,
			final Map<String, String> likeCondition,final String departmentName,final String sex);
	
	public long countGetBySome(final Map<String, String> equalCondition,
			final Map<String, String> likeCondition,final String departmentName,final String sex);
}
