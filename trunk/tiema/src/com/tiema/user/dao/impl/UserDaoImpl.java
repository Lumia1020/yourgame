package com.tiema.user.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.impl.GenericDaoHibernateImpl;
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

	@Override
	public User find(String username) {
		return this.findEntity("from User u where u.username = ? ", new Object[] { username });
	}

	@Override
	public User find(String username, String password) {
		return this.findEntity("from User u where u.username = ? and u.password = ?", new Object[] { username, password });
	}

}
