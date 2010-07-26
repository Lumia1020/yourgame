package com.lhq.action;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.core.BaseAction;
import com.lhq.core.EnhancedExample;
import com.lhq.core.Page;
import com.lhq.po.Grid;
import com.lhq.po.Seat;
import com.lhq.po.Station;
import com.lhq.service.IGridService;
import com.lhq.service.ISeatService;
import com.lhq.service.IStationService;

@SuppressWarnings("serial")
public class GridAction extends BaseAction {

	private IGridService gridService;
	
	private ISeatService seatService;
	
	private IStationService stationService;

	private Grid grid;

	private Page page;

	private boolean success;

	private Map<String, Object> infos = new HashMap<String, Object>();
	
	/**
	 * 添加格口页面
	 * @return
	 */
	public String addGridPage(){
		Page stationPage = new Page();
		stationPage.setRoot(stationService.getAllList("SELECT DISTINCT stationId,stationName from Station"));
		getRequest().setAttribute("stationPage", stationPage);
		
		Page sectionPage = new Page();
		sectionPage.setRoot(stationService.getAllList("SELECT DISTINCT sectionId,sectionName from Station order by cast(sectionName as int)"));
		getRequest().setAttribute("sectionPage", sectionPage);
		
		DetachedCriteria dc = DetachedCriteria.forClass(Seat.class);
		Page seatPage = new Page();
		seatPage.setResult(dc);
		seatPage = seatService.getSeatList(seatPage);
		getRequest().setAttribute("seatPage", seatPage);
		return SUCCESS;
	}

	/**
	 * 更新格口
	 * 
	 * @return
	 */
	public String updateGrid() {
		this.success = gridService.updateGrid(grid);
		return SUCCESS;
	}

	/**
	 * 删除格口
	 * 
	 * @return
	 */
	public String deleteGrid() {
		this.success = gridService.deleteGrid(grid);
		return SUCCESS;
	}

	/**
	 * 添加格口
	 * 
	 * @return
	 */
	public String addGrid() {
		try {
			this.success = gridService.addGrid(grid);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 列表格口
	 * 
	 * @return
	 */
	public String showGrids() {
		DetachedCriteria dc = DetachedCriteria.forClass(Grid.class);
		String condition = page.getParams().get("condition");
		if (condition != null && !"".equals(condition)) {
			Grid u = new Grid();
			u.setArea(condition);
			dc.add(EnhancedExample.createDefault(u));
		}

		page.setResult(dc);
		page = gridService.getGridList(page);
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

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public void setGridService(IGridService gridService) {
		this.gridService = gridService;
	}

	public void setStationService(IStationService stationService) {
		this.stationService = stationService;
	}

	public void setSeatService(ISeatService seatService) {
		this.seatService = seatService;
	}
}
