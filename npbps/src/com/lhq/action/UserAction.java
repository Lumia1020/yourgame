package com.lhq.action;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.lhq.core.BaseAction;
import com.lhq.core.EnhancedExample;
import com.lhq.core.Page;
import com.lhq.po.Dept;
import com.lhq.po.Permissions;
import com.lhq.po.User;
import com.lhq.service.IDeptService;
import com.lhq.service.IPermissionsService;
import com.lhq.service.IUserService;

/**
 * 用户业务逻辑处理
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class UserAction extends BaseAction {

	private IUserService userService;

	private IDeptService deptService;

	private IPermissionsService permissionsService;

	private User user;

	private Permissions permissions;

	private Page page;

	private boolean success;

	private Map<String, Object> infos = new HashMap<String, Object>();

	/**
	 * 更新权限
	 * @return
	 */
	public String updatePermissions() {
		this.success = permissionsService.updateFieldValue(page);
		return SUCCESS;
	}

	/**
	 * 显示权限
	 * @return
	 */
	public String showPermissions() {
		getRequest().setAttribute("permissions", permissionsService.findById(permissions.getPid()));
		return SUCCESS;
	}

	/**
	 * 更新用户
	 * @return
	 */
	public String updateUser() {
		this.success = userService.updateUser(user);
		return SUCCESS;
	}

	/**
	 * 删除角色
	 * @return
	 */
	public String deleteRole() {
		this.success = permissionsService.deletePermissions(permissions);
		return SUCCESS;
	}

	/**
	 * 删除用户
	 * @return
	 */
	public String deleteUser() {
		this.success = userService.deleteUser(user);
		return SUCCESS;
	}
	
	/**
	 * 添加角色
	 * @return
	 */
	public String addRole(){
		try {
			this.success = permissionsService.addRole(permissions);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 增加用户
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public String addUser() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			this.success = userService.addUser(user);
		} catch (RuntimeException e) {
			this.infos.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 获得用户页面需要的 部门列表和角色列表
	 * @return
	 */
	public String addUserPage() {
		Page deptPage = new Page();
		DetachedCriteria deptDc = DetachedCriteria.forClass(Dept.class);
		deptPage.setResult(deptDc);
		deptPage = deptService.getDeptList(deptPage);
		// ~获得部门列表

		Page rolePage = new Page();
		DetachedCriteria roleDc = DetachedCriteria.forClass(Permissions.class);
		roleDc.add(Restrictions.isNull("parentId"));// 角色
		rolePage.setResult(roleDc);
		rolePage = permissionsService.getPermissionsList(rolePage);
		// ~获得角色

		getRequest().setAttribute("deptList", deptPage.getRoot());
		getRequest().setAttribute("roleList", rolePage.getRoot());

		return SUCCESS;
	}

	/**
	 * 显示角色
	 * @return
	 */
	public String showRoles() {
		DetachedCriteria dc = DetachedCriteria.forClass(Permissions.class);
		dc.add(Restrictions.isNull("parentId"));
		String condition = page.getParams().get("condition");
		if (condition != null && !"".equals(condition)) {
			Permissions u = new Permissions();
			u.setRoleName(condition);
			dc.add(EnhancedExample.createDefault(u));
		}

		page.setResult(dc);
		page = permissionsService.getPermissionsList(page);
		page.initPageInfo();
		getRequest().setAttribute("page", page);
		return SUCCESS;
	}

	/**
	 * 列表用户
	 * @return
	 */
	public String showUsers() {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		String condition = page.getParams().get("condition");
		if (condition != null && !"".equals(condition)) {
			User u = new User();
			u.setUserId(condition);
			u.setUserName(condition);
			dc.add(EnhancedExample.createDefault(u));
		}

		page.setResult(dc);
		page = userService.getUserList(page);
		page.initPageInfo();
		getRequest().setAttribute("page", page);
		return SUCCESS;
	}

	/**
	 * 注销
	 * @return
	 */
	public String logout() {
		getSession().removeAttribute("permissions");
		getSession().removeAttribute("user");
		getSession().removeAttribute("dept");
		return SUCCESS;
	}

	/**
	 * 登录
	 * @return
	 */
	public String login() {
		try {
			User u = userService.login(user);
			if (u != null) {
				Permissions p = permissionsService.findById(u.getPid());
				Dept d = deptService.findById(u.getDeptId());
				getSession().setAttribute("dept", d);
				getSession().setAttribute("permissions", p);
				getSession().setAttribute("user", u);
				return SUCCESS;
			}
		} catch (RuntimeException e) {
			getSession().removeAttribute("dept");
			getSession().removeAttribute("permissions");
			getSession().removeAttribute("user");
		}
		getRequest().setAttribute("msg", "show");
		return LOGIN;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}

	public void setPermissionsService(IPermissionsService permissionsService) {
		this.permissionsService = permissionsService;
	}

	public Map<String, Object> getInfos() {
		return infos;
	}

	public void setInfos(Map<String, Object> infos) {
		this.infos = infos;
	}

	public Permissions getPermissions() {
		return permissions;
	}

	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}
}
