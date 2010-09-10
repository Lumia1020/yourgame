package com.gvp.po;

import java.io.Serializable;

/**
 * 客户信息
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class Customer implements Serializable {

	private Integer cid;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户类别
	 */
	private String customerType;

	/**
	 * 产品类型分组字符串 HTL#MT01 HTL#MT02 HTL#MT03 为同一种类产品的3个不同型号
	 */
	private String productCode;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 电话
	 */
	private String tell;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 电子邮件
	 */
	private String email;

	public Customer() {
	}


	public Customer(String customerName) {
		super();
		this.customerName = customerName;
	}


	public Customer(String customerName, String customerType, String productCode) {
		super();
		this.customerName = customerName;
		this.customerType = customerType;
		this.productCode = productCode;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
