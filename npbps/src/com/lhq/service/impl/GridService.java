package com.lhq.service.impl;

import java.util.List;

import com.lhq.core.Page;
import com.lhq.dao.IGridDao;
import com.lhq.po.Grid;
import com.lhq.service.IGridService;

public class GridService implements IGridService {

	private IGridDao gridDao;

	public Page getGridList(Page page) {
		try {
			if (page.getLimit() == 0) {
				page.setRoot(gridDao.findByCriteria(page.getResult()));
			} else {
				page.setRoot(gridDao.findByCriteria(page.getResult(), page.getStart(), page.getLimit()));
				page.setTotalProperty(gridDao.findByCount(page.getCount()));
			}
			page.setSuccess(true);
		} catch (RuntimeException e) {
			page.setSuccess(false);
			e.printStackTrace();
		}
		return page;
	}

	public void setGridDao(IGridDao gridDao) {
		this.gridDao = gridDao;
	}

	public boolean addGrid(Grid grid) {
		List list = gridDao.findByHQL("select max(id) from Grid");
		String id = (String) list.get(0);
		if(id == null){
			grid.setId("1");
		}else{
			id = String.valueOf((Integer.valueOf(id.trim()) + 1));
			grid.setId(id);
		}
		
		grid = gridDao.saveGrid(grid);
		return grid.getUuid() == null ? false : true;
	}

	public boolean deleteGrid(Grid grid) {
		Integer rows = gridDao.executeUpdate("delete Grid where uuid = :uuid", new String[] { "uuid" },
				new String[] { grid.getUuid() });
		return rows > 0 ? true : false;
	}

	public boolean updateGrid(Grid grid) {
		Integer rows = gridDao.executeUpdate(
				"update Grid set stationId=:stationId,sectionId=:sectionId,area=:area,seatId=:seatId where uuid=:uuid",
				new String[] { "stationId", "sectionId", "area", "seatId", "uuid" }, new String[] {
						grid.getStationId(), grid.getSectionId(), grid.getArea(), grid.getSeatId(), grid.getUuid() });
		return rows > 0 ? true : false;
	}

}
