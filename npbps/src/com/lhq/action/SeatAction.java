package com.lhq.action;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.core.BaseAction;
import com.lhq.core.EnhancedExample;
import com.lhq.core.Page;
import com.lhq.po.Seat;
import com.lhq.po.Station;
import com.lhq.service.ISeatService;
import com.lhq.service.IStationService;

@SuppressWarnings("serial")
public class SeatAction extends BaseAction {

	private ISeatService seatService;
	
	private IStationService stationService;
	
	private Seat seat;

	private Page page;

	private boolean success;

	private Map<String, Object> infos = new HashMap<String, Object>();
	
	
	/**
	 * 更新台席
	 * 
	 * @return
	 */
	public String updateSeat() {
		this.success = seatService.updateSeat(seat);
		return SUCCESS;
	}

	/**
	 * 删除台席
	 * 
	 * @return
	 */
	public String deleteSeat() {
		this.success = seatService.deleteSeat(seat);
		return SUCCESS;
	}

	/**
	 * 添加台席
	 * 
	 * @return
	 */
	public String addSeat() {
		try {
			this.success = seatService.addSeat(seat);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 列表台席
	 * 
	 * @return
	 */
	public String showSeats() {
		DetachedCriteria dc = DetachedCriteria.forClass(Seat.class);
		String condition = page.getParams().get("condition");
		if (condition != null && !"".equals(condition)) {
			Seat u = new Seat();
			u.setSeatName(condition);
			dc.add(EnhancedExample.createDefault(u));
		}

		page.setResult(dc);
		page = seatService.getSeatList(page);
		page.initPageInfo();
		getRequest().setAttribute("page", page);
		
		
		Page stationPage = new Page();
		stationPage.setRoot(stationService.getAllList("SELECT DISTINCT stationId,stationName from Station"));
		getRequest().setAttribute("stationPage", stationPage);
		
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

	public void setSeatService(ISeatService seatService) {
		this.seatService = seatService;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public void setStationService(IStationService stationService) {
		this.stationService = stationService;
	}

}
