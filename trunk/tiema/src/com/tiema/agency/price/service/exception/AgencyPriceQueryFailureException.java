package com.tiema.agency.price.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: AgencyPriceQueryFailureException
 * @Description: 查询中介公司价格错误时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-23 下午11:20:18
 * 
 */
public class AgencyPriceQueryFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= -7700458587026730884L;

	public AgencyPriceQueryFailureException() {
	}

	public AgencyPriceQueryFailureException(String message) {
		super(message);
	}

	public AgencyPriceQueryFailureException(Throwable cause) {
		super(cause);
	}

	public AgencyPriceQueryFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
