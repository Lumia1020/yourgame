package com.lhq.service;

import com.lhq.core.Page;
import com.lhq.po.Dept;

public interface IDeptService {

	Page getDeptList(Page deptPage);

	boolean addDept(Dept dept);

	boolean deleteDept(Dept dept);

	boolean updateDept(Dept dept);

	Dept findById(String deptId);

}
