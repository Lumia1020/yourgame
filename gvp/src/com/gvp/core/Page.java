package com.gvp.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
import org.hibernate.criterion.DetachedCriteria;

public class Page {
	/** 总记录数 */
	private int totalProperty;

	/** 分页结果 */
	private List root;

	/** 开始页码 */
	private int start;

	/** 每页多少 */
	private int limit;

	/** 成功与否 */
	private boolean success;

	private Object obj;

	private Map<String, String> params = new HashMap<String, String>();

	private Map params2 = new HashMap();

	/** 查询条件 */
	private List conditions;

	private DetachedCriteria result;

	private DetachedCriteria count;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
		this.params.put("limit", String.valueOf(limit));
	}

	public List getRoot() {
		return root;
	}

	public void setRoot(List root) {
		this.root = root;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
		this.params.put("start", String.valueOf(this.start));
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(int totalProperty) {
		this.totalProperty = totalProperty;
	}

	public List getConditions() {
		return conditions;
	}

	public void setConditions(List conditions) {
		this.conditions = conditions;
	}

	public DetachedCriteria getCount() {
		return count;
	}

	public void setCount(DetachedCriteria count) {
		this.count = count;
	}

	public DetachedCriteria getResult() {
		return result;
	}

	public void setResult(DetachedCriteria result) {
		this.count = (DetachedCriteria) SerializationUtils.clone(result);
		this.result = result;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Map getParams2() {
		return params2;
	}

	public void setParams2(Map params2) {
		this.params2 = params2;
	}

}
