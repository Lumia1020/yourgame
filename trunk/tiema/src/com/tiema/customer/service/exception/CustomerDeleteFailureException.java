package com.tiema.customer.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: CustomerDeleteFailureException
 * @Description: 删除客户资料错误时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 17, 2010 10:56:17 AM
 * 
 */
public class CustomerDeleteFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 3279622700574696612L;

	public CustomerDeleteFailureException() {
	}

	public CustomerDeleteFailureException(String message) {
		super(message);
	}

	public CustomerDeleteFailureException(Throwable cause) {
		super(cause);
	}

	public CustomerDeleteFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
