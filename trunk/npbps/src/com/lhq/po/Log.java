package com.lhq.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 7、 日志表
 * 
 * @author yourgame
 * 
 */
public class Log implements Serializable {

	private String uuid;

	/** 登录账号 */
	private String userId;

	/** 分发日期时间 */
	private Date dispenseDate;

	/** 分发报刊代号 */
	private String dispenseCode;

	/** 分发实际份数 */
	private Double dispenseActnum;

	public Double getDispenseActnum() {
		return dispenseActnum;
	}

	public void setDispenseActnum(Double dispenseActnum) {
		this.dispenseActnum = dispenseActnum;
	}

	public String getDispenseCode() {
		return dispenseCode;
	}

	public void setDispenseCode(String dispenseCode) {
		this.dispenseCode = dispenseCode;
	}

	public Date getDispenseDate() {
		return dispenseDate;
	}

	public void setDispenseDate(Date dispenseDate) {
		this.dispenseDate = dispenseDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String lid) {
		this.uuid = lid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
