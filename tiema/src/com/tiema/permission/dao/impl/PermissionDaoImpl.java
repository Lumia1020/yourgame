package com.tiema.permission.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.core.orm.Page;
import com.tiema.permission.dao.PermissionDao;
import com.tiema.permission.entity.Permission;

@Component("permissionDao")
public class PermissionDaoImpl extends GenericDaoHibernateImpl<Permission, Long, PermissionDao> implements PermissionDao {

	public PermissionDaoImpl() {
		super();
	}


}
