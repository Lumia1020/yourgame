package com.lhq.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.lhq.core.MyUtils;
import com.lhq.core.Page;
import com.lhq.dao.IGetDataDao;
import com.lhq.po.Dept;
import com.lhq.po.Dispense;
import com.lhq.po.GetData;
import com.lhq.service.IGetDataService;

public class GetDataService implements IGetDataService {

	private IGetDataDao getDataDao;

	public Page getGetDataList(Page page) {
		String startDate = page.getParams().get("startDate");
		String endDate = page.getParams().get("endDate");
		String flag = page.getParams().get("flag");
		String print = page.getParams().get("print");
		Dept dept = (Dept) page.getObj();

		String hql = "select a.* from t_zh_getdata a inner join(select press_code from t_zh_dispense where station_name='"
				+ dept.getDeptName() + "'";
		String hql2 = "select a.section_name from t_zh_getdata a inner join(select press_code from t_zh_dispense where station_name='"
				+ dept.getDeptName() + "'";
		String hql3 = "select * from t_zh_dispense where station_name='" + dept.getDeptName() + "'";

		if (!MyUtils.isEmpty(flag)) {
			hql += (" and r_flag='" + flag + "'");
			hql2 += (" and r_flag='" + flag + "'");
			hql3 += (" and r_flag='" + flag + "'");
		}
		if (!MyUtils.isEmpty(startDate) && !MyUtils.isEmpty(endDate)) {
			hql += (" and r_time between '" + startDate + "' and '" + endDate + "'");
		}
		hql += ") b on a.press_code=b.press_code order by cast(a.section_name as int)";
		hql2 += ") b on a.press_code=b.press_code group by a.section_name order by cast(a.section_name as int)";
		try {
			if (page.getLimit() == 0) {
				page.setRoot(getDataDao.findByNativeSql(hql, GetData.class));
				if (print != null) {
					page.setRoot2(getDataDao.findByNativeSql(hql2, null));
					page.setRoot3(getDataDao.findByNativeSql(hql3, Dispense.class));
				}
			} else {
				page.setRoot(getDataDao.findByNativeSql(hql, page.getStart(), page.getLimit(), GetData.class));
				page.setTotalProperty(getDataDao.findByNativeSql(hql, GetData.class).size());
			}
			page.setSuccess(true);
		} catch (RuntimeException e) {
			page.setSuccess(false);
			e.printStackTrace();
		}
		return page;
	}

	public void setGetDataDao(IGetDataDao getDataDao) {
		this.getDataDao = getDataDao;
	}

	public boolean addGetData(GetData dispense) {
		dispense = getDataDao.saveGetData(dispense);
		return dispense.getUuid() == null ? false : true;
	}

	public boolean deleteGetData(GetData dispense) {
		Integer rows = getDataDao.executeUpdate("delete GetData where uuid = :uuid", new String[] { "uuid" },
				new String[] { dispense.getUuid() });
		return rows > 0 ? true : false;
	}

	public boolean updateGetData(GetData dispense) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		GetData di = getDataDao.findById(dispense.getUuid());
		MyUtils.copyProperties(di, dispense);
		return getDataDao.update(di);
	}

	public GetData findById(String dispenseId) {
		return getDataDao.findById(dispenseId);
	}

	public boolean updateFlags(Page page) {
		String uuids = page.getParams().get("uuids");
		String value = page.getParams().get("value");
		Integer rows = getDataDao.executeUpdate("update GetData set rflag=:value,rtime=:time where uuid in(" + uuids
				+ ")", new String[] { "value", "time" }, new String[] { "string", "date" }, new Object[] { value,
				new Date() });
		return rows > 0 ? true : false;
	}

	public boolean importGetData(String path) {
		Workbook workbook = null;
		File file = new File(path);
		try {
			workbook = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (workbook == null) {
			return false;
		}
		Sheet s = workbook.getSheet(0);
		for (int i = 1; i < s.getRows(); i++) {
			String pressCode = s.getCell(0, i).getContents();
			String pressName = s.getCell(1, i).getContents();
			String publishAddress = s.getCell(2, i).getContents();
			String pressInfo = s.getCell(3, i).getContents();
			String outNumber = s.getCell(4, i).getContents();
			String checkYearPrice = s.getCell(5, i).getContents();
			String checkPrice = s.getCell(6, i).getContents();
			String discountPrice = s.getCell(7, i).getContents();
			String receiveName = s.getCell(8, i).getContents();
			String receiveTell = s.getCell(9, i).getContents();
			String outDepart = s.getCell(10, i).getContents();
			String outAddress = s.getCell(11, i).getContents();
			String stationName = s.getCell(12, i).getContents();
			String sectionName = s.getCell(13, i).getContents();
			
			GetData gd = new GetData();
			gd.setPressCode(pressCode);
			gd.setPressName(pressName);
			gd.setPublishAddress(publishAddress);
			gd.setPressInfo(pressInfo);
			gd.setOutNumber(outNumber);
			gd.setCheckYearPrice(checkYearPrice);
			gd.setCheckPrice(checkPrice);
			gd.setDiscountPrice(discountPrice);
			gd.setReceiveName(receiveName);
			gd.setReceiveTell(receiveTell);
			gd.setOutDepart(outDepart);
			gd.setOutAddress(outAddress);
			gd.setStationName(stationName);
			gd.setSectionName(sectionName);
			
			getDataDao.saveGetData(gd);
		}
		return true;
	}

}
