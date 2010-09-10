package com.gvp.po;

import java.io.Serializable;

/**
 * 加工信息
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class ProcessInfo implements Serializable {

	private Integer pid;

	/** 机种 */
	private String models;

	/** 工序名称 */
	private String processName;

	/** 预计日产量 */
	private String dayOutput;

	/** 加工时间 */
	private String processTime;

	/** 损耗率 */
	private String attritionRate;
	
	/**调机品*/
	private String conditioners;

	/** 加工单价 */
	private String processPrice;
	
	/**关联到报时表*/
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

	public String getModels() {
		return models;
	}

	public void setModels(String models) {
		this.models = models;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}


	public String getProcessPrice() {
		return processPrice;
	}

	public void setProcessPrice(String processPrice) {
		this.processPrice = processPrice;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	public String getDayOutput() {
		return dayOutput;
	}

	public void setDayOutput(String dayOutput) {
		this.dayOutput = dayOutput;
	}

	public String getConditioners() {
		return conditioners;
	}

	public void setConditioners(String conditioners) {
		this.conditioners = conditioners;
	}

}
