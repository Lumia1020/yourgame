package com.tiema.payment.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: PaymentCategoryDeleteFailureException
 * @Description: 删除付款方式种类错误时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 5:45:29 PM
 * 
 */
public class PaymentCategoryDeleteFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= -3035990728607716089L;

	public PaymentCategoryDeleteFailureException() {
	}

	public PaymentCategoryDeleteFailureException(String message) {
		super(message);
	}

	public PaymentCategoryDeleteFailureException(Throwable cause) {
		super(cause);
	}

	public PaymentCategoryDeleteFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
