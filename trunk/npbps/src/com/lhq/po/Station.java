package com.lhq.po;

import java.io.Serializable;

/**
 * 站段信息
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Station implements Serializable {
	private String uuid;

	/** 站名称 */
	private String stationName;

	/** 段名称 */
	private String sectionName;

	/** 站id */
	private String stationId;

	/** 段id */
	private String sectionId;

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String sid) {
		this.uuid = sid;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
}
