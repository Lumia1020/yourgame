package com.tiema.role.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiema.core.service.GenericService;
import com.tiema.role.entity.Role;

/**
 * @ClassName: RoleService
 * @Description: 角色业务接口
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 1, 2010 12:18:52 PM
 * 
 */
public interface RoleService extends GenericService<Role, Long> {

	/**
	 * @Title: exists
	 * @Description: 检查同名的角色名称是否存在,如果role对象中只包含了roleName的value则仅仅查询数据库中是否包含了该名称的角色,如果role.id也包含了值的话,就要加上<> role.id的判断
	 */
	public boolean exists(Role role);

	/**
	 * @Title: findRolePermissions
	 * @Description: 查找指定角色的所有权限信息
	 * @param
	 * @return List<Map>
	 * @throws
	 */
	public List<Map> findRolePermissions(Long roleId);

	/**
	 * @Title: addRolePermissions
	 * @Description: 为指定角色添加权限信息,执行方式是先从数据库把已经关联的所有角色全部删除,然后重新添加新的角色关联
	 * @param roleId 角色id
	 * @param permissionIds 权限id集合
	 */
	public boolean updateRolePermissions(Long roleId, String[] permissionIds);

}
