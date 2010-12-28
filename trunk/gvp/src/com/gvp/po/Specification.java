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

	private Integer	specid;

	/** 规格名称 */
	private String	specName;

	/** 所属种类 */
	private Species	species;

	/** 供应商材料单价 */
	private String	price;

	public Specification() {
	}

	public Specification(Integer specid, String specName, Integer speciesid, String speciesName, String price) {
		super();
		this.specid = specid;
		this.specName = specName;
		this.species = new Species(speciesid, speciesName);
		this.price = price;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public Integer getSpecid() {
		return specid;
	}

	public void setSpecid(Integer specid) {
		this.specid = specid;
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

}
