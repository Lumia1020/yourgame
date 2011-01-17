package com.gvp.po;

import java.io.Serializable;

/**
 * @ClassName: StuffView
 * 
 *             <pre>
 * SELECT
 * 		s.stuffid,
 * 		s.stuffName,
 * 		p.id AS providerid,
 * 		p.providerName
 * 	FROM
 * 		t_stuff s
 * 	LEFT JOIN t_provider p ON p.id = s.providerid
 * 
 * </pre>
 * @Description: 材质视图
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-12-29 上午10:32:02
 * 
 */
@SuppressWarnings("serial")
@Deprecated
public class StuffView implements Serializable{

	private Integer	stuffid;

	/** 材质名称 */
	private String	stuffName;

	private Integer	providerid;

	/** @Fields providerName : 供应商名称 */
	private String	providerName;

	public StuffView() {
	}

	public Integer getProviderid() {
		return providerid;
	}

	public String getProviderName() {
		return providerName;
	}

	public Integer getStuffid() {
		return stuffid;
	}

	public String getStuffName() {
		return stuffName;
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

	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}

}
