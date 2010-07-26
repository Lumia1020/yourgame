package com.lhq.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.lhq.core.MyUtils;
import com.lhq.core.Page;
import com.lhq.dao.IPermissionsDao;
import com.lhq.dao.IUserDao;
import com.lhq.po.Permissions;
import com.lhq.po.User;
import com.lhq.service.IUserService;

/**
 * 用户业务接口实现
 * 
 * @author yourgame
 * 
 */
public class UserService implements IUserService {
	
	private IPermissionsDao permissionsDao;

	private IUserDao userDao;

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@SuppressWarnings("unchecked")
	public User login(User user) {
		List<User> users = userDao.findByExample(user);
		if (users != null && !users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	public Page getUserList(Page page) {
		try {
			if (page.getLimit() == 0) {
				page.setRoot(userDao.findByCriteria(page.getResult()));
			} else {
				page.setRoot(userDao.findByCriteria(page.getResult(), page.getStart(), page.getLimit()));
				page.setTotalProperty(userDao.findByCount(page.getCount()));
			}
			page.setSuccess(true);
		} catch (RuntimeException e) {
			page.setSuccess(false);
			e.printStackTrace();
		}
		return page;
	}

	public boolean deleteUser(User user) {
		Integer rows = userDao.executeUpdate("delete User where uuid = :uuid", new String[] { "uuid" },
				new String[] { user.getUuid() });
		return rows > 0 ? true : false;
	}

	public boolean updateUser(User user) {
		Integer rows = userDao.executeUpdate("update User set userName=:userName,password=:password where uuid=:uuid",
				new String[] { "userName", "password", "uuid" }, new String[] { user.getUserName(), user.getPassword(),
						user.getUuid() });
		return rows > 0 ? true : false;
	}

	public boolean addUser(User user) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List users = userDao.findByExample(new User(user.getUserId()));
		if(users != null && !users.isEmpty()){
			throw new RuntimeException("此帐号已存在!");
		}
		Permissions role = permissionsDao.findById(user.getPid());//查找角色
		Permissions p = new Permissions();
		MyUtils.copyProperties(p, role);
		p.setPid(null);
		p.setParentId(role.getPid());
		p.setRoleName(null);
		p = permissionsDao.savePermissions(p);
		user.setPid(p.getPid());
		user = userDao.saveUser(user);
		if(user.getUuid() == null){
			throw new RuntimeException("用户添加失败!");
		}
		return true;
	}

	public void setPermissionsDao(IPermissionsDao permissionsDao) {
		this.permissionsDao = permissionsDao;
	}

}
