package com.tiema.golf.price.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: ClubPriceQueryFailureException
 * @Description: 查询俱乐部价格出错时报此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-20 上午01:07:11
 * 
 */
public class ClubPriceQueryFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 5139736752469799435L;

	public ClubPriceQueryFailureException() {
	}

	public ClubPriceQueryFailureException(String message) {
		super(message);
	}

	public ClubPriceQueryFailureException(Throwable cause) {
		super(cause);
	}

	public ClubPriceQueryFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
