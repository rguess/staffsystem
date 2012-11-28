package org.guess.staffingsystem.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

public interface ObjectDao<T,PK extends Serializable> {

	@Transactional
	public void add(T obj);
	
	@Transactional
	public void delete(T obj);
	
	@Transactional
	public void deleteById(PK id);
	
	@Transactional
	public void update(T obj);
	
	public T get(PK id);
	public List<T> list(String queryString);
	public List<T> getListForPage(final int offset, final int length,final Map<String, String> eqProperties, final Map<String, String> likeProperties);
	public List<T> getList(final Map<String,String> eqProperties, final Map<String,String> likeProperties);
	public long count(final Map<String,String> eqProperties, final Map<String,String> likeProperties);

}
