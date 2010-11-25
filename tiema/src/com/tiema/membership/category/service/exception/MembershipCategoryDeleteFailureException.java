package com.tiema.membership.category.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: MembershipCategoryDeleteFailureException
 * @Description: 删除会籍种类失败时发生的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 4:04:01 PM
 * 
 */
public class MembershipCategoryDeleteFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 8912614022555345701L;

	public MembershipCategoryDeleteFailureException() {
	}

	public MembershipCategoryDeleteFailureException(String message) {
		super(message);
	}

	public MembershipCategoryDeleteFailureException(Throwable cause) {
		super(cause);
	}

	public MembershipCategoryDeleteFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
