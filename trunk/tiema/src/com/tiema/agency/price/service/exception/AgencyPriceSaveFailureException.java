package com.tiema.agency.price.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: AgencyPriceSaveFailureException
 * @Description: 新增中介公司价格错误抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-23 下午11:19:43
 * 
 */
public class AgencyPriceSaveFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 5939714366142681749L;

	public AgencyPriceSaveFailureException() {
	}

	public AgencyPriceSaveFailureException(String message) {
		super(message);
	}

	public AgencyPriceSaveFailureException(Throwable cause) {
		super(cause);
	}

	public AgencyPriceSaveFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
