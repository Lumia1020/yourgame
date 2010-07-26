package com.lhq.service.impl;

import java.util.List;

import com.lhq.core.Page;
import com.lhq.dao.IDeptDao;
import com.lhq.po.Dept;
import com.lhq.service.IDeptService;

public class DeptService implements IDeptService {

	private IDeptDao deptDao;

	public Page getDeptList(Page page) {
		try {
			if (page.getLimit() == 0) {
				page.setRoot(deptDao.findByCriteria(page.getResult()));
			} else {
				page.setRoot(deptDao.findByCriteria(page.getResult(), page.getStart(), page.getLimit()));
				page.setTotalProperty(deptDao.findByCount(page.getCount()));
			}
			page.setSuccess(true);
		} catch (RuntimeException e) {
			page.setSuccess(false);
			e.printStackTrace();
		}
		return page;
	}

	public void setDeptDao(IDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public boolean addDept(Dept dept) {
		List list = deptDao.findByHQL("select max(deptId) from Dept");
		List list2 = deptDao.findByHQL("select max(id) from Dept");
		String deptId = (String) list.get(0);
		String id = (String) list2.get(0);

		if(id == null){
			dept.setId("1");
		}else{
			id = String.valueOf((Integer.valueOf(id.trim()) + 1));
			dept.setId(id);
		}
		if (deptId == null) {
			dept.setDeptId("1");
		} else {
			deptId = String.valueOf((Integer.valueOf(deptId) + 1));
			dept.setDeptId(deptId);
		}
		dept = deptDao.saveDept(dept);
		return dept.getUuid() == null ? false : true;
	}

	public boolean deleteDept(Dept dept) {
		Integer rows = deptDao.executeUpdate("delete Dept where uuid = :uuid", new String[] { "uuid" },
				new String[] { dept.getUuid() });
		return rows > 0 ? true : false;
	}

	public boolean updateDept(Dept dept) {
		Integer rows = deptDao.executeUpdate("update Dept set deptName=:deptName where uuid=:uuid", new String[] {
				"deptName", "uuid" }, new String[] { dept.getDeptName(), dept.getUuid() });
		return rows > 0 ? true : false;
	}

	public Dept findById(String deptId) {
		List list = deptDao.findByHQL("from Dept where deptId='" + deptId + "'");
		if (list != null && !list.isEmpty()) {
			return (Dept) list.get(0);
		}
		return null;
	}
}
