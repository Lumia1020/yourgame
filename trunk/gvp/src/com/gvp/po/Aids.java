package com.gvp.po;

import java.io.Serializable;

/**
 * 辅助工具
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Aids implements Serializable {

	private Integer aid;

	/** 工具名称 */
	private String aidsName;

	/** 原价 */
	private String originalPrice;

	/** 单价 */
	private String price;

	private Integer qid;

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getAidsName() {
		return aidsName;
	}

	public void setAidsName(String aidsName) {
		this.aidsName = aidsName;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
