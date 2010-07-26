package com.lhq.po;

import java.io.Serializable;
import java.util.Date;

/*******************************************************************************
 * 10、 总包接收表（T_PRECEIVE，中心局用）
 * 
 * @author yourgame
 * 
 */
public class Preceive implements Serializable {

	private String uuid;

	/** 袋牌号码 可空，防止散件没有袋牌的情况 */
	private String packageCode;

	/** 清单报刊代号 */
	private String listPressCode;

	/** 清单报刊名称 输入代号自动匹配名称 */
	private String listPressName;

	/** 清单报刊份数 */
	private Double listNumber;

	/** 接收日期时间 */
	private Date revTime;

	/** 接收员工姓名 不可存放工号，怕工号对应的姓名被改 */
	private String revPreson;

	/** 接收报刊的批次 接收刊物的批次（期别） */
	private String revTimeStep;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String id) {
		this.uuid = id;
	}

	public Double getListNumber() {
		return listNumber;
	}

	public void setListNumber(Double listNumber) {
		this.listNumber = listNumber;
	}

	public String getListPressCode() {
		return listPressCode;
	}

	public void setListPressCode(String listPressCode) {
		this.listPressCode = listPressCode;
	}

	public String getListPressName() {
		return listPressName;
	}

	public void setListPressName(String listPressName) {
		this.listPressName = listPressName;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getRevPreson() {
		return revPreson;
	}

	public void setRevPreson(String revPreson) {
		this.revPreson = revPreson;
	}

	public Date getRevTime() {
		return revTime;
	}

	public void setRevTime(Date revTime) {
		this.revTime = revTime;
	}

	public String getRevTimeStep() {
		return revTimeStep;
	}

	public void setRevTimeStep(String revTimeStep) {
		this.revTimeStep = revTimeStep;
	}

}
