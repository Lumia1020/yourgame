package com.lhq.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 9、 验单录入表
 * 
 * @author yourgame
 * 
 */
public class Verification implements Serializable {

	private String uuid;

	/** 验单类型 “零星”等 */
	private String veriType;

	/** 验单号 4位年+2位月+2位日+6位时分秒 */
	private String veriCode;

	/** 寄达局 “广州报刊局” */
	private String toOffice;

	/** 报刊代号 “1-1”等 */
	private String pressCode;

	/** 报刊名称 “东方周刊”等 */
	private String pressName;

	/** 分发期别 “0928”等（考虑2010年多增加2位） */
	private String pressTimeStep;

	/** 副刊名 “正刊”等 */
	private String pressAlias;

	/** 出版日期 “YYY-MM-DD” */
	private Date publishDate;

	/** 应发份数 “11” */
	private Double autoNumber;

	/** 标签份数 “11” */
	private Double signNumber;

	/** 短少份数 “1” */
	private Double lessNumber;

	/** 短少类别 “差错发生在正/副张内”等 */
	private String lessType;

	/** 加急标志 “正常” */
	private String urgentSign;

	/** 联系电话 “2168209” */
	private String conTel;

	/** 挂号号码 “0123” */
	private String conNum;

	/** 主管员工 */
	private String conManager;

	/** 备注 */
	private String mark;

	/** 是否已发验单标志 “0”或“1” */
	private String checked;

	/** 验单录入打印时间 */
	private Date checkedDate;

	/** 发验者姓名 */
	private String checkedUser;

	public Double getAutoNumber() {
		return autoNumber;
	}

	public void setAutoNumber(Double autoNumber) {
		this.autoNumber = autoNumber;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Date getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(Date checkedDate) {
		this.checkedDate = checkedDate;
	}

	public String getCheckedUser() {
		return checkedUser;
	}

	public void setCheckedUser(String checkedUser) {
		this.checkedUser = checkedUser;
	}

	public String getConManager() {
		return conManager;
	}

	public void setConManager(String conManager) {
		this.conManager = conManager;
	}

	public String getConNum() {
		return conNum;
	}

	public void setConNum(String conNum) {
		this.conNum = conNum;
	}

	public String getConTel() {
		return conTel;
	}

	public void setConTel(String conTel) {
		this.conTel = conTel;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String id) {
		this.uuid = id;
	}

	public Double getLessNumber() {
		return lessNumber;
	}

	public void setLessNumber(Double lessNumber) {
		this.lessNumber = lessNumber;
	}

	public String getLessType() {
		return lessType;
	}

	public void setLessType(String lessType) {
		this.lessType = lessType;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getPressAlias() {
		return pressAlias;
	}

	public void setPressAlias(String pressAlias) {
		this.pressAlias = pressAlias;
	}

	public String getPressCode() {
		return pressCode;
	}

	public void setPressCode(String pressCode) {
		this.pressCode = pressCode;
	}

	public String getPressName() {
		return pressName;
	}

	public void setPressName(String pressName) {
		this.pressName = pressName;
	}

	public String getPressTimeStep() {
		return pressTimeStep;
	}

	public void setPressTimeStep(String pressTimeStep) {
		this.pressTimeStep = pressTimeStep;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Double getSignNumber() {
		return signNumber;
	}

	public void setSignNumber(Double signNumber) {
		this.signNumber = signNumber;
	}

	public String getToOffice() {
		return toOffice;
	}

	public void setToOffice(String toOffice) {
		this.toOffice = toOffice;
	}

	public String getUrgentSign() {
		return urgentSign;
	}

	public void setUrgentSign(String urgentSign) {
		this.urgentSign = urgentSign;
	}

	public String getVeriCode() {
		return veriCode;
	}

	public void setVeriCode(String veriCode) {
		this.veriCode = veriCode;
	}

	public String getVeriType() {
		return veriType;
	}

	public void setVeriType(String veriType) {
		this.veriType = veriType;
	}

}
