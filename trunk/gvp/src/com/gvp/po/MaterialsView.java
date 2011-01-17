package com.gvp.po;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAnyAttribute;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 生产材料信息视图
 * 
 * <pre>
  SELECT
  	`t`.`mid` AS `mid`,
  	`r`.`id` AS `providerid`,
  	`r`.`providerName` AS `providerName`,
  	`f`.`stuffid` AS `stuffid`,
  	`f`.`stuffName` AS `stuffName`,
  	`p`.`speciesid` AS `speciesid`,
  	`p`.`speciesName` AS `speciesName`,
  	`s`.`specid` AS `specid`,
  	`s`.`specName` AS `specName`,
  	`t`.`productsName` AS `productsName`,
  	`t`.`materialsName` AS `materialsName`,
  	`t`.`diameter` AS `diameter`,
  	`t`.`weight` AS `weight`,
  	`t`.`count` AS `count`,
  	`t`.`size` AS `size`,
  	`t`.`materialPrice` AS `materialPrice`,
  	`t`.`price` AS `price`,
  	`t`.`jiansha` AS `jiansha`,
  	`t`.`loss` AS `loss`,
  	`t`.`qid` AS `qid`,
  	`t`.`adjustRemark` AS `adjustRemark`,
  	`t`.`adjustDate` AS `adjustDate`
  FROM
  	(
  		(
  			(
  				(
  					`t_materials` `t`
  					LEFT JOIN `t_specification` `s` ON(
  						(`s`.`specid` = `t`.`specid`)
  					)
  				)
  				LEFT JOIN `t_species` `p` ON(
  					(
  						`p`.`speciesid` = `s`.`speciesid`
  					)
  				)
  			)
  			LEFT JOIN `t_stuff` `f` ON(
  				(
  					`f`.`stuffid` = `p`.`stuffid`
  				)
  			)
  		)
  		LEFT JOIN `t_provider` `r` ON(
  			(`r`.`id` = `f`.`providerid`)
  		)
  	)
  
 * </pre>
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class MaterialsView implements Serializable {
	private Integer	mid;

	@Deprecated
	private Integer	providerid;

	@Deprecated
	private String	providerName;

	/** 材质id */
	private Integer	stuffid;

	/** 材质名称 */
	private String	stuffName;

	/** 种类id */
	private Integer	speciesid;

	/** 种类名称 */
	private String	speciesName;

	/** 规格id */
	private Integer	specid;

	/** 规格名称 */
	private String	specName;

	/** 产品名称 */
	private String	productsName;

	/** 材料名称 */
	private String	materialsName;

	/** 材料直径 */
	private String	diameter;

	/** 材料重量 */
	private String	weight;

	/** 支数 */
	private String	count;

	/** 共损耗 */
	private String	loss;

	/** 尺寸 */
	private String	size;

	/** 供应商材料单价 */
	private String	materialPrice;

	/** 材料单价 */
	private String	price;

	/** 减沙 */
	private String	jiansha;

	/** @Fields adjustRemark : 调节备注 */
	private String	adjustRemark;
	/** @Fields adjustDate : 调节时间 */
	private Date	adjustDate;

	/** 关联报时记录 */
	private Integer	qid;

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public String getMaterialsName() {
		return materialsName;
	}

	@Deprecated
	public Integer getProviderid() {
		return providerid;
	}

	@Deprecated
	public String getProviderName() {
		return providerName;
	}

	@Deprecated
	public void setProviderid(Integer providerid) {
		this.providerid = providerid;
	}

	@Deprecated
	public void setProviderName(String providerName) {
		this.providerName = providerName;
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

	public Integer getSpecid() {
		return specid;
	}

	public void setSpecid(Integer specid) {
		this.specid = specid;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public Integer getSpeciesid() {
		return speciesid;
	}

	public void setSpeciesid(Integer speciesid) {
		this.speciesid = speciesid;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public Integer getStuffid() {
		return stuffid;
	}

	public void setStuffid(Integer stuffid) {
		this.stuffid = stuffid;
	}

	public String getStuffName() {
		return stuffName;
	}

	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public MaterialsView adjust() {
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
