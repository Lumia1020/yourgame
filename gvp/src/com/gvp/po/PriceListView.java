package com.gvp.po;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 规格价格表视图
 * 
 * <pre>
 * SELECT
 * 	`v`.`listid` AS `listid`,
 * 	`r`.`id` AS `providerid`,
 * 	`r`.`providerName` AS `providerName`,
 * 	`f`.`stuffid` AS `stuffid`,
 * 	`f`.`stuffName` AS `stuffName`,
 * 	`p`.`speciesid` AS `speciesid`,
 * 	`p`.`speciesName` AS `speciesName`,
 * 	`s`.`specid` AS `specid`,
 * 	`s`.`specName` AS `specName`,
 * 	`v`.`price` AS `price`,
 * 	`v`.`remark` AS `remark`,
 * 	`v`.`recordTime` AS `recordTime`
 * FROM
 * 	(
 * 		(
 * 			(
 * 				(
 * 					`t_price_list` `v`
 * 					LEFT JOIN `t_specification` `s` ON(
 * 						(`s`.`specid` = `v`.`specid`)
 * 					)
 * 				)
 * 				LEFT JOIN `t_species` `p` ON(
 * 					(
 * 						`p`.`speciesid` = `s`.`speciesid`
 * 					)
 * 				)
 * 			)
 * 			LEFT JOIN `t_stuff` `f` ON(
 * 				(
 * 					`p`.`stuffid` = `f`.`stuffid`
 * 				)
 * 			)
 * 		)
 * 		LEFT JOIN `t_provider` `r` ON(
 * 			(`r`.`id` = `f`.`providerid`)
 * 		)
 * 	)
 * </pre>
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class PriceListView implements Serializable {

	private Integer	listid;

	private Integer	providerid;

	private String	providerName;

	private Integer	stuffid;

	private String	stuffName;

	private Integer	speciesid;

	private String	speciesName;
	
	private Integer	specid;

	/** 规格名称 */
	private String	specName;

	/** 供应商材料单价 */
	private String	price;

	/** 记录时间 */
	private Date	recordTime;

	/** 说明 */
	private String	remark;

	public Integer getProviderid() {
		return providerid;
	}

	public String getProviderName() {
		return providerName;
	}

	public Integer getStuffid() {
		return stuffid;
	}


	public Integer getSpecid() {
		return specid;
	}

	public String getSpecName() {
		return specName;
	}

	public Integer getSpeciesid() {
		return speciesid;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setProviderid(Integer providerid) {
		this.providerid = providerid;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
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

	public void setSpecid(Integer specid) {
		this.specid = specid;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public void setSpeciesid(Integer speciesid) {
		this.speciesid = speciesid;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public PriceListView() {
	}

	public PriceListView(Integer listid, String price, String remark, Date recordTime) {
		super();
		this.listid = listid;
		this.price = price;
		this.remark = remark;
		this.recordTime = recordTime;
	}

	public Integer getListid() {
		return listid;
	}

	public String getPrice() {
		return price;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRecordTime() {
		return recordTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setListid(Integer listid) {
		this.listid = listid;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
