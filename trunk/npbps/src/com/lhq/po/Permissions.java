package com.lhq.po;

import java.io.Serializable;

/**
 * 角色（权限）
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Permissions  implements Serializable{

	/** 角色（权限）id */
	private String pid;

	/** 台席 */
	private String seat;

	/** 格口 */
	private String grid;

	/** 站段 */
	private String station;

	/** 用户管理 */
	private String user;

	/** *部门管理 */
	private String dept;

	/** 角色管理 */
	private String role;

	/** 投递清单打印 */
	private String verification;

	/** *投递接收 */
	private String delivery;

	
	private String roleName;

	/** 如果为空，代表是角色，如果有关联id则表示是用户的权限 */
	private String parentId;


	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String uuid) {
		this.pid = uuid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? roleName : roleName.trim();
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery == null ? delivery : delivery.trim();
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept == null ? dept : dept.trim();
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid == null ? grid : grid.trim();
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat == null ? seat : seat.trim();
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station == null ? station : station.trim();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user == null ? user : user.trim();
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification == null ? verification : verification.trim();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role == null ? role : role.trim();
	}
}
