package com.tiema.agency.price.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
* @ClassName: AgencyPriceUpdateFailureException
* @Description: 修改中介公司价格出错时抛此异常
* @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
* @date 2010-11-23 下午11:19:05
*
*/
public class AgencyPriceUpdateFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 449680484511582781L;

	public AgencyPriceUpdateFailureException() {
	}

	public AgencyPriceUpdateFailureException(String message) {
		super(message);
	}

	public AgencyPriceUpdateFailureException(Throwable cause) {
		super(cause);
	}

	public AgencyPriceUpdateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
