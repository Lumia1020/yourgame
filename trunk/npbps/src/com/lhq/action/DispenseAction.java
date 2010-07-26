package com.lhq.action;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.lhq.core.BaseAction;
import com.lhq.core.MyUtils;
import com.lhq.core.Page;
import com.lhq.po.Dept;
import com.lhq.po.Dispense;
import com.lhq.service.IDispenseService;

@SuppressWarnings("serial")
public class DispenseAction extends BaseAction {

	private IDispenseService dispenseService;

	private Dispense dispense;

	private Page page;

	private boolean success;

	private Map<String, Object> infos = new HashMap<String, Object>();
	
	public String updateFlags(){
		this.success = dispenseService.updateFlags(page);
		return SUCCESS;
	}

	/**
	 * 更新分发
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public String updateDispense() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		this.success = dispenseService.updateDispense(dispense);
		return SUCCESS;
	}

	/**
	 * 删除分发
	 * 
	 * @return
	 */
	public String deleteDispense() {
		this.success = dispenseService.deleteDispense(dispense);
		return SUCCESS;
	}

	/**
	 * 添加分发
	 * 
	 * @return
	 */
	public String addDispense() {
		try {
			this.success = dispenseService.addDispense(dispense);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 列表分发
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public String showDispenses() throws ParseException {
		DetachedCriteria dc = DetachedCriteria.forClass(Dispense.class);
		String startDate = page.getParams().get("startDate");
		String endDate = page.getParams().get("endDate");
		String flag = page.getParams().get("flag");
		
		if (!MyUtils.isEmpty(startDate) && !MyUtils.isEmpty(endDate)) {
			dc.add(Restrictions.ge("rtime", MyUtils.string2Date(startDate,"yyyy-MM-dd")));
			dc.add(Restrictions.le("rtime", MyUtils.string2Date(endDate,"yyyy-MM-dd")));
		}
		if(!MyUtils.isEmpty(flag)){
			dc.add(Restrictions.eq("rflag", flag));
		}
		Dept dept = (Dept) getSession().getAttribute("dept");
		dc.add(Restrictions.eq("stationName", dept.getDeptName()));
		
		page.setResult(dc);
		page = dispenseService.getDispenseList(page);
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

	public Dispense getDispense() {
		return dispense;
	}

	public void setDispense(Dispense dispense) {
		this.dispense = dispense;
	}

	public void setDispenseService(IDispenseService dispenseService) {
		this.dispenseService = dispenseService;
	}

}
