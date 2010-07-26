package com.lhq.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 6、 分发表
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Dispense implements Serializable {

	private String uuid;

	/** 站名称 */
	private String stationName;

	/** 段名称 */
	private String sectionName;

	/** 应分发份数 */
	private Double initNumber;

	/** 实际分发份数 */
	private Double actNumber;

	/** 报刊代号 */
	private String pressCode;

	/** 报刊名称 */
	private String pressName;

	/** 期别 用于显示分发用，”200915”（考虑2010年） */
	private String pressTimeStep;

	/** 台席ID */
	private String seatId;

	/**0代表投递站未接收，1代表投递站已接*/
	private String rflag;

	/**代表接收日期时间*/
	private Date rtime;

	public Double getActNumber() {
		return actNumber;
	}

	public void setActNumber(Double actNumber) {
		this.actNumber = actNumber;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String id) {
		this.uuid = id;
	}

	public Double getInitNumber() {
		return initNumber;
	}

	public void setInitNumber(Double initNumber) {
		this.initNumber = initNumber;
	}

	public String getPressCode() {
		return pressCode;
	}

	public void setPressCode(String pressCode) {
		this.pressCode = pressCode;
	}

	public String getPressName() {
		return pressName;
	}

	public void setPressName(String pressName) {
		this.pressName = pressName;
	}

	public String getPressTimeStep() {
		return pressTimeStep;
	}

	public void setPressTimeStep(String pressTimeStep) {
		this.pressTimeStep = pressTimeStep;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getRflag() {
		return rflag;
	}

	public void setRflag(String rflag) {
		this.rflag = rflag;
	}

	public Date getRtime() {
		return rtime;
	}

	public void setRtime(Date rtime) {
		this.rtime = rtime;
	}


}
