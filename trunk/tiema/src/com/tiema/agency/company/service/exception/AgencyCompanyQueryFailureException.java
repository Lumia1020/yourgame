package com.tiema.agency.company.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: AgencyCompanyQueryFailureException
 * @Description: 查询中介公司数据错误抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 19, 2010 2:35:11 PM
 *
 */
public class AgencyCompanyQueryFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 8296083920384258353L;

	public AgencyCompanyQueryFailureException() {
	}

	public AgencyCompanyQueryFailureException(String message) {
		super(message);
	}

	public AgencyCompanyQueryFailureException(Throwable cause) {
		super(cause);
	}

	public AgencyCompanyQueryFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
