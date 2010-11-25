package com.tiema.agency.company.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tiema.core.constant.State;

/**
 * @ClassName: AgencyCompany
 * @Description: 中介公司
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 19, 2010 2:21:17 PM
 * 
 */
@Entity
@Table(name = "T_AGENCY_COMPANY")
public class AgencyCompany implements Serializable {

	private static final long	serialVersionUID	= -905554261627096573L;

	/** @Fields id : 主键 */
	private Long				id;

	/** @Fields companyName : 中介公司名称 */
	private String				companyName;

	/** @Fields contact : 联系人 */
	private String				contact;

	/** @Fields telephone : 固定电话 */
	private String				telephone;

	/** @Fields mobile : 移动电话 */
	private String				mobile;

	/** @Fields fax : 传真号码 */
	private String				fax;

	/** @Fields address : 公司地址 */
	private String				address;

	/** @Fields zipCode : 邮政编码 */
	private String				zipCode;

	/** @Fields im : QQ/msn */
	private String				im;

	/** @Fields email : E_mail：电子邮箱地址。 */
	private String				email;

	/** @Fields state : 是否有效:指该中介公司是否允许订场:1可以,2不可以 */
	private State				state;

	/** @Fields remark : 备注：备注说明信息。 */
	private String				remark;

	public AgencyCompany() {
	}

	public AgencyCompany(Long agencyCompanyId) {
		this.id = agencyCompanyId;
	}

	@Column(length = 150)
	public String getAddress() {
		return address;
	}

	@Column(length = 50)
	public String getCompanyName() {
		return companyName;
	}

	@Column(length = 50)
	public String getContact() {
		return contact;
	}

	@Column(length = 150)
	public String getEmail() {
		return email;
	}

	@Column(length = 50)
	public String getFax() {
		return fax;
	}

	@Id
	@SequenceGenerator(name = "AGENCY_COMPANY_SEQ_GEN", sequenceName = "T_AGENCY_COMPANY_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGENCY_COMPANY_SEQ_GEN")
	public Long getId() {
		return id;
	}

	@Column(length = 100)
	public String getIm() {
		return im;
	}

	@Column(length = 50)
	public String getMobile() {
		return mobile;
	}

	@Column(length = 400)
	public String getRemark() {
		return remark;
	}

	@Column(nullable = false)
	@Enumerated
	public State getState() {
		return state;
	}

	@Column(length = 50)
	public String getTelephone() {
		return telephone;
	}

	@Column(length = 6)
	public String getZipCode() {
		return zipCode;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
