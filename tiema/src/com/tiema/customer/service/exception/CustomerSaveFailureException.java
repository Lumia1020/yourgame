package com.tiema.customer.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: CustomerSaveFailureException
 * @Description: 新增客户资料发生异常时抛出
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 17, 2010 10:06:34 AM
 * 
 */
public class CustomerSaveFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 6901156495233691668L;

	public CustomerSaveFailureException() {
	}

	public CustomerSaveFailureException(String message) {
		super(message);
	}

	public CustomerSaveFailureException(Throwable cause) {
		super(cause);
	}

	public CustomerSaveFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
