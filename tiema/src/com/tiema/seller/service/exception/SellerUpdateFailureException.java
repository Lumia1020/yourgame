package com.tiema.seller.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
* @ClassName: SellerUpdateFailureException
* @Description: 修改销售员数据发生异常
* @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
* @date 2010-11-19 上午12:07:51
*
*/
public class SellerUpdateFailureException extends DataAccessFailureException {


	/** @Fields serialVersionUID : TODO */
	private static final long	serialVersionUID	= 7920944598486767681L;

	public SellerUpdateFailureException() {
	}

	public SellerUpdateFailureException(String message) {
		super(message);
	}

	public SellerUpdateFailureException(Throwable cause) {
		super(cause);
	}

	public SellerUpdateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
