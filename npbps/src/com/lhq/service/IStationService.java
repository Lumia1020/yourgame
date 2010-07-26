package com.lhq.service;

import java.util.List;

import com.lhq.core.Page;
import com.lhq.po.Station;

public interface IStationService {

	boolean deleteStation(Station station);

	boolean updateStation(Station station);

	boolean addStation(Station station);

	Page getStationList(Page page);

	List getAllList(String hql);

}
