package com.tiema.role.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.role.dao.RoleDao;
import com.tiema.role.entity.Role;

/**
 * @ClassName: RoleDaoImpl
 * @Description: Hibernate实现的RoleDao
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 1, 2010 2:34:28 PM
 * 
 */
@Component("roleDao")
public class RoleDaoImpl extends GenericDaoHibernateImpl<Role, Long, RoleDao> implements RoleDao {

	public RoleDaoImpl() {
	}

}
