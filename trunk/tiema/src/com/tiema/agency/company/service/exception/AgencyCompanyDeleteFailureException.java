package com.tiema.agency.company.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: AgencyCompanyDeleteFailureException
 * @Description: 删除中介公司数据错误时抛出此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 19, 2010 2:34:16 PM
 * 
 */
public class AgencyCompanyDeleteFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 1406553932516647778L;

	public AgencyCompanyDeleteFailureException() {
	}

	public AgencyCompanyDeleteFailureException(String message) {
		super(message);
	}

	public AgencyCompanyDeleteFailureException(Throwable cause) {
		super(cause);
	}

	public AgencyCompanyDeleteFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
