package com.tiema.role.service.exception;

import com.tiema.core.service.exception.DataAccessFailureException;

public class RoleDeleteFailureException extends DataAccessFailureException {

	public RoleDeleteFailureException() {
	}

	public RoleDeleteFailureException(String message) {
		super(message);
	}

	public RoleDeleteFailureException(Throwable cause) {
		super(cause);
	}

	public RoleDeleteFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}

