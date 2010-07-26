package com.lhq.po;

import java.io.Serializable;

/**
 * 2、 部门表
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Dept implements Serializable {
	private String uuid;

	private String id;

	private String deptId;

	private String deptName;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getId() {
		return id;
	}

	public void setId(String deid) {
		this.id = deid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


}
