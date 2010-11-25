package com.tiema.customer.service.exception;

/**
 * @ClassName: CustomerNumberExistsException
 * @Description: 客户编号已经存在时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 18, 2010 9:55:41 AM
 * 
 */
public class CustomerNumberExistsException extends Exception {

	private static final long	serialVersionUID	= -7142647090808631509L;

	public CustomerNumberExistsException() {
	}

	public CustomerNumberExistsException(String message) {
		super(message);
	}

	public CustomerNumberExistsException(Throwable cause) {
		super(cause);
	}

	public CustomerNumberExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
