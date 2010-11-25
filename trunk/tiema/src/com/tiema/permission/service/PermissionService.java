package com.tiema.permission.service;

import java.util.List;
import java.util.Map;

import com.tiema.core.service.GenericService;
import com.tiema.permission.entity.Permission;

/**
 * @ClassName: PermissionService
 * @Description: 权限业务接口
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 9, 2010 10:42:54 AM
 *
 */
public interface PermissionService extends GenericService<Permission, Long> {

	/**
	 * @Title: findAllPermissions
	 * @Description: 查找所有角色信息,包含了role_id,permission_id
	 * @return List<Map> 
	 */
	List<Map<String,Object>> findPermissionMap();

	/**
	 * @Title: findAllPermissions
	 * @Description: 根据hql查找权限集合
	 * @return List<Permission> 返回所有权限
	 */
	List<Permission> findPermissionList();


}

