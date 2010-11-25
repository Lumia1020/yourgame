package com.tiema.golf.price.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: ClubPriceSaveFailureException
 * @Description: 新增俱乐部价格出错抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-20 上午01:05:07
 * 
 */
public class ClubPriceSaveFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= -564818283519598512L;

	public ClubPriceSaveFailureException() {
	}

	public ClubPriceSaveFailureException(String message) {
		super(message);
	}

	public ClubPriceSaveFailureException(Throwable cause) {
		super(cause);
	}

	public ClubPriceSaveFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
