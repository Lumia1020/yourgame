package com.tiema.role.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.core.orm.Page;
import com.tiema.permission.entity.Permission;
import com.tiema.role.dao.RoleDao;
import com.tiema.role.entity.Role;
import com.tiema.role.service.RoleService;
import com.tiema.role.service.exception.RoleAndUserHaveAssociatedException;
import com.tiema.role.service.exception.RoleDeleteFailureException;
import com.tiema.role.service.exception.RoleSaveFailureException;
import com.tiema.user.dao.UserDao;

/**
 * @ClassName: RoleServiceImpl
 * @Description: 角色业务逻辑实现类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 1, 2010 2:42:16 PM
 * 
 */
@Component("roleService")
public class RoleServiceImpl implements RoleService {

	private RoleDao	roleDao;

	private UserDao	userDao;

	@Override
	public Role add(Role entity) {
		try {
			return roleDao.save(entity);
		} catch (RuntimeException e) {
			throw new RoleSaveFailureException("角色保存失败!", e);
		}
	}

	@Override
	public void delete(Role entity) {
		try {
			roleDao.delete(entity);
		} catch (Exception e) {
			throw new RoleDeleteFailureException("角色删除失败!", e);
		}
	}

	/*
	 * (non-Javadoc) 
	 * @Title:deleteById 
	 * @Description:根据角色id删除角色,同时hibernate会自动的删除角色和权限关联表中的数据 
	 * @param id 角色id 
	 * 
	 * @see com.tiema.core.service.GenericService#deleteById(java.io.Serializable)
	 */
	@Override
	public void deleteById(Long id) {
		if (null == id) {
			throw new IllegalArgumentException("要删除的角色id不能为空!");
		}
		
		String sql = "SELECT wmsys.wm_concat(t.CHINESENAME) CHINESENAME from T_USER t,T_USER_ROLE_RELATION rr where t.ID = rr.USER_ID and rr.ROLE_ID = ?";
		List<Map<String,Object>> users = userDao.findByNativeSql(sql, id);
		if(users.get(0).entrySet().iterator().next().getValue() != null){
			
			throw new RoleAndUserHaveAssociatedException("角色删除失败!当前删除的角色已经关联到某些用户，需要取消所有用户与该角色的所有关联才可以删除该角色！",users);
		}
		roleDao.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(Role role) {
		String hql = "from Role r where r.roleName = ?";
		List params = new ArrayList();
		params.add(role.getRoleName());
		if (role.getId() != null) {
			hql += " and r.id <> ?";
			params.add(role.getId());
		}
		List<Role> list = roleDao.findEntities(hql, params.toArray());
		return list.size() > 0;
	}

	@Override
	public Role findById(Long id) {
		return roleDao.findById(id);
	}

	@Override
	public List<Role> findEntities() {
		return roleDao.findEntities();
	}

	@Override
	public List<Role> findEntities(String hql, Object... values) {
		return roleDao.findEntities(hql, values);
	}

	@Override
	public Page findPage(Page page) {
		return roleDao.findByCriteria(page);
	}

	@Override
	public List findRolePermissions(Long roleId) {
		return roleDao.findByNativeSql("SELECT t.permission_id FROM t_role_permission_relation t,t_permission p WHERE t.permission_id =  p.id AND t.role_id = ?", roleId);
	}

	@Override
	public Role findUnique(String hql, Object... values) {
		return roleDao.findUnique(hql, values);
	}

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Role update(Role entity) {
		return roleDao.update(entity);
	}

	/*
	 * (non-Javadoc) @Title:updateRolePermissions
	 * @Description:修改角色所关联的权限记录,通过替换角色对象中权限集合对象,hibernate会自动先删除角色和权限的关联记录,然后自动重新插入新的关联记录 @param roleId 角色id @param
	 * permissionIds 通过前台传进来的新的权限关联的权限id集合 @return 更新是否成功
	 * 
	 * @see com.tiema.role.service.RoleService#updateRolePermissions(java.lang.Long, java.lang.String[])
	 */
	@Override
	public boolean updateRolePermissions(Long roleId, String[] permissionIds) {
		Role role = roleDao.findById(roleId);
		Set<Permission> sets = new HashSet<Permission>();
		if (permissionIds != null) {
			for (String id : permissionIds) {
				Permission p = new Permission(Long.parseLong(id));
				sets.add(p);
			}
		}
		role.setPermissions(sets);
		try {
			roleDao.update(role);
			return true;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return false;
	}

}
