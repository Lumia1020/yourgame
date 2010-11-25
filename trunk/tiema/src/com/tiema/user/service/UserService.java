package com.tiema.user.service;

import com.tiema.core.service.GenericService;
import com.tiema.user.entity.User;
import com.tiema.user.entity.UserView;

/**
 * @ClassName: UserService
 * @Description: 用户业务层接口
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 27, 2010 1:06:46 AM
 */
public interface UserService extends GenericService<User, Long> {

	/**
	 * @Title: exists
	 * @Description: 判断指定用户名是否存在，若不存在则返回flase,否则为true
	 * @param username 用户名
	 * @return 是否存在的boolean值
	 * @throws Exception
	 */
	public boolean exists(String username);

	/**
	 * @Title: login
	 * @Description: 判断示例(user)用户是否存在,存在则返回,不存在则返回null
	 * @param user 示例用户信息
	 * @return 验证通过则返回数据库中的user数据,否则返回null
	 * @throws Exception
	 */
	public User exists(User user) throws Exception;

	/**
	 * @Title: existsNeIdAndEqUsername
	 * @Description: 检查不等于制定用户(id)以及等于指定用户名(username)的记录是否存在
	 * @param id 用户id
	 * @param username 用户名
	 * @return boolean 是否存在
	 */
	public boolean existsNeIdAndEqUsername(Long id, String username);

	/**
	 * @Title: addUserReturnView
	 * @Description: 添加一个用户后,从视图中返回他的记录
	 * @param user 需要新增的用户实体
	 * @return UserView 用户视图
	 */
	public UserView addUserReturnView(User user);

	/**
	 * @Title: findUserViewById
	 * @Description: 根据用户id去视图查找记录
	 * @param userId 用户id
	 * @return UserView 用户视图
	 */
	public UserView findUserViewById(Long userId);

	/**
	 * @Title: updateUserReturnView
	 * @Description: 修改用户后返回修改后的视图
	 * @param user 需要修改的实体对象
	 * @return UserView 用户视图
	 */
	public UserView updateUserReturnView(User user);

}
