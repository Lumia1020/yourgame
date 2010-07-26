package com.lhq.action;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.core.BaseAction;
import com.lhq.core.EnhancedExample;
import com.lhq.core.Page;
import com.lhq.po.Station;
import com.lhq.service.IStationService;

@SuppressWarnings("serial")
public class StationAction extends BaseAction {
	private IStationService stationService;

	private Station station;

	private Page page;

	private boolean success;

	private Map<String, Object> infos = new HashMap<String, Object>();
	
	/**
	 * 更新站段
	 * 
	 * @return
	 */
	public String updateStation() {
		this.success = stationService.updateStation(station);
		return SUCCESS;
	}

	/**
	 * 删除站段
	 * 
	 * @return
	 */
	public String deleteStation() {
		this.success = stationService.deleteStation(station);
		return SUCCESS;
	}

	/**
	 * 添加站段
	 * 
	 * @return
	 */
	public String addStation() {
		try {
			this.success = stationService.addStation(station);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String showStationsArray(){
		Page root = new Page();
		root.setRoot(stationService.getAllList("SELECT DISTINCT stationId,stationName from Station"));
		getRequest().setAttribute("page", root);
		return SUCCESS;
	}

	/**
	 * 列表站段
	 * 
	 * @return
	 */
	public String showStations() {
		DetachedCriteria dc = DetachedCriteria.forClass(Station.class);
		String condition = page.getParams().get("condition");
		if (condition != null && !"".equals(condition)) {
			Station u = new Station();
			u.setStationName(condition);
			u.setSectionName(condition);
			dc.add(EnhancedExample.createDefault(u));
		}

		page.setResult(dc);
		page = stationService.getStationList(page);
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

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setStationService(IStationService stationService) {
		this.stationService = stationService;
	}

}
