package com.tiema.agency.company.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: AgencyCompanySaveFailureException
 * @Description: 新增中介公司数据错误时抛此异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 19, 2010 2:35:32 PM
 * 
 */
public class AgencyCompanySaveFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= -1645423388307946573L;

	public AgencyCompanySaveFailureException() {
	}

	public AgencyCompanySaveFailureException(String message) {
		super(message);
	}

	public AgencyCompanySaveFailureException(Throwable cause) {
		super(cause);
	}

	public AgencyCompanySaveFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
