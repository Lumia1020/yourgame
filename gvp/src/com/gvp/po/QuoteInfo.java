package com.gvp.po;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 报时表
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class QuoteInfo implements Serializable {

	private Integer qid;
	
	/** 客户id */
	private Integer cid;

	/** 客户 */
	private String customerName;
	
	/** 客户类别 */
	private String customerType;

	/** 产品编码 */
	private String productCode;

	/** 报价人 */
	private String quoter;

	/** 单价 */
	private String price;

	/** 填单日期 */
	private Date recordTime;

	/** 修改时间 */
	private Date modifyTime;

	/** DCC */
	private String dccNo;

	/** 存入文件夹页码 */
	private String pageNo;

	/** 归属哪个原单 */
	private Integer ownerId;
	
	/**报时表状态 未提交审核申请,已提交审核申请,已审核,已退回*/
	private String state;
	
	/** @Fields adjustDate : 调节日期*/
	private Date adjustDate;

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAdjustDate() {
		return adjustDate;
	}

	public void setAdjustDate(Date adjustDate) {
		this.adjustDate = adjustDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public String getQuoter() {
		return quoter;
	}

	public void setQuoter(String quoter) {
		this.quoter = quoter;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDccNo() {
		return dccNo;
	}

	public void setDccNo(String dccNo) {
		this.dccNo = dccNo;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

}
