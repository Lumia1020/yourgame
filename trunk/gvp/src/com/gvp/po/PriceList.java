package com.gvp.po;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 规格价格表
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class PriceList implements Serializable {

	private Integer listid;
	
	

	/** 规格id */
	private Integer specid;

	/** 规格名称 */
	private String specName;

	/** 供应商材料单价 */
	private String price;

	/** 说明 */
	private String remark;

	/** 记录时间 */
	private Date recordTime;

	public PriceList(Integer listid, Integer specid, String specName, String price, String remark, Date recordTime) {
		super();
		this.listid = listid;
		this.specid = specid;
		this.specName = specName;
		this.price = price;
		this.remark = remark;
		this.recordTime = recordTime;
	}

	public PriceList() {
	}

	public Integer getListid() {
		return listid;
	}

	public void setListid(Integer listid) {
		this.listid = listid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
