package com.tiema.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.user.dao.UserDao;
import com.tiema.user.entity.User;
import com.tiema.user.service.UserService;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户管理业务层实现类,实现了对用户增删改差等操作
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 27, 2010 1:39:48 AM
 */
@Component("userService")
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	@Override
	public User add(User user) throws Exception {
		return userDao.save(user);
	}

	@Override
	public boolean exists(String username) throws Exception {
		User user = userDao.find(username);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public User exists(User user) throws Exception {
		return userDao.find(user.getUsername(),user.getPassword());
	}

	@Override
	public List<User> findUsers() {
		return userDao.findEntities();
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id);
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
