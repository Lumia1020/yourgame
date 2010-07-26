package com.lhq.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.po.Dept;

public interface IDeptDao {

	List findByCriteria(DetachedCriteria result);

	List findByCriteria(DetachedCriteria result, int start, int limit);

	int findByCount(DetachedCriteria count);

	List findByExample(Dept dept);

	Integer executeUpdate(String hql, String[] names, String[] values);

	Integer executeUpdate(String hql);

	List findByHQL(String hql);

	Dept findById(Integer id);

	List findByProperty(String propertyName, Object value);

	void saveOrUpdate(Dept dept);

	Dept saveDept(Dept dept);

}
