package com.lhq.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

	private String uuid;

	/** 帐号 */
	private String userId;

	/** 密码 */
	private String password;

	/** 用户姓名 */
	private String userName;

	/** 部门id */
	private String deptId;
	
	private String seatId;

	/** 权限id */
	private String pid;

	private String role;// 1、 当Depart_Name为中心局时：1代表接收；2代表分发2、

	// 当Depart_Name为其它时：全部使用1

	public User(String userId2) {
		this.userId = userId2;
	}

	public User() {
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userid) {
		this.userId = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String id) {
		this.uuid = id;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

}
