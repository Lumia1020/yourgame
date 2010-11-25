package com.tiema.agency.company.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: AgencyCompanyUpdateFailureException
 * @Description: 修改中介公司资料时发生的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 19, 2010 2:33:48 PM
 * 
 */
public class AgencyCompanyUpdateFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= -6642818867187367203L;

	public AgencyCompanyUpdateFailureException() {
	}

	public AgencyCompanyUpdateFailureException(String message) {
		super(message);
	}

	public AgencyCompanyUpdateFailureException(Throwable cause) {
		super(cause);
	}

	public AgencyCompanyUpdateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
