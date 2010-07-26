package com.lhq.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.lhq.core.MyUtils;
import com.lhq.core.Page;
import com.lhq.dao.IDispenseDao;
import com.lhq.po.Dispense;
import com.lhq.service.IDispenseService;

public class DispenseService implements IDispenseService {

	private IDispenseDao dispenseDao;

	public Page getDispenseList(Page page) {
		try {
			if (page.getLimit() == 0) {
				page.setRoot(dispenseDao.findByCriteria(page.getResult()));
			} else {
				page.setRoot(dispenseDao.findByCriteria(page.getResult(), page.getStart(), page.getLimit()));
				page.setTotalProperty(dispenseDao.findByCount(page.getCount()));
			}
			page.setSuccess(true);
		} catch (RuntimeException e) {
			page.setSuccess(false);
			e.printStackTrace();
		}
		return page;
	}

	public void setDispenseDao(IDispenseDao dispenseDao) {
		this.dispenseDao = dispenseDao;
	}

	public boolean addDispense(Dispense dispense) {
		dispense = dispenseDao.saveDispense(dispense);
		return dispense.getUuid() == null ? false : true;
	}

	public boolean deleteDispense(Dispense dispense) {
		Integer rows = dispenseDao.executeUpdate("delete Dispense where uuid = :uuid", new String[] { "uuid" },
				new String[] { dispense.getUuid() });
		return rows > 0 ? true : false;
	}

	public boolean updateDispense(Dispense dispense) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Dispense di = dispenseDao.findById(dispense.getUuid());
		MyUtils.copyProperties(di, dispense);
		return dispenseDao.update(di);
	}

	public Dispense findById(String dispenseId) {
		return dispenseDao.findById(dispenseId);
	}

	public boolean updateFlags(Page page) {

		String uuids = page.getParams().get("uuids");
		String value = page.getParams().get("value");
		Integer rows = dispenseDao.executeUpdate("update Dispense set rflag=:value,rtime=:time where uuid in(" + uuids+ ")",
				new String[] { "value", "time" }, new String[] { "string", "date"}, new Object[] {
						value, new Date()});
		return rows > 0 ? true : false;
	}

}
