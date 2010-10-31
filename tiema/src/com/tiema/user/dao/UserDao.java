package com.tiema.user.dao;

import com.tiema.core.dao.GenericDao;
import com.tiema.user.entity.User;

/**
 * @ClassName: UserDao
 * @Description: 用户数据访问对象接口定义
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 27, 2010 4:43:25 PM
 * 
 */
public interface UserDao extends GenericDao<User, Long> {

	/**
	 * @Title: find
	 * @Description: 根据用户名查找用户对象
	 * @param username 用户名
	 * @return User 用户对象
	 */
	public User find(String username);

	/**
	 * @Title: find
	 * @Description: 根据用户名和密码查找用户对象
	 * @param username 用户名 
	 * @param password 密码
	 * @return User 用户对象
	 */
	public User find(String username, String password);
}
