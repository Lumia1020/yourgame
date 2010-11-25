package com.tiema.role.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: RoleSaveFailureException
 * @Description: 角色添加异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 11:20:02 AM
 *
 */
public class RoleSaveFailureException extends DataAccessFailureException {

	public RoleSaveFailureException() {
	}

	public RoleSaveFailureException(String message) {
		super(message);
	}

	public RoleSaveFailureException(Throwable cause) {
		super(cause);
	}

	public RoleSaveFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}

