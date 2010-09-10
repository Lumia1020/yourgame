/*
 * @(#)SystemLogInterceptor.java 2009-9-17
 *
 * Copyright YOURGAME. All rights reserved.
 */

package com.gvp.core;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import com.gvp.po.SystemLog;
import com.gvp.po.User;
import com.gvp.service.IPublicService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class SystemLogInterceptor extends AbstractInterceptor {

	private IPublicService publicService;

	public void setPublicService(IPublicService publicService) {
		this.publicService = publicService;
	}

	@Override
	public String intercept(ActionInvocation i) throws Exception {
		ActionContext ctx = i.getInvocationContext();
		String methodName = ctx.getName();
		String result = null;

		Class targetClass = i.getAction().getClass();
		Method method = targetClass.getMethod(methodName);

		if (method != null) {
			boolean hasAnnotation = method.isAnnotationPresent(Action.class);

			if (hasAnnotation) {
				Action annotation = method.getAnnotation(Action.class);
				if ("login".equals(methodName)) {
					result = i.invoke();
				}

				Map session = ctx.getSession();
				User user = (User) session.get("user");

				if (user != null) {
					try {
						SystemLog sysLog = new SystemLog();

						sysLog.setRecordTime(new Date());
						sysLog.setUserid(user.getUserid());
						sysLog.setUsername(user.getUsername());
						sysLog.setExeOperation(annotation.description());
						publicService.saveEntity(sysLog);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		if (null == result) {
			return i.invoke();
		}
		return result;
	}

}
