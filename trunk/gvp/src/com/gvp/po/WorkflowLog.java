package com.gvp.po;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 记录报时单提交审核与退回的工作流程日志
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class WorkflowLog implements Serializable {
	private Integer logid;

	/** 报时单id */
	private Integer qid;

	/** 说明 */
	private String description;
	
	/**报时单状态*/
	private String state;

	/** 操作人id*/
	private Integer userid;

	/** 用户名 */
	private String username;
	
	/** 记录时间 */
	private Date recordTime;

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public WorkflowLog() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLogid() {
		return logid;
	}

	public void setLogid(Integer logid) {
		this.logid = logid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
