package com.lhq.service;

import com.lhq.core.Page;
import com.lhq.po.Seat;

public interface ISeatService {

	Page getSeatList(Page page);

	boolean addSeat(Seat seat);

	boolean deleteSeat(Seat seat);

	boolean updateSeat(Seat seat);

}
