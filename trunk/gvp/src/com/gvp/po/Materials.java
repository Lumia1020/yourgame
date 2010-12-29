package com.gvp.po;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 生产材料信息
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Materials implements Serializable {
	private Integer			mid;

	/** 规格id */
	private Specification	specification;

	/** 产品名称 */
	private String			productsName;

	/** 材料名称 */
	private String			materialsName;

	/** 材料直径 */
	private String			diameter;

	/** 材料重量 */
	private String			weight;

	/** 支数 */
	private String			count;

	/** 共损耗 */
	private String			loss;

	/** 尺寸 */
	private String			size;

	/** 供应商材料单价 */
	private String			materialPrice;

	/** 材料单价 */
	private String			price;

	/** 减沙 */
	private String			jiansha;

	/** @Fields adjustRemark : 调节备注 */
	private String			adjustRemark;
	
	/** @Fields adjustDate : 调节时间 */
	private Date			adjustDate;

	/** 关联报时记录 */
	private Integer			qid;

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public String getMaterialsName() {
		return materialsName;
	}

	public void setMaterialsName(String materialsName) {
		this.materialsName = materialsName;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public String getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(String materialPrice) {
		this.materialPrice = materialPrice;
	}

	public String getLoss() {
		return loss;
	}

	public void setLoss(String loss) {
		this.loss = loss;
	}

	public String getJiansha() {
		return jiansha;
	}

	public void setJiansha(String jiansha) {
		this.jiansha = jiansha;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Materials adjust() {
		this.price = Double.toString(Double.valueOf(this.weight) * Double.valueOf(this.materialPrice) / 10000);
		return this;
	}

	public String getAdjustRemark() {
		return adjustRemark;
	}

	public void setAdjustRemark(String ajdustRemark) {
		this.adjustRemark = ajdustRemark;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAdjustDate() {
		return adjustDate;
	}

	public void setAdjustDate(Date adjustDate) {
		this.adjustDate = adjustDate;
	}

}
