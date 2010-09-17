package com.gvp.service.vo;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

public class PriceListVO implements Serializable {

	private static final long serialVersionUID = 4240983567289021839L;

	private String stuffName;
	private String speciesName;
	private String specName;
	private String price;
	private String remark;
	private Date recordTime;
	private Integer listid;

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRecordTime() {
		return recordTime;
	}

	public String getStuffName() {
		return stuffName;
	}

	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Integer getListid() {
		return listid;
	}

	public void setListid(Integer listid) {
		this.listid = listid;
	}

}
