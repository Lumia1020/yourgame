package com.tiema.user.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.user.dao.UserViewDao;
import com.tiema.user.entity.UserView;

/**
 * @ClassName: UserViewDaoImpl
 * @Description: UserViewDaoImpl的Hibernate实现类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 28, 2010 10:45:02 AM
 * 
 */
@Component("userViewDao")
public class UserViewDaoImpl extends GenericDaoHibernateImpl<UserView, Long, UserViewDao> implements UserViewDao {

	public UserViewDaoImpl() {
	}

}
