package com.lhq.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.po.Dispense;

public interface IDispenseDao {

	List findByCriteria(DetachedCriteria result);

	List findByCriteria(DetachedCriteria result, int start, int limit);

	int findByCount(DetachedCriteria count);

	List findByExample(Dispense dispense);

	Integer executeUpdate(String hql, String[] names, String[] values);

	Integer executeUpdate(String hql);

	List findByHQL(String hql);

	Dispense findById( String id);

	List findByProperty(String propertyName, Object value);

	void saveOrUpdate(Dispense dispense);

	Dispense saveDispense(Dispense dispense);

	boolean update(Dispense di);

	Integer executeUpdate(String hql, String[] names, String[] types, Object[] values);

}
