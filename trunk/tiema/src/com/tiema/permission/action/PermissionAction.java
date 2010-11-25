package com.tiema.permission.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tiema.core.action.BaseAction;
import com.tiema.core.orm.Page;
import com.tiema.permission.entity.Permission;
import com.tiema.permission.service.PermissionService;

/**
 * @ClassName: PermissionAction
 * @Description: 权限管理,对权限进行维护
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 11, 2010 1:32:56 AM
 */
@Component
@Scope("prototype")
public class PermissionAction extends BaseAction {

	private static final Logger	log					= LoggerFactory.getLogger(PermissionAction.class);
	private static final long	serialVersionUID	= -9203469190986627347L;

	/** @Fields permissionId : 权限id */
	private Long				permissionId;

	/** @Fields permission : 角色实体对象 */
	private Permission			permission;

	/** @Fields permissions : 角色集合 */
	private List<Permission>	permissions;

	/** @Fields page : 分页对象 */
	private Page				page;

	/** @Fields success : 表示某个操作的结果 */
	private Boolean				success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>	jsons;

	private PermissionService	permissionService;

	public PermissionAction() {
	}

	public String findAll() {
		log.info("查找所有权限集合");
		setPermissions(permissionService.findPermissionList());
		return SUCCESS;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Map<String, Object> getJsons() {
		return jsons;
	}

	public void setJsons(Map<String, Object> jsons) {
		this.jsons = jsons;
	}

	@Resource
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

}
