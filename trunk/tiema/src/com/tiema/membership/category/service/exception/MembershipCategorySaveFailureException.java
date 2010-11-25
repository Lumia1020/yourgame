package com.tiema.membership.category.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: MembershipCategorySaveFailureException
 * @Description: 保存会籍种类失败是发生的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 4:03:12 PM
 *
 */
public class MembershipCategorySaveFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= -501466963998820465L;

	public MembershipCategorySaveFailureException() {
	}

	public MembershipCategorySaveFailureException(String message) {
		super(message);
	}

	public MembershipCategorySaveFailureException(Throwable cause) {
		super(cause);
	}

	public MembershipCategorySaveFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
