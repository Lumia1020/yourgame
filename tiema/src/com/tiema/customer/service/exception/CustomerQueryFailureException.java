package com.tiema.customer.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: CustomerQueryFailureException
 * @Description: 查询客户资料时发生的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 17, 2010 10:20:05 AM
 * 
 */
public class CustomerQueryFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 2718750901907536364L;

	public CustomerQueryFailureException() {
	}

	public CustomerQueryFailureException(String message) {
		super(message);
	}

	public CustomerQueryFailureException(Throwable cause) {
		super(cause);
	}

	public CustomerQueryFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
