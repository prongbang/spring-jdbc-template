package com.prongbang.sjt.dao;

import java.io.Serializable;
import java.util.List;

public interface AbstractDao<T, PK extends Serializable> {
	
	public T findByPK(PK pk) throws Exception;

	public int save(T entity) throws Exception;

	public int update(T entity) throws Exception;

	public int delete(T entity) throws Exception;

	public List<T> findAll() throws Exception;
}
