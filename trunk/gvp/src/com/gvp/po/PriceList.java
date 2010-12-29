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

	private Integer			listid;

	/** 供应商材料单价 */
	private String			price;

	/** 记录时间 */
	private Date			recordTime;

	/** 说明 */
	private String			remark;

	/** 规格id */
	private Specification	specification;

	public PriceList() {
	}

	public PriceList(Integer listid, String price, String remark, Date recordTime) {
		super();
		this.listid = listid;
		this.price = price;
		this.remark = remark;
		this.recordTime = recordTime;
	}

	public Integer getListid() {
		return listid;
	}

	public String getPrice() {
		return price;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRecordTime() {
		return recordTime;
	}

	public String getRemark() {
		return remark;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setListid(Integer listid) {
		this.listid = listid;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

}
