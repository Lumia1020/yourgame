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

	private Integer	speciesid;

	/** 种类名 */
	private String	speciesName;

	/** 所属材质 */
	private Stuff	stuff;

	public Species(Integer speciesid, String speciesName, Integer stuffid, String stuffName) {
		super();
		this.speciesid = speciesid;
		this.speciesName = speciesName;
		this.stuff = new Stuff(stuffid, stuffName);
	}

	public Stuff getStuff() {
		return stuff;
	}

	public void setStuff(Stuff stuff) {
		this.stuff = stuff;
	}

	public Species() {
		super();
	}

	public Species(Integer speciesid2, String speciesName2) {
		this.speciesid = speciesid2;
		this.speciesName = speciesName2;
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

}
