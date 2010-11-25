package com.tiema.golf.price.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: ClubPriceUpdateFailureException
 * @Description: 修改俱乐部价格出错时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-20 上午01:07:50
 * 
 */
public class ClubPriceUpdateFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 8658339097208171768L;

	public ClubPriceUpdateFailureException() {
	}

	public ClubPriceUpdateFailureException(String message) {
		super(message);
	}

	public ClubPriceUpdateFailureException(Throwable cause) {
		super(cause);
	}

	public ClubPriceUpdateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
