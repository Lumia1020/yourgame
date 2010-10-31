package com.tiema.user.service;

import java.util.List;

import com.tiema.user.entity.User;

/**
 * @ClassName: UserService
 * @Description: 用户业务层接口
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 27, 2010 1:06:46 AM
 */
public interface UserService {

	/**
	 * @Title: login
	 * @Description: 判断示例(user)用户是否存在,存在则返回,不存在则返回null
	 * @param user 示例用户信息
	 * @return 验证通过则返回数据库中的user数据,否则返回null
	 * @throws Exception
	 */
	public User exists(User user) throws Exception;
	
	/**
	 * @Title: exists
	 * @Description: 判断指定用户名是否存在，若不存在则返回flase,否则为true
	 * @param username 用户名
	 * @return 是否存在的boolean值
	 * @throws Exception
	 */
	public boolean exists(String username) throws Exception;

	/**
	 * @Title: add
	 * @Description: 新增用户,并返回新增后的用户,否则返回null
	 * @param user 需要新增的用户信息
	 * @return 新增用户或者null
	 * @throws Exception
	 */
	public User add(User user) throws Exception;

	/**
	 * @Title: getUsers
	 * @DescriptionfindUsers 获取所有用户的集合
	 * @return 用户集合
	 */
	public List<User> findUsers();

	/**
	 * @Title: findById
	 * @Description: 根据用户id加载用户信息
	 * @param id 用户id
	 * @return 用户信息
	 */
	public User findById(Long id);
	
	/**
	 * @Title: delete
	 * @Description: 删除用户
	 * @param user 用户实体
	 * @return boolean 是否删除成功
	 * @throws
	 *//*
	public boolean delete(User user);
	public boolean delteById(Long id);*/

}
