package com.tiema.seller.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: SellerQueryFailureException
 * @Description: 查询销售员数据时发生的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-19 上午12:08:20
 * 
 */
public class SellerQueryFailureException extends DataAccessFailureException {


	/** @Fields serialVersionUID : TODO */
	private static final long	serialVersionUID	= 2836768787634273022L;

	public SellerQueryFailureException() {
	}

	public SellerQueryFailureException(String message) {
		super(message);
	}

	public SellerQueryFailureException(Throwable cause) {
		super(cause);
	}

	public SellerQueryFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
