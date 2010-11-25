package com.tiema.core.service.exception;

/**
 * @ClassName: DataAccessFailureException
 * @Description: 数据访问失败异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 11:11:33 AM
 *
 */
public class DataAccessFailureException extends RuntimeException {

	public DataAccessFailureException() {
		super();
	}

	public DataAccessFailureException(String message) {
		super(message);
	}

	public DataAccessFailureException(Throwable cause) {
		super(cause);
	}

	public DataAccessFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}

