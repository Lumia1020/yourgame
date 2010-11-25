package com.tiema.user.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.core.orm.Page;
import com.tiema.membership.category.service.exception.MembershipCategoryDeleteFailureException;
import com.tiema.role.dao.RoleDao;
import com.tiema.role.entity.Role;
import com.tiema.user.dao.UserDao;
import com.tiema.user.dao.UserViewDao;
import com.tiema.user.entity.User;
import com.tiema.user.entity.UserView;
import com.tiema.user.service.UserService;
import com.tiema.user.service.exception.UserDeleteFailureException;
import com.tiema.util.MyUtils;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户管理业务层实现类,实现了对用户增删改差等操作
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 27, 2010 1:39:48 AM
 */
@Component("userService")
public class UserServiceImpl implements UserService {

	private UserDao		userDao;
	private UserViewDao	userViewDao;
	private RoleDao		roleDao;

	@Override
	public User add(User user) {

		List<Long> ids = new ArrayList<Long>();
		for (Role r : user.getRoles()) {
			ids.add(r.getId());
		}
		if (ids.size() > 0) {
			List<Role> roles = roleDao.findByIds(ids);
			Set<Role> sets = new HashSet<Role>();
			sets.addAll(roles);
			user.setRoles(sets);
		}
		return userDao.save(user);
	}

	@Override
	public void delete(User entity) {
		try {
			userDao.delete(entity);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			userDao.deleteById(id);
		} catch (RuntimeException e) {
			throw new UserDeleteFailureException("用户删除失败!", e);
		}
	}

	@Override
	public boolean exists(String username) {
		User user = userDao.findEntity("from User u where u.username = ? ", new Object[] { username });
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean existsNeIdAndEqUsername(Long id, String username) {
		String hql = "from User u where u.id <> ? and u.username = ?";
		List<User> list = userDao.findEntities(hql, new Object[] { id, username });
		return list.size() > 0;
	}

	@Override
	public User exists(User user) {
		return userDao.findEntity("from User u where u.username = ? and u.password = ?", new Object[] { user.getUsername(), user.getPassword() });
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public List<User> findEntities() {
		return userDao.findEntities();
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Resource
	public void setUserViewDao(UserViewDao userViewDao) {
		this.userViewDao = userViewDao;
	}

	@Override
	public User update(User entity) {
		return userDao.update(entity);
	}

	@Override
	public UserView updateUserReturnView(User user) {
		User u = userDao.findById(user.getId());
		MyUtils.copyProperties(u, user);
		userDao.update(u);
		userDao.flush();
		return userViewDao.findById(user.getId());
	}

	@Override
	public Page findPage(Page page) {
		return userDao.findByCriteria(page);
	}

	@Override
	public User findUnique(String hql, Object... values) {
		return userDao.findUnique(hql, values);
	}

	@Override
	public List<User> findEntities(String hql, Object... params) {
		return userDao.findEntities(hql, params);
	}

	@Override
	public UserView addUserReturnView(User user) {
		userDao.save(user);
		userDao.flush();
		return userViewDao.findById(user.getId());
	}

	@Override
	public UserView findUserViewById(Long userId) {
		return userViewDao.findById(userId);
	}

}
