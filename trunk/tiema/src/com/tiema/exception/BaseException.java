package com.tiema.exception;

/**
 * @ClassName: BaseException
 * @Description: 异常基类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 10:49:44 AM
 *
 */
public class BaseException extends Exception {

	private static final long	serialVersionUID	= 4875410507372931237L;

	public BaseException() {
		super();
	}

	public BaseException(String msg) {
		super(msg);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
