package com.tiema.role.service.exception;

import java.util.List;

/**
 * @ClassName: RoleAndUserHaveAssociatedException
 * @Description: 角色与用户已经关联无法删除时的异常
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 2:34:55 PM
 *
 */
public class RoleAndUserHaveAssociatedException extends RoleDeleteFailureException {
	
	/** @Fields users : 关联的用户集合*/
	private List users;

	public List getUsers() {
		return users;
	}

	public void setUsers(List users) {
		this.users = users;
	}

	public RoleAndUserHaveAssociatedException() {
	}

	public RoleAndUserHaveAssociatedException(String message) {
		super(message);
	}

	public RoleAndUserHaveAssociatedException(Throwable cause) {
		super(cause);
	}

	public RoleAndUserHaveAssociatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoleAndUserHaveAssociatedException(String message, List users) {
		super(message);
		this.users = users;
	}

}

