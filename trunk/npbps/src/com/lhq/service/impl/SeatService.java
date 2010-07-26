package com.lhq.service.impl;

import java.util.List;

import com.lhq.core.Page;
import com.lhq.dao.ISeatDao;
import com.lhq.po.Seat;
import com.lhq.service.ISeatService;

public class SeatService implements ISeatService {

	private ISeatDao seatDao;

	public Page getSeatList(Page page) {
		try {
			if (page.getLimit() == 0) {
				page.setRoot(seatDao.findByCriteria(page.getResult()));
			} else {
				page.setRoot(seatDao.findByCriteria(page.getResult(), page.getStart(), page.getLimit()));
				page.setTotalProperty(seatDao.findByCount(page.getCount()));
			}
			page.setSuccess(true);
		} catch (RuntimeException e) {
			page.setSuccess(false);
			e.printStackTrace();
		}
		return page;
	}

	public void setSeatDao(ISeatDao seatDao) {
		this.seatDao = seatDao;
	}

	public boolean addSeat(Seat seat) {
		List list = seatDao.findByHQL("select max(seatId) from Seat");
		String seatId = (String) list.get(0);
		seatId = seatId == null ? "1":seatId;
		if(seatId == null){
			seat.setSeatId("1");
		}else{
			seatId = String.valueOf(Integer.valueOf(seatId.trim()) + 1);
			seat.setSeatId(seatId);
		}
		seat = seatDao.saveSeat(seat);
		return seat.getUuid() == null ? false : true;
	}

	public boolean deleteSeat(Seat seat) {
		Integer rows = seatDao.executeUpdate("delete Seat where uuid = :uuid", new String[] { "uuid" },
				new String[] { seat.getUuid() });
		return rows > 0 ? true : false;
	}

	public boolean updateSeat(Seat seat) {
		Integer rows = seatDao
				.executeUpdate("update Seat set seatName=:seatName,stationId=:stationId where uuid=:uuid",
						new String[] { "seatName", "stationId", "uuid" }, new String[] { seat.getSeatName(),
								seat.getStationId(), seat.getUuid() });
		return rows > 0 ? true : false;
	}
}
