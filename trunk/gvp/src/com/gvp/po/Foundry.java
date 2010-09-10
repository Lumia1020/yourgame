package com.gvp.po;

import java.io.Serializable;

/**
 * 代工信息
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Foundry implements Serializable {

	private Integer fid;

	/** 外发加工类别 */
	private String plateMerchant;

	/** 损耗率 */
	private String attritionRate;

	/** 外发加工单价 */
	private String processPrice;

	/** 备注 */
	private String remark;

	/** 关联到报时表 */
	private Integer qid;

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public String getAttritionRate() {
		return attritionRate;
	}

	public void setAttritionRate(String attritionRate) {
		this.attritionRate = attritionRate;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getPlateMerchant() {
		return plateMerchant;
	}

	public void setPlateMerchant(String plateMerchant) {
		this.plateMerchant = plateMerchant;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProcessPrice() {
		return processPrice;
	}

	public void setProcessPrice(String processPrice) {
		this.processPrice = processPrice;
	}


}
