package com.tiema.golf.club.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: GolfClubSaveFailureException
 * @Description: 新增客户资料发生异常时抛出
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 17, 2010 10:06:34 AM
 * 
 */
public class GolfClubSaveFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 6901156495233691668L;

	public GolfClubSaveFailureException() {
	}

	public GolfClubSaveFailureException(String message) {
		super(message);
	}

	public GolfClubSaveFailureException(Throwable cause) {
		super(cause);
	}

	public GolfClubSaveFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
