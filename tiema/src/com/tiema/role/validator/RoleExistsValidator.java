package com.tiema.role.validator;

import javax.annotation.Resource;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.tiema.role.entity.Role;
import com.tiema.role.service.RoleService;

/**
 * @ClassName: RoleNameExistsValidator
 * @Description: 检查角色名的在数据库种是否存在的校验器，只有roleName的时候就检查角色名， 如果还有id的话就检查不等于这个id但是等于这个roleName的角色记录是否存在
 * @author yourgame <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-14 下午10:40:54
 * 
 */
public class RoleExistsValidator extends FieldValidatorSupport {

	/** @Fields idName : id的映射名称 */
	private String		idName;

	/** @Fields roleService : 角色业务接口 */
	private RoleService	roleService;

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.validator.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object obj) throws ValidationException {

		Long roleId = null;
		String roleName = null;
		String fieldName = getFieldName();
		Object object = getFieldValue(fieldName, obj);

		if (object == null || "".equals(object)) {
			return;
		}
		roleName = object.toString();

		object = getFieldValue(this.getIdName(), obj);
		if (object != null) {
			roleId = Long.valueOf(object.toString());
		}

		if (roleName == null || "".equals(roleName)) {
			return;
		}
		Role role = new Role(roleId, roleName);

		if (roleService.exists(role)) {
			addFieldError(fieldName, obj);
		}

	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

}
