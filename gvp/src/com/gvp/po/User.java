package com.gvp.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {
	private Integer userid;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 姓名 */
	private String name;
	
	/** 权限id */
	private Integer rid;

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
