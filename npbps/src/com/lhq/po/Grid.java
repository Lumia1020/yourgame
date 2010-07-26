package com.lhq.po;

import java.io.Serializable;

/**
 * 5、 格口信息表
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Grid implements Serializable {

	private String uuid;

	/** 站ID */
	private String stationId;

	/** 段ID */
	private String sectionId;

	/** 片区名称 */
	private String area;

	private String id;

	/** 台席ID */
	private String seatId;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String gid) {
		this.uuid = gid;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
