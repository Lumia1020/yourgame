package com.tiema.user.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.user.dao.UserDao;
import com.tiema.user.entity.User;

/**
 * @ClassName: UserDaoImpl
 * @Description: UserDao的Hibernate实现类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 28, 2010 10:45:02 AM
 * 
 */
@Component("userDao")
public class UserDaoImpl extends GenericDaoHibernateImpl<User, Long, UserDao> implements UserDao {

	public UserDaoImpl() {
	}

}
