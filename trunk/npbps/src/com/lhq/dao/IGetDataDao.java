package com.lhq.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.po.GetData;

public interface IGetDataDao {

	List findByCriteria(DetachedCriteria result);

	List findByCriteria(DetachedCriteria result, int start, int limit);

	int findByCount(DetachedCriteria count);

	List findByExample(GetData getData);

	Integer executeUpdate(String hql, String[] names, String[] values);

	Integer executeUpdate(String hql);

	List findByHQL(String hql);

	GetData findById( String id);

	List findByProperty(String propertyName, Object value);

	void saveOrUpdate(GetData getData);

	GetData saveGetData(GetData getData);

	boolean update(GetData di);

	Integer executeUpdate(String hql, String[] names, String[] types, Object[] values);

	List findByNativeSql(String hql,Class c);

	List findByNativeSql(String hql, int start, int limit,Class c);

}
