package com.tiema.customer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.tiema.core.constant.Sex;
import com.tiema.core.constant.State;
import com.tiema.membership.category.entity.MembershipCategory;

/**
 * @ClassName: Customer
 * @Description: 客户资料实体
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 3:22:05 PM
 * 
 */
@Entity
@Table(name = "T_CUSTOMER")
public class Customer implements Serializable {

	private static final long	serialVersionUID	= 5331676447663506364L;

	/** @Fields id : 客户id */
	private Long				id;

	/** @Fields number : 客户编号 */
	private String				customerNumber;

	/** @Fields customerName : 客户姓名 */
	private String				customerName;

	/** @Fields sex : 性别:男、女、未知 */
	private Sex					sex;

	/** @Fields birthday : 出生日期 */
	private Date				birthday;

	/** @Fields membershipCategory : 会籍种类 */
	private MembershipCategory	membershipCategory;

	/** @Fields idCard : 身份证号码 */
	private String				idCard;

	/** @Fields mobile : 手机号码 */
	private String				mobile;

	/** @Fields contact : 联系电话 */
	private String				telephone;

	/** @Fields address : 通讯地址 */
	private String				address;

	/** @Fields zipCode : 邮政编码 */
	private String				zipCode;

	/** @Fields email : 电子邮件 */
	private String				email;

	/** @Fields buyer : 买方 */
	private String				buyer;

	/** @Fields seller : 卖方 */
	private String				seller;

	/** @Fields originalMember : 原始客户 */
	private String				originalCustomer;

	/** @Fields transactionDate : 交易日期 */
	private Date				transactionDate;

	/** @Fields remark : 说明 */
	private String				remark;

	/** @Fields state : 客户资料状态:1为可用,2为隐藏 */
	private State				state;

	public Customer() {
	}

	public Customer(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	@Column(length = 150)
	public String getAddress() {
		return address;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	@Column(length = 50)
	public String getBuyer() {
		return buyer;
	}

	@Column(length = 20)
	public String getTelephone() {
		return telephone;
	}

	@Column(length = 50, nullable = false)
	public String getCustomerName() {
		return customerName;
	}

	@Column(length = 18, updatable = false, nullable = false)
	public String getCustomerNumber() {
		return customerNumber;
	}

	@Column(length = 200)
	public String getEmail() {
		return email;
	}

	@Id
	@SequenceGenerator(name = "CUSTOMER_SEQ_GEN", sequenceName = "T_CUSTOMER_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ_GEN")
	public Long getId() {
		return id;
	}

	@Column(length = 50)
	public String getIdCard() {
		return idCard;
	}

	@OneToOne
	public MembershipCategory getMembershipCategory() {
		return membershipCategory;
	}

	@Column(length = 50)
	public String getMobile() {
		return mobile;
	}

	@Column(length = 50)
	public String getOriginalCustomer() {
		return originalCustomer;
	}

	@Column(length = 400)
	public String getRemark() {
		return remark;
	}

	@Column(length = 50)
	public String getSeller() {
		return seller;
	}

	@Enumerated
	public Sex getSex() {
		return sex;
	}

	@Column(nullable = false)
	@Enumerated
	public State getState() {
		return state;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format = "yyyy-MM-dd")
	public Date getTransactionDate() {
		return transactionDate;
	}

	@Column(length = 6)
	public String getZipCode() {
		return zipCode;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public void setTelephone(String contact) {
		this.telephone = contact;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerNumber(String number) {
		this.customerNumber = number;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setMembershipCategory(MembershipCategory membershipCategory) {
		this.membershipCategory = membershipCategory;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setOriginalCustomer(String originalMember) {
		this.originalCustomer = originalMember;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
