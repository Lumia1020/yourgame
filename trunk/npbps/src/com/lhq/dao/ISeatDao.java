package com.lhq.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.po.Seat;

public interface ISeatDao {
	List findByCriteria(DetachedCriteria result);

	List findByCriteria(DetachedCriteria result, int start, int limit);

	int findByCount(DetachedCriteria count);

	List findByExample(Seat seat);

	Integer executeUpdate(String hql, String[] names, String[] values);

	Integer executeUpdate(String hql);

	List findByHQL(String hql);

	Seat findById( Integer id);

	List findByProperty(String propertyName, Object value);

	void saveOrUpdate(Seat seat);

	Seat saveSeat(Seat seat);

}
