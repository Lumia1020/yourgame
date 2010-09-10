package com.gvp.core;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import com.gvp.po.SystemLog;
import com.gvp.po.User;

public class LogAspect {

	// private SystemLogService systemLogService;

	private Log logger = LogFactory.getLog(LogAspect.class);

	public Object doSystemLog(ProceedingJoinPoint point) throws Throwable {

		String methodName = point.getSignature().getName();

		// 目标方法不为空
		if (StringUtils.isNotEmpty(methodName)) {
			// set与get方法除外
			if (!(methodName.startsWith("set") || methodName.startsWith("get"))) {

				Class targetClass = point.getTarget().getClass();
				Method method = targetClass.getMethod(methodName);

				if (method != null) {

					boolean hasAnnotation = method.isAnnotationPresent(Action.class);

					if (hasAnnotation) {
						Action annotation = method.getAnnotation(Action.class);

						if (logger.isDebugEnabled()) {
							logger.debug("Action method:" + method.getName() + " Description:"
									+ annotation.description());
						}
						// 取到当前的操作用户
//						ThreadBean tb = new ThreadBean();
//
//						User user = (User) tb.getContext().getSession().getAttribute("user");
//						if (user != null) {
//							try {
//								SystemLog sysLog = new SystemLog();
//
//								sysLog.setCreatetime(new Date());
//								sysLog.setUserid(user.getUserid());
//								sysLog.setExeOperation(annotation.description());
//								// systemLogService.save(sysLog);
//							} catch (Exception ex) {
//								logger.error(ex.getMessage());
//							}
//						}

					}
				}

			}
		}
		return point.proceed();
	}

}
