package com.tiema.membership.category.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

/**
 * @ClassName: MembershipCategoryUpdateFailureException
 * @Description: 更新会籍资料失败后发生的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 4:37:54 PM
 * 
 */
public class MembershipCategoryUpdateFailureException extends DataAccessFailureException {

	private static final long	serialVersionUID	= 5449495833125954888L;

	public MembershipCategoryUpdateFailureException() {
	}

	public MembershipCategoryUpdateFailureException(String message) {
		super(message);
	}

	public MembershipCategoryUpdateFailureException(Throwable cause) {
		super(cause);
	}

	public MembershipCategoryUpdateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
