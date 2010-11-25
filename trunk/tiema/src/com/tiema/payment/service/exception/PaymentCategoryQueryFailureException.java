package com.tiema.payment.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: PaymentCategoryQueryFailureException
 * @Description: 查询付款方式种类错误时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 5:45:51 PM
 * 
 */
public class PaymentCategoryQueryFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 4012238461634255770L;

	public PaymentCategoryQueryFailureException() {
	}

	public PaymentCategoryQueryFailureException(String message) {
		super(message);
	}

	public PaymentCategoryQueryFailureException(Throwable cause) {
		super(cause);
	}

	public PaymentCategoryQueryFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
