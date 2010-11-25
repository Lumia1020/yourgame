package com.tiema.seller.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: SellerDeleteFailureException
 * @Description: 删除销售员错误时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-19 上午12:06:57
 * 
 */
public class SellerDeleteFailureException extends DataAccessFailureException {


	/** @Fields serialVersionUID : TODO */
	private static final long	serialVersionUID	= -1594462479510218987L;

	public SellerDeleteFailureException() {
	}

	public SellerDeleteFailureException(String message) {
		super(message);
	}

	public SellerDeleteFailureException(Throwable cause) {
		super(cause);
	}

	public SellerDeleteFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
