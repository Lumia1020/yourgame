package com.gvp.po;

import java.io.Serializable;

/**
 * 参考信息
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class ReferenceInfo implements Serializable {

	private Integer rid;

	/** 咭刀 (刀片厚度切片大小,对材料造成一定的损失,损失质量等同于切面厚度) */
	private String cardKnife;

	/** 尾料 (剩余材料) */
	private String residualMaterial;

	/** 总长 */
	private String general;

	/** 成品重量 */
	private String finishedWeight;

	/** 一支料产量数 */
	private String output;

	/** 机器型号 */
	private String machineModel;

	/** 材料商 */
	private String materialSupplier;

	/** 最小起订量 */
	private String moq;
	
	/** 运费 */
	private Double freight;
	
	/**关联报时表*/
	private Integer qid;

	public String getCardKnife() {
		return cardKnife;
	}

	public void setCardKnife(String cardKnife) {
		this.cardKnife = cardKnife;
	}

	public String getFinishedWeight() {
		return finishedWeight;
	}

	public void setFinishedWeight(String finishedWeight) {
		this.finishedWeight = finishedWeight;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public String getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}


	public String getMaterialSupplier() {
		return materialSupplier;
	}

	public void setMaterialSupplier(String materialSupplier) {
		this.materialSupplier = materialSupplier;
	}

	public String getMoq() {
		return moq;
	}

	public void setMoq(String moq) {
		this.moq = moq;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getResidualMaterial() {
		return residualMaterial;
	}

	public void setResidualMaterial(String residualMaterial) {
		this.residualMaterial = residualMaterial;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}



}
