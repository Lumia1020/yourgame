package com.lhq.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.po.Permissions;

public interface IPermissionsDao {

	List findByCriteria(DetachedCriteria result);

	List findByCriteria(DetachedCriteria result, int start, int limit);

	int findByCount(DetachedCriteria count);

	List findByExample(Permissions permissions);

	Integer executeUpdate(String hql, String[] names, String[] values);

	Integer executeUpdate(String hql);

	List findByHQL(String hql);

	Permissions findById(String uuid);

	List findByProperty(String propertyName, Object value);

	void saveOrUpdate(Permissions permissions);

	Permissions savePermissions(Permissions permissions);


}
