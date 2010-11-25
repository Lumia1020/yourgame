package com.tiema.seller.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: SellerSaveFailureException
 * @Description: 新增销售员数据时发生的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-19 上午12:08:57
 * 
 */
public class SellerSaveFailureException extends DataAccessFailureException {


	/** @Fields serialVersionUID : TODO */
	private static final long	serialVersionUID	= 1789692761567880763L;

	public SellerSaveFailureException() {
	}

	public SellerSaveFailureException(String message) {
		super(message);
	}

	public SellerSaveFailureException(Throwable cause) {
		super(cause);
	}

	public SellerSaveFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
