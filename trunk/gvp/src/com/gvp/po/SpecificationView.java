package com.gvp.po;

import java.io.Serializable;

/**
 * 规格视图
 * <pre>
 * SELECT
	`s`.`specid` AS `specid`,
	`s`.`specName` AS `specName`,
	`s`.`price` AS `price`,
	`p`.`speciesid` AS `speciesid`,
	`p`.`speciesName` AS `speciesName`,
	`f`.`stuffid` AS `stuffid`,
	`f`.`stuffName` AS `stuffName`
FROM
	(
		(
			(
				`t_specification` `s`
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
	)
 * </pre>
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class SpecificationView implements Serializable {

	/** 供应商材料单价 */
	private String	price;

//	private Integer	providerid;
//
//	private String	providerName;
	private Integer	specid;

	/** 所属种类 */
	private Integer	speciesid;
	private String	speciesName;

	/** 规格名称 */
	private String	specName;
	private Integer	stuffid;
	
	

	private String	stuffName;

	public SpecificationView() {
	}

	public String getPrice() {
		return price;
	}

//	public Integer getProviderid() {
//		return providerid;
//	}
//
//	public String getProviderName() {
//		return providerName;
//	}

	public Integer getSpecid() {
		return specid;
	}

	public Integer getSpeciesid() {
		return speciesid;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public String getSpecName() {
		return specName;
	}

	public Integer getStuffid() {
		return stuffid;
	}

	public String getStuffName() {
		return stuffName;
	}

	public void setPrice(String price) {
		this.price = price;
	}

//	public void setProviderid(Integer providerid) {
//		this.providerid = providerid;
//	}
//
//	public void setProviderName(String providerName) {
//		this.providerName = providerName;
//	}

	public void setSpecid(Integer specid) {
		this.specid = specid;
	}

	public void setSpeciesid(Integer speciesid) {
		this.speciesid = speciesid;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public void setStuffid(Integer stuffid) {
		this.stuffid = stuffid;
	}

	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}

}
