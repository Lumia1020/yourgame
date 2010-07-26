package com.lhq.po;

import java.io.Serializable;

/**
 * 4、	台席信息表
 * @author yourgame
 *
 */
@SuppressWarnings("serial")
public class Seat implements Serializable {

	private String uuid;

	/** 台席ID */
	private String seatId;

	/** 台席名称 用户自定义，指分发用的台席-包括格口 */
	private String seatName;

	/** 含有的站ID 存放数组（“1，2，3，5，6”） */
	private String stationId;

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
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

}
