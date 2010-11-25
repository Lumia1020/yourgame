package com.tiema.permission.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.core.orm.Page;
import com.tiema.permission.dao.PermissionDao;
import com.tiema.permission.entity.Permission;
import com.tiema.permission.service.PermissionService;

/**
 * @ClassName: PermissionServiceImpl
 * @Description: 权限业务层实现类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 9, 2010 10:44:25 AM
 * 
 */
@Component("permissionService")
public class PermissionServiceImpl implements PermissionService {

	private PermissionDao permissionDao;

	@Override
	public Permission add(Permission entity) {
		return permissionDao.save(entity);
	}

	@Override
	public void delete(Permission entity) {
		try {
			permissionDao.delete(entity);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			permissionDao.deleteById(id);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Permission findById(Long id) {
		return permissionDao.findById(id);
	}

	@Override
	public List<Permission> findEntities() {
		return permissionDao.findEntities();
	}
	
	@Override
	public List<Permission> findEntities(String hql, Object... params) {
		return permissionDao.findEntities(hql, params);
	}

	@Override
	public Page findPage(Page page) {
		return permissionDao.findByCriteria(page);
	}

	@Override
	public Permission findUnique(String hql, Object... values) {
		return permissionDao.findUnique(hql, values);
	}

	@Resource
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	@Override
	public Permission update(Permission entity) {
		return permissionDao.update(entity);
	}

	@Override
	public List<Map<String,Object>> findPermissionMap() {
		return permissionDao.findByNativeSql("SELECT t.id, t.name, t.parent_id FROM t_permission t");
	}

	@Override
	public List<Permission> findPermissionList() {
		return permissionDao.findByNativeSql("SELECT * FROM t_permission ", new Class[]{Permission.class});
	}


}
