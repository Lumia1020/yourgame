package com.tiema.customer.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: CustomerUpdateFailureException
 * @Description: 修改客户资料时发生的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 17, 2010 10:21:30 AM
 * 
 */
public class CustomerUpdateFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 8121483801525898731L;

	public CustomerUpdateFailureException() {
	}

	public CustomerUpdateFailureException(String message) {
		super(message);
	}

	public CustomerUpdateFailureException(Throwable cause) {
		super(cause);
	}

	public CustomerUpdateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
