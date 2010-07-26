package com.lhq.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.po.Station;

public interface IStationDao {
	List findByCriteria(DetachedCriteria result);

	List findByCriteria(DetachedCriteria result, int start, int limit);

	int findByCount(DetachedCriteria count);

	List findByExample(Station station);

	Integer executeUpdate(String hql, String[] names, String[] values);

	Integer executeUpdate(String hql);

	List findByHQL(String hql);

	Station findById( Integer id);

	List findByProperty(String propertyName, Object value);

	void saveOrUpdate(Station station);

	Station saveStation(Station station);
	
	List findByNativeSql(String hql,Class c);

	List findByNativeSql(String hql, int start, int limit,Class c);
}
