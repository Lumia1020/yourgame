package com.tiema.user.validator;

import javax.annotation.Resource;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.tiema.user.service.UserService;

/**
 * @ClassName: UserExistsValidator
 * @Description: 检查用户名的在数据库种是否存在的校验器，只有userName的时候就检查用户名， 如果还有id的话就检查不等于这个id但是等于这个userName的用户记录是否存在
 * @author yourgame <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-14 下午10:40:54
 * 
 */
public class UserExistsValidator extends FieldValidatorSupport {

	/** @Fields idName : id的映射名称 */
	private String		idName;

	/** @Fields roleService : 用户业务接口 */
	private UserService	userService;

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.validator.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object obj) throws ValidationException {

		Long userId = null;
		String userName = null;
		String fieldName = getFieldName();
		Object object = getFieldValue(fieldName, obj);

		if (object == null || "".equals(object)) {
			return;
		}
		userName = object.toString();

		object = getFieldValue(this.getIdName(), obj);
		if (object != null) {
			userId = Long.valueOf(object.toString());
		}

		if (userName == null || "".equals(userName)) {
			return;
		}

		if (userId != null) { //如果有要求判断id则是:检查不等于制定id却等于指定用户名的记录是否存在
			if (userService.existsNeIdAndEqUsername(userId, userName)) {
				addFieldError(fieldName, obj);
			}
		} else {
			if (userService.exists(userName)) {
				addFieldError(fieldName, obj);
			}
		}

	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

}
