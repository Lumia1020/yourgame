package com.lhq.service;

import java.lang.reflect.InvocationTargetException;

import com.lhq.core.Page;
import com.lhq.po.User;

/**
 * 用户业务逻辑接口
 * 
 * @author yourgame
 * 
 */
public interface IUserService {

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user);

	public Page getUserList(Page page);

	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public boolean deleteUser(User user);

	public boolean updateUser(User user);

	public boolean addUser(User user) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
