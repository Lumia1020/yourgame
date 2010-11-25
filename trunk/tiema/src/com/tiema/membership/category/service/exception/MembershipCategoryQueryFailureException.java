package com.tiema.membership.category.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: MembershipCategoryQueryFailureException
 * @Description: 查询会籍种类信息错误时发生的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 4:44:18 PM
 * 
 */
public class MembershipCategoryQueryFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 8873813903436101755L;

	public MembershipCategoryQueryFailureException() {
	}

	public MembershipCategoryQueryFailureException(String message) {
		super(message);
	}

	public MembershipCategoryQueryFailureException(Throwable cause) {
		super(cause);
	}

	public MembershipCategoryQueryFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
