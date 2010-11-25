package com.tiema.agency.price.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: AgencyPriceDeleteFailureException
 * @Description: 删除中介公司价格错误时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-23 下午11:20:59
 * 
 */
public class AgencyPriceDeleteFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 7676576074195503225L;

	public AgencyPriceDeleteFailureException() {
	}

	public AgencyPriceDeleteFailureException(String message) {
		super(message);
	}

	public AgencyPriceDeleteFailureException(Throwable cause) {
		super(cause);
	}

	public AgencyPriceDeleteFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
