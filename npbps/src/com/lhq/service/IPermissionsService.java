package com.lhq.service;

import com.lhq.core.Page;
import com.lhq.po.Permissions;

public interface IPermissionsService {

	Page getPermissionsList(Page rolePage);

	Permissions findById(String uuid);
	
	boolean updateFieldValue(Page page);

	boolean deletePermissions(Permissions permissions);

	boolean addRole(Permissions permissions);

}
