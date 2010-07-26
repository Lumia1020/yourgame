package com.lhq.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 8、 要数表
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class GetData implements Serializable {

	private String uuid;

	/** 站名称 */
	private String stationName;

	/** 段名称 */
	private String sectionName;

	/** 报刊代号 */
	private String pressCode;

	/** 报刊名称 */
	private String pressName;

	/** 报刊期刊属性 */
	private String pressInfo;

	/** 集订分送份数 */
	private String outNumber;

	/** 集订分送的单位名称 */
	private String outDepart;

	/** 集订分送地址 */
	private String outAddress;

	/** 集订分送邮编 */
	private String outPostCode;

	/** 订阅起日期 */
	private Date startDate;

	/** 订阅止日期 */
	private Date endDate;

	/** 出版地 */
	private String publishAddress;

	/** 核对年价 */
	private String checkYearPrice;

	/** 核对金额 */
	private String checkPrice;

	/** 折后金额 */
	private String discountPrice;

	/** 收件人电话 */
	private String receiveTell;

	/** 收件人 */
	private String receiveName;

	public String getCheckPrice() {
		return checkPrice;
	}

	public void setCheckPrice(String checkPrice) {
		this.checkPrice = checkPrice;
	}

	public String getCheckYearPrice() {
		return checkYearPrice;
	}

	public void setCheckYearPrice(String checkYearPrice) {
		this.checkYearPrice = checkYearPrice;
	}

	public String getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getPublishAddress() {
		return publishAddress;
	}

	public void setPublishAddress(String publishAddress) {
		this.publishAddress = publishAddress;
	}

	public String getReceiveTell() {
		return receiveTell;
	}

	public void setReceiveTell(String receiveTell) {
		this.receiveTell = receiveTell;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOutAddress() {
		return outAddress;
	}

	public void setOutAddress(String outAddress) {
		this.outAddress = outAddress;
	}

	public String getOutDepart() {
		return outDepart;
	}

	public void setOutDepart(String outDepart) {
		this.outDepart = outDepart;
	}

	public String getOutNumber() {
		return outNumber;
	}

	public void setOutNumber(String outNumber) {
		this.outNumber = outNumber;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getOutPostCode() {
		return outPostCode;
	}

	public void setOutPostCode(String outPostCode) {
		this.outPostCode = outPostCode;
	}

	public String getPressCode() {
		return pressCode;
	}

	public void setPressCode(String pressCode) {
		this.pressCode = pressCode;
	}

	public String getPressInfo() {
		return pressInfo;
	}

	public void setPressInfo(String pressInfo) {
		this.pressInfo = pressInfo;
	}

	public String getPressName() {
		return pressName;
	}

	public void setPressName(String pressName) {
		this.pressName = pressName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
}
