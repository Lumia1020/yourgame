package com.tiema.role.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tiema.core.action.BaseAction;
import com.tiema.core.dao.hibernate.core.EnhancedExample;
import com.tiema.core.orm.Page;
import com.tiema.permission.service.PermissionService;
import com.tiema.role.entity.Role;
import com.tiema.role.service.RoleService;
import com.tiema.role.service.exception.RoleAndUserHaveAssociatedException;

/**
 * @ClassName: RoleAction
 * @Description: 角色管理(增加新的角色,修改角色信息,删除角色,查询所有角色)
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 1, 2010 12:17:19 PM
 * 
 */
@Component
@Scope("prototype")
public class RoleAction extends BaseAction {

	private static final Logger	log					= LoggerFactory.getLogger(RoleAction.class);
	private static final long	serialVersionUID	= -5711550992447426410L;

	/** @Fields roleId : 角色id */
	private Long				roleId;

	/** @Fields role : 角色实体 */
	private Role				role;

	/** @Fields roles : 角色集合 */
	private List<Role>			roles;

	/** @Fields page : 分页数据 */
	private Page				page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean				success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>	jsons				= new HashMap<String, Object>();

	/** @Fields roleService : 角色业务接口 */
	private RoleService			roleService;

	/** @Fields permissionService : 权限业务接口 */
	private PermissionService	permissionService;

	/**
	 * @Title: add
	 * @Description: 新增一个角色
	 */
	public String add() throws Exception {
		log.debug("新增角色开始");
		try {
			roleService.add(role);
			setSuccess(true);
			log.debug("新增角色成功");
		} catch (RuntimeException e) {
			log.debug("新增角色失败", e);
		}
		return SUCCESS;
	}

	/**
	 * @Title: find
	 * @Description: 查找角色的分页信息
	 */
	@SuppressWarnings("unchecked")
	public String find() {
		DetachedCriteria dc = DetachedCriteria.forClass(Role.class);
		if (role != null) {
			dc.add(EnhancedExample.createDefaultAll(role, true));
		}
		getPage().setCriteria(dc);
		this.setPage(roleService.findPage(getPage()));
		return SUCCESS;
	}

	/**
	 * @Title: findAll
	 * @Description: 查找所有角色
	 */
	public String findAll() {
		setRoles(roleService.findEntities());
		return SUCCESS;
	}

	/**
	 * @Title: load
	 * @Description: 加载一个角色对象
	 */
	public String load() {
		try {
			setRole(roleService.findById(roleId));
			setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: delete
	 * @Description: 删除角色
	 */
	public String delete() {
		try {
			roleService.deleteById(roleId);
			setSuccess(true);
		} catch (RoleAndUserHaveAssociatedException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			jsons.put("users", e.getUsers());
		}
		return SUCCESS;
	}

	/**
	 * @Title: update
	 * @Description: 更新角色信息
	 */
	public String update() {
		try {
			setRole(roleService.update(role));
			setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: findRolePermission
	 * @Description: 查找角色以及角色的权限信息
	 */
	public String findRolePermission() {
		try {
			List<Map<String, Object>> allPermissionlist = permissionService.findPermissionMap();
			role = roleService.findById(roleId);
			List<Map> rolePermissionList = roleService.findRolePermissions(roleId);

			jsons.put("allPermissionList", allPermissionlist);
			jsons.put("rolePermissionList", rolePermissionList);
			jsons.put("role", role);
			jsons.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: addRolePermissions
	 * @Description: 为指定角色添加权限信息
	 */
	public String updateRolePermissions() {
		String[] permissionIds = getParameters().get("permissionIds");
		setSuccess(roleService.updateRolePermissions(roleId, permissionIds));
		return SUCCESS;
	}

	public Role getRole() {
		return role;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Page getPage() {
		return page;
	}

	@Resource
	public void setPage(Page page) {
		this.page = page;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, Object> getJsons() {
		return jsons;
	}

	public void setJsons(Map<String, Object> jsons) {
		this.jsons = jsons;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Resource
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

}
