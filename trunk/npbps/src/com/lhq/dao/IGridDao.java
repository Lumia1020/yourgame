package com.lhq.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.po.Grid;

public interface IGridDao {

	List findByCriteria(DetachedCriteria result);

	List findByCriteria(DetachedCriteria result, int start, int limit);

	int findByCount(DetachedCriteria count);

	List findByExample(Grid grid);

	Integer executeUpdate(String hql, String[] names, String[] values);

	Integer executeUpdate(String hql);

	List findByHQL(String hql);

	Grid findById(Integer id);

	List findByProperty(String propertyName, Object value);

	void saveOrUpdate(Grid grid);

	Grid saveGrid(Grid grid);

}
