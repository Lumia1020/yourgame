package com.gvp.po;

import java.io.Serializable;

/**
 * 规格
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Specification implements Serializable {

	private Integer specid;

	/** 规格名称 */
	private String specName;

	/** 材质id */
	private Integer stuffid;

	/** 材质名称 */
	private String stuffName;

	/** 所属种类 */
	private Integer speciesid;

	/** 种类名称 */
	private String speciesName;

	/** 供应商材料单价 */
	private String price;

	public Specification() {
	}

	public Specification(Integer specid, String specName, Integer stuffid, String stuffName, Integer speciesid,
			String speciesName, String price) {
		super();
		this.specid = specid;
		this.specName = specName;
		this.stuffid = stuffid;
		this.stuffName = stuffName;
		this.speciesid = speciesid;
		this.speciesName = speciesName;
		this.price = price;
	}

	public Integer getSpecid() {
		return specid;
	}

	public void setSpecid(Integer specid) {
		this.specid = specid;
	}

	public Integer getSpeciesid() {
		return speciesid;
	}

	public void setSpeciesid(Integer speciesid) {
		this.speciesid = speciesid;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public void setStuffName(String sutffName) {
		this.stuffName = sutffName;
	}

}
