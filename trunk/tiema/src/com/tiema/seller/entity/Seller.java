package com.tiema.seller.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tiema.core.constant.Sex;
import com.tiema.core.constant.State;

/**
 * @ClassName: Seller
 * @Description: 销售员
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-18 下午10:33:18
 * 
 */
@Entity
@Table(name = "T_SELLER")
public class Seller implements Serializable {

	private static final long	serialVersionUID	= 7394030172282209746L;

	private Long				id;

	/** @Fields sellerName : 姓名：销售员的姓名。 */
	private String				sellerName;

	/** @Fields sex : 性别：销售员的性别 */
	private Sex					sex;

	/** @Fields deptName : 部门：销售员的所属部门。 */
	private String				deptName;

	/** @Fields jobTitle : 职务：销售员的职务。 */
	private String				jobTitle;

	/** @Fields mobile : 移动电话：销售员的移动电话。 */
	private String				mobile;

	/** @Fields im : QQ/msn：销售员的QQ/msn账号。 */
	private String				im;

	/** @Fields email : E_mail：电子邮箱地址。 */
	private String				email;

	/** @Fields state : 销售员状态:1可用户,2锁定 */
	private State				state;

	/** @Fields remark : 备注：备注说明信息。 */
	private String				remark;

	public Seller() {
	}

	@Column(length = 50)
	public String getDeptName() {
		return deptName;
	}

	@Column(length = 100)
	public String getEmail() {
		return email;
	}

	@Id
	@SequenceGenerator(name = "SELLER_SEQ_GEN", sequenceName = "T_SELLER_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SELLER_SEQ_GEN")
	public Long getId() {
		return id;
	}

	@Column(length = 100)
	public String getIm() {
		return im;
	}

	@Column(length = 50)
	public String getJobTitle() {
		return jobTitle;
	}

	@Column(length = 50)
	public String getMobile() {
		return mobile;
	}

	@Column(length = 50, nullable = false)
	public String getSellerName() {
		return sellerName;
	}

	@Column(length = 400)
	public String getRemark() {
		return remark;
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

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public void setState(State state) {
		this.state = state;
	}

}
