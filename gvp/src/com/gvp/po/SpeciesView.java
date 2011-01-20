package com.gvp.po;

import java.io.Serializable;

/**
 * @ClassName: SpeciesView
 * <pre>
 * SELECT
	`s`.`speciesid` AS `speciesid`,
	`s`.`speciesName` AS `speciesName`,
	`t`.`stuffid` AS `stuffid`,
	`t`.`stuffName` AS `stuffName`,
FROM
	(
		(
			`t_species` `s`
			LEFT JOIN `t_stuff` `t` ON(
				(
					`t`.`stuffid` = `s`.`stuffid`
				)
			)
		)
	)
 * </pre>
 * @Description: 种类视图
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-12-29 上午11:53:40
 * 
 */
@SuppressWarnings("serial")
public class SpeciesView implements Serializable {

	private Integer	speciesid;

	/** 种类名 */
	private String	speciesName;

	/** 所属材质 */
	private Integer	stuffid;

	private String	stuffName;


	public Integer getSpeciesid() {
		return speciesid;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public Integer getStuffid() {
		return stuffid;
	}

	public String getStuffName() {
		return stuffName;
	}

	public void setSpeciesid(Integer speciesid) {
		this.speciesid = speciesid;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public void setStuffid(Integer stuffid) {
		this.stuffid = stuffid;
	}

	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}

	public SpeciesView() {
		// TODO Auto-generated constructor stub
	}

}
