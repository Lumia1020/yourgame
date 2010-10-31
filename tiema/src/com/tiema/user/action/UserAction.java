package com.tiema.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tiema.core.Page;
import com.tiema.core.action.BaseAction;
import com.tiema.user.entity.User;
import com.tiema.user.service.UserService;

/**
 * @ClassName: UserAction
 * @Description: 用户管理类,登录,登出,新增,删除,修改,查询用户等..
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 25, 2010 11:03:33 PM
 */
@Component
@Scope("prototype")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = -302435454110478109L;
	private static final Logger log = LoggerFactory.getLogger(UserAction.class);

	/** @Fields userid : 用户id */
	private Long userid;

	/** @Fields user : 用户对象 */
	private User user;

	/** @Fields users : 用户集合 */
	private List<User> users;

	/** @Fields page : 分页对象 */
	private Page<?> page;

	private UserService userService;

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserAction() {
	}

	/**
	 * @Title: login
	 * @Description: 用户登录
	 * @throws Exception
	 */
	public String login() throws Exception {
		log.debug("用户登录,username: " + user.getUsername() + " password: " + user.getPassword());
		User u = userService.exists(user);
		setSuccess(u != null);
		return SUCCESS;
	}

	/**
	 * @Title: add
	 * @Description: 添加用户
	 * @param
	 * @return String
	 * @throws
	 */
	public String add() throws Exception {
		userService.add(user);
		return SUCCESS;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

}
