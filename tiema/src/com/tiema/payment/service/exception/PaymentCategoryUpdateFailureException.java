package com.tiema.payment.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: PaymentCategoryUpdateFailureException
 * @Description: 修改付款方式种类错误时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 5:46:29 PM
 * 
 */
public class PaymentCategoryUpdateFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= -5746333309538483866L;

	public PaymentCategoryUpdateFailureException() {
	}

	public PaymentCategoryUpdateFailureException(String message) {
		super(message);
	}

	public PaymentCategoryUpdateFailureException(Throwable cause) {
		super(cause);
	}

	public PaymentCategoryUpdateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
