package com.lhq.service.impl;

import com.lhq.core.Page;
import com.lhq.dao.IPermissionsDao;
import com.lhq.po.Permissions;
import com.lhq.service.IPermissionsService;

public class PermissionsService implements IPermissionsService {

	private IPermissionsDao permissionsDao;

	public Page getPermissionsList(Page page) {
		try {
			if (page.getLimit() == 0) {
				page.setRoot(permissionsDao.findByCriteria(page.getResult()));
			} else {
				page.setRoot(permissionsDao.findByCriteria(page.getResult(), page.getStart(), page.getLimit()));
				page.setTotalProperty(permissionsDao.findByCount(page.getCount()));
			}
			page.setSuccess(true);
		} catch (RuntimeException e) {
			page.setSuccess(false);
			e.printStackTrace();
		}
		return page;
	}

	public void setPermissionsDao(IPermissionsDao permissionsDao) {
		this.permissionsDao = permissionsDao;
	}

	public Permissions findById(String pid) {
		return permissionsDao.findById( pid);
	}

	public boolean updateFieldValue(Page page) {
		String field = page.getParams().get("field");
		String value = page.getParams().get("value");
		String pid = page.getParams().get("pid");
		String hql = "update Permissions set " + field + "=:value where pid=:pid";
		Integer rows = permissionsDao.executeUpdate(hql, new String[] { "value", "pid" }, new String[] { value, pid });
		return rows > 0 ? true : false;
	}

	public boolean deletePermissions(Permissions permissions) {
		Integer rows = permissionsDao.executeUpdate("delete Permissions where pid = :pid", new String[] { "pid" },
				new String[] { permissions.getPid().toString() });
		return rows > 0 ? true : false;
	}

	public boolean addRole(Permissions permissions) {
		permissions = permissionsDao.savePermissions(permissions);
		return permissions.getPid() == null ? false:true;
	}

}
