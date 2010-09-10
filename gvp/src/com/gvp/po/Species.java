package com.gvp.po;

import java.io.Serializable;

/**
 * 种类
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Species implements Serializable {

	private Integer speciesid;

	/** 种类名 */
	private String speciesName;

	/** 所属材质id */
	private Integer stuffid;

	/** 材质名称 */
	private String stuffName;


	public Species(Integer speciesid, String speciesName, Integer stuffid, String stuffName) {
		super();
		this.speciesid = speciesid;
		this.speciesName = speciesName;
		this.stuffid = stuffid;
		this.stuffName = stuffName;
	}

	public Species() {
		super();
	}

	public Integer getSpeciesid() {
		return speciesid;
	}

	public void setSpeciesid(Integer speciesid) {
		this.speciesid = speciesid;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public Integer getStuffid() {
		return stuffid;
	}

	public void setStuffid(Integer stuffid) {
		this.stuffid = stuffid;
	}

	public String getStuffName() {
		return stuffName;
	}

	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}
}
