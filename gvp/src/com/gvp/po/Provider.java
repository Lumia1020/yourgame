package com.gvp.po;

import java.io.Serializable;

/**
 * @ClassName: Provider
 * @Description: 供应商
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-12-28 下午05:31:42
 * 
 */
@SuppressWarnings("serial")
@Deprecated
public class Provider implements Serializable {

	private Integer	id;

	/** @Fields providerName : 供应商名字 */
	private String	providerName;

	public Provider() {
	}

	public Provider(String providerName) {
		this.providerName = providerName;
	}

	public Integer getId() {
		return id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

}
