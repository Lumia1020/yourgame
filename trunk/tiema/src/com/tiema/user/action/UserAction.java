package com.tiema.user.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tiema.core.action.BaseAction;
import com.tiema.core.constant.State;
import com.tiema.core.dao.hibernate.core.EnhancedExample;
import com.tiema.core.orm.Page;
import com.tiema.user.entity.User;
import com.tiema.user.entity.UserView;
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

	private static final Logger	log					= LoggerFactory.getLogger(UserAction.class);
	private static final long	serialVersionUID	= -302435454110478109L;

	/** @Fields userId : 用户id */
	private Long				userId;

	/** @Fields user : 用户对象 */
	private User				user;

	/** @Fields user_password2 : 确认密码 */
	private String				user_password2;

	/** @Fields userView : 用户视图 */
	private UserView			userView;

	/** @Fields users : 用户集合 */
	private List<User>			users;

	/** @Fields page : 分页数据 */
	private Page				page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean				success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>	jsons;

	private UserService			userService;

	public UserAction() {
	}

	/**
	 * @Title: load
	 * @Description: 加载一个用户对象
	 */
	public String load() {
		try {
			setUserView(userService.findUserViewById(userId));
			setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: add
	 * @Description: 添加用户
	 */
	public String add() throws Exception {
		user.setSkin("22");
		user.setState(State.VALID);
		try {
			setUserView(userService.addUserReturnView(user));
			setSuccess(true);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * @Title: changeState
	 * @Description: 跟换用户的系统皮肤
	 */
	public String updateUserState() {
		User u = (User) getSession().get("user");
		u.setSkin(getParameters().get("State")[0]);

		try {
			User _u = userService.update(u);
			getSession().put("user", _u);
			setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: update
	 * @Description: 修改用户信息,返回用户修改后的视图对象
	 */
	public String update() {
		try {
			setUserView(userService.updateUserReturnView(user));
			setSuccess(true);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: findUserPage
	 * @Description: 用户分页
	 */
	public String findUserPage() {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		if (user != null) {
			dc.add(EnhancedExample.createDefaultAll(user, true));
		}
		getPage().setCriteria(dc);
		this.setPage(userService.findPage(getPage()));
		return SUCCESS;
	}

	/**
	 * @Title: findUserPage
	 * @Description: 用户分页_视图
	 */
	public String findUserViewPage() {
		DetachedCriteria dc = DetachedCriteria.forClass(UserView.class);
		if (userView != null) {
			dc.add(EnhancedExample.createDefaultAll(userView, true));
		}
		getPage().setCriteria(dc);
		this.setPage(userService.findPage(getPage()));
		return SUCCESS;
	}

	/**
	 * @Title: login
	 * @Description: 用户登录
	 * @throws Exception
	 */
	public String login() throws Exception {
		User u = userService.exists(user);
		if (u != null) {
			getSession().put("user", u);
			return SUCCESS;
		}
		addFieldError("login_fail", "请检查用户名密码是否正确!");
		return INPUT;
	}

	/**
	 * @Title: logout
	 * @Description: 退出系统
	 */
	public String logout() {
		getSession().remove("user");
		log.debug("移除session中的用户信息:" + getSession().get("user"));
		setSuccess(true);
		return INPUT;
	}

	/**
	 * @Title: delete
	 * @Description: 删除用户
	 */
	public String delete() {
		try {
			userService.deleteById(userId);
			setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Map<String, Object> getJsons() {
		return jsons;
	}

	public Page getPage() {
		return page;
	}

	public boolean getSuccess() {
		return success;
	}

	public User getUser() {
		return user;
	}

	public Long getUserId() {
		return userId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setJsons(Map<String, Object> jsons) {
		this.jsons = jsons;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserId(Long userid) {
		this.userId = userid;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserView getUserView() {
		return userView;
	}

	public void setUserView(UserView userView) {
		this.userView = userView;
	}

	public String getUser_password2() {
		return user_password2;
	}

	public void setUser_password2(String user_password2) {
		this.user_password2 = user_password2;
	}

}
