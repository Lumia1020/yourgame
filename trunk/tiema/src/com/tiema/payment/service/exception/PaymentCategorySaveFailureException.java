package com.tiema.payment.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: PaymentCategorySaveFailureException
 * @Description: 新增付款方式种类错误时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 5:46:09 PM
 * 
 */
public class PaymentCategorySaveFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 3711600969850135337L;

	public PaymentCategorySaveFailureException() {
	}

	public PaymentCategorySaveFailureException(String message) {
		super(message);
	}

	public PaymentCategorySaveFailureException(Throwable cause) {
		super(cause);
	}

	public PaymentCategorySaveFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
