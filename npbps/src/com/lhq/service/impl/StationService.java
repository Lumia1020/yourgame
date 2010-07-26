package com.lhq.service.impl;

import java.util.List;

import com.lhq.core.Page;
import com.lhq.dao.IStationDao;
import com.lhq.po.Station;
import com.lhq.service.IStationService;

public class StationService implements IStationService {

	private IStationDao stationDao;
	

	public Page getStationList(Page page) {
		try {
			if (page.getLimit() == 0) {
				page.setRoot(stationDao.findByCriteria(page.getResult()));
			} else {
				page.setRoot(stationDao.findByCriteria(page.getResult(), page.getStart(), page.getLimit()));
				page.setTotalProperty(stationDao.findByCount(page.getCount()));
			}
			page.setSuccess(true);
		} catch (RuntimeException e) {
			page.setSuccess(false);
			e.printStackTrace();
		}
		return page;
	}

	public void setStationDao(IStationDao stationDao) {
		this.stationDao = stationDao;
	}

	public boolean addStation(Station station) {
		List staId = stationDao.findByHQL("select max(stationId) from Station");
		List secId = stationDao.findByHQL("select max(sectionId) from Station");
		String stationId = (String) staId.get(0);
		String sectionId = (String) secId.get(0);
		
		if(sectionId == null){
			station.setSectionId("1");
		}else{
			sectionId = String.valueOf((Integer.valueOf(sectionId.trim()) + 1));
			station.setSectionId(sectionId);
		}
		
		if(stationId == null){
			station.setStationId("1");
		}else{
			stationId = String.valueOf((Integer.valueOf(stationId.trim()) + 1));
			station.setStationId(stationId);
		}

		station = stationDao.saveStation(station);
		return station.getUuid() == null ? false : true;
	}

	public boolean deleteStation(Station station) {
		Integer rows = stationDao.executeUpdate("delete Station where uuid = :uuid", new String[] { "uuid" },
				new String[] { station.getUuid() });
		return rows > 0 ? true : false;
	}

	public boolean updateStation(Station station) {
		Integer rows = stationDao.executeUpdate(
				"update Station set stationName=:stationName,sectionName=:sectionName where uuid=:uuid", new String[] {
						"stationName", "sectionName", "uuid" }, new String[] { station.getStationName(),
						station.getSectionName(), station.getUuid() });
		return rows > 0 ? true : false;
	}

	public List getAllList(String hql) {
//		return stationDao.findByNativeSql(hql, Station.class);
		return stationDao.findByHQL(hql);
	}

}
