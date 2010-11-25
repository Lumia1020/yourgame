package com.tiema.core.action;

import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName: BaseAction
 * @Description: Action基类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * 
 * @date Oct 25, 2010 10:40:43 PM
 * 
 */
public class BaseAction extends ActionSupport implements ParameterAware, RequestAware, SessionAware, ApplicationAware {

	private static final long serialVersionUID = 3052287642335482442L;


	private Map<String, String[]> parameters;
	private Map<String, Object> request;
	private Map<String, Object> session;
	private Map<String, Object> application;


	public Map<String, Object> getApplication() {
		return application;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	public Map<String, String[]> getParameters() {
		return parameters;
	}

}
