package com.tiema.user.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: UserDeleteFailureException
 * @Description: 删除用户发生错误异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 17, 2010 10:59:27 AM
 * 
 */
public class UserDeleteFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 8743870163632926650L;

	public UserDeleteFailureException() {
	}

	public UserDeleteFailureException(String message) {
		super(message);
	}

	public UserDeleteFailureException(Throwable cause) {
		super(cause);
	}

	public UserDeleteFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
