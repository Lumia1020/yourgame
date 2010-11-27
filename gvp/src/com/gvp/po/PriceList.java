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

	/** 材质id */
	private Integer stuffid;

	/** 种类id */
	private Integer speciesid;

	/** 规格id */
	private Integer specid;

	private Integer cid;

	/**
	 * 客户名称
	 */
	private String customerName;

	/** 供应商材料单价 */
	private String price;

	/** 说明 */
	private String remark;

	/** 记录时间 */
	private Date recordTime;

	public PriceList(Integer listid, Integer stuffid, Integer speciesid, Integer specid, String price, String remark,
			Date recordTime) {
		super();
		this.listid = listid;
		this.stuffid = stuffid;
		this.speciesid = speciesid;
		this.specid = specid;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStuffid() {
		return stuffid;
	}

	public void setStuffid(Integer stuffid) {
		this.stuffid = stuffid;
	}

	public Integer getSpeciesid() {
		return speciesid;
	}

	public void setSpeciesid(Integer speciesid) {
		this.speciesid = speciesid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
