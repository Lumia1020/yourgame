package com.lhq.action;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.core.BaseAction;
import com.lhq.core.EnhancedExample;
import com.lhq.core.Page;
import com.lhq.po.Dept;
import com.lhq.service.IDeptService;

@SuppressWarnings("serial")
public class DeptAction extends BaseAction {

	private IDeptService deptService;
	
	private Dept dept;

	private Page page;

	private boolean success;

	private Map<String, Object> infos = new HashMap<String, Object>();

	
	/**
	 * 更新部门
	 * @return
	 */
	public String updateDept() {
		this.success = deptService.updateDept(dept);
		return SUCCESS;
	}
	
	
	/**
	 * 删除部门
	 * @return
	 */
	public String deleteDept() {
		this.success = deptService.deleteDept(dept);
		return SUCCESS;
	}
	
	
	/**
	 * 添加部门
	 * @return
	 */
	public String addDept(){
		try {
			this.success = deptService.addDept(dept);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 列表部门
	 * @return
	 */
	public String showDepts() {
		DetachedCriteria dc = DetachedCriteria.forClass(Dept.class);
		String condition = page.getParams().get("condition");
		if (condition != null && !"".equals(condition)) {
			Dept u = new Dept();
			u.setDeptName(condition);
			dc.add(EnhancedExample.createDefault(u));
		}

		page.setResult(dc);
		page = deptService.getDeptList(page);
		page.initPageInfo();
		getRequest().setAttribute("page", page);
		return SUCCESS;
	}

	public Map<String, Object> getInfos() {
		return infos;
	}

	public void setInfos(Map<String, Object> infos) {
		this.infos = infos;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}
}
