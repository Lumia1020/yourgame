package com.gvp.po;

import java.io.Serializable;

/**
 * 材质
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Stuff implements Serializable {

	private Integer stuffid;

	/** 材质名称 */
	private String stuffName;

	public Stuff(Integer sid, String stuffName) {
		super();
		this.stuffid = sid;
		this.stuffName = stuffName;
	}

	public Stuff() {
		super();
	}

	public Integer getStuffid() {
		return stuffid;
	}

	public void setStuffid(Integer sid) {
		this.stuffid = sid;
	}

	public String getStuffName() {
		return stuffName;
	}

	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}

}
