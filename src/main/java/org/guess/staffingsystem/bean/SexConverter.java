package org.guess.staffingsystem.bean;

import org.apache.commons.beanutils.Converter;
import org.guess.staffingsystem.bean.Staff.Sex;

public class SexConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convert(Class clz, Object value) {
		if (clz != Sex.class) {
			return null;
		}
		if (value instanceof String) {
			return Sex.valueOf((String) value);
		}
		return null;
	}

}
