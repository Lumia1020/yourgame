package com.gvp.po;

import java.io.Serializable;

/**
 * 报时单其他价格
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class OtherQuotePrice implements Serializable {

	private Integer oid;

	/** 单价 */
	private String price;

	/** 说明 */
	private String remark;

	private Integer qid;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
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

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

}
