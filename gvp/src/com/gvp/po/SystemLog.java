package com.gvp.po;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

@SuppressWarnings("serial")
public class SystemLog implements Serializable {

	private Integer logid;

	/** 用户id */
	private Integer userid;

	/** 用户名 */
	private String username;

	/** 操作说明 */
	private String exeOperation;

	/** 操作时间 */
	private Date recordTime;

	@Override
	public String toString() {
		return "用户ID:" + this.getUserid() + "用户名:" + this.getUsername() + "操作说明:" + this.getExeOperation() + "操作时间:"
				+ this.getRecordTime();
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date createtime) {
		this.recordTime = createtime;
	}

	public String getExeOperation() {
		return exeOperation;
	}

	public void setExeOperation(String exeOperation) {
		this.exeOperation = exeOperation;
	}

	public Integer getLogid() {
		return logid;
	}

	public void setLogid(Integer logid) {
		this.logid = logid;
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

	public SystemLog() {
		super();
	}

	public SystemLog(Integer userid, String username, String exeOperation, Date createtime) {
		super();
		this.userid = userid;
		this.username = username;
		this.exeOperation = exeOperation;
		this.recordTime = createtime;
	}

}
