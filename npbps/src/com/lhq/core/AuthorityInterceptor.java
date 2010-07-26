/*
 * @(#)AuthorityInterceptor.java 2009-9-17
 *
 * Copyright YOURGAME. All rights reserved.
 */

package com.lhq.core;

import java.util.Map;

import com.lhq.po.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class AuthorityInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();
		User user = (User) session.get("user");
		if (user != null) {
			return invocation.invoke();
		}
		return "invalid_session";

	}

}
