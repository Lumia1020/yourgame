package com.tiema.golf.price.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: ClubPriceDeleteFailureException
 * @Description: 删除俱乐部价格出错时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-20 上午01:05:55
 * 
 */
public class ClubPriceDeleteFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 2249024077837450953L;

	public ClubPriceDeleteFailureException() {
	}

	public ClubPriceDeleteFailureException(String message) {
		super(message);
	}

	public ClubPriceDeleteFailureException(Throwable cause) {
		super(cause);
	}

	public ClubPriceDeleteFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
