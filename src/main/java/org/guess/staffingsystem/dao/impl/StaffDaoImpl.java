package org.guess.staffingsystem.dao.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.guess.staffingsystem.bean.Staff;
import org.guess.staffingsystem.bean.Staff.Sex;
import org.guess.staffingsystem.core.ObjectDaoImpl;
import org.guess.staffingsystem.dao.StaffDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

@SuppressWarnings("rawtypes")
public class StaffDaoImpl extends ObjectDaoImpl<Staff, Integer> implements
		StaffDao {

	public StaffDaoImpl(Class persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Staff> getBySome(final int offset, final int length,
			final Map<String, String> equalCondition,
			final Map<String, String> likeCondition,
			final String departmentName, final String sex) {
		
		return super.getHibernateTemplate().executeFind(
				new HibernateCallback<List<Staff>>() {
					@Override
					public List<Staff> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(Staff.class);
						try {
							if (equalCondition != null) {
								Set<String> keySet = equalCondition.keySet();
								Iterator<String> it = keySet.iterator();
								while (it.hasNext()) {
									String key = it.next();
									String value = equalCondition.get(key);
									if (Staff.class.getDeclaredField(key)
											.getType().getName().equals("int")) {
										crit.add(Restrictions.eq(key,
												Integer.parseInt(value)));
									} else if (Staff.class
											.getDeclaredField(key).getType()
											.getName().equals("double")) {
										crit.add(Restrictions.eq(key,
												Double.parseDouble(value)));
									} else if (Staff.class
											.getDeclaredField(key).getType()
											.getName().equals("float")) {
										crit.add(Restrictions.eq(key,
												Float.parseFloat(value)));
									} else {
										crit.add(Restrictions.eq(key, value));
									}
								}
							}
							if (likeCondition != null) {
								Set<String> keySet = likeCondition.keySet();
								Iterator<String> it = keySet.iterator();
								while (it.hasNext()) {
									String key = it.next();
									String value = likeCondition.get(key);
									if (value != null) {
										crit.add(Restrictions.like(key, value,
												MatchMode.ANYWHERE));
									}
								}
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
						if (departmentName != null && "" != departmentName) {
							if ("未分配".equals(departmentName)) {
								crit.add(Restrictions.isNull("department"));
							} else {
								crit.createCriteria("department")
										.add(Restrictions.eq("name",
												departmentName));
							}
						}
						if (sex != null && "" != sex) {
							crit.add(Restrictions.eq("sex", Sex.valueOf(sex)));
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.setFirstResult(offset);
						crit.setMaxResults(length);
						crit.addOrder(Order.desc("id"));
						List<Staff> page = crit.list();
						return page;
					}

				});
	}

	@Override
	public long countGetBySome(final Map<String, String> equalCondition,
			final Map<String, String> likeCondition,
			final String departmentName, final String sex) {

		long count = super.getHibernateTemplate().execute(
				new HibernateCallback<Long>() {
					@Override
					public Long doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(Staff.class);
						crit.setProjection(Projections.rowCount());
						try {
							if (equalCondition != null) {
								Set<String> keySet = equalCondition.keySet();
								Iterator<String> it = keySet.iterator();
								while (it.hasNext()) {
									String key = it.next();
									String value = equalCondition.get(key);
									if (Staff.class.getDeclaredField(key)
											.getType().getName().equals("int")) {
										crit.add(Restrictions.eq(key,
												Integer.parseInt(value)));
									} else if (Staff.class
											.getDeclaredField(key).getType()
											.getName().equals("double")) {
										crit.add(Restrictions.eq(key,
												Double.parseDouble(value)));
									} else if (Staff.class
											.getDeclaredField(key).getType()
											.getName().equals("float")) {
										crit.add(Restrictions.eq(key,
												Float.parseFloat(value)));
									} else {
										crit.add(Restrictions.eq(key, value));
									}
								}
							}
							if (likeCondition != null) {
								Set<String> keySet = likeCondition.keySet();
								Iterator<String> it = keySet.iterator();
								while (it.hasNext()) {
									String key = it.next();
									String value = likeCondition.get(key);
									if (value != null) {
										crit.add(Restrictions.like(key, value,
												MatchMode.ANYWHERE));
									}
								}
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
						if (departmentName != null && "" != departmentName) {
							if ("未分配".equals(departmentName)) {
								crit.add(Restrictions.isNull("department"));
							} else {
								crit.createCriteria("department")
										.add(Restrictions.eq("name",
												departmentName));
							}
						}
						if (sex != null && "" != sex) {
							crit.add(Restrictions.eq("sex", Sex.valueOf(sex)));
						}
						return (Long) crit.uniqueResult();
					}

				});
		return count;
	}

}
