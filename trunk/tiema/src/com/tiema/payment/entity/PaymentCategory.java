package com.tiema.payment.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tiema.core.constant.State;
import com.tiema.payment.constant.PaymentType;

/**
 * @ClassName: PaymentCategory
 * @Description: 付款方式类别,大类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 10:06:21 AM
 * 
 */
@Entity
@Table(name = "T_PAYMENT_CATEGORY")
public class PaymentCategory implements Serializable {

	private static final long	serialVersionUID	= 5315107405443421017L;

	private Long				id;

	/** @Fields categoryName : 付款方式类别名称 */
	private String				categoryName;

	/** @Fields type : 类型,用来区分付款方式的一种标识,就像人一样,只有男女两种性别 */
	private PaymentType			paymentType;

	/** @Fields state : 状态:1可用,2不可用 ,当设置为2的时候,所有子类都要设定为2 */
	private State				state;

	/** @Fields remark : 备注 */
	private String				remark;

	/** @Fields paymentModes : 付款方式集合 */
	private Set<PaymentMode>	paymentModes		= new HashSet<PaymentMode>();

	public PaymentCategory() {
	}

	@Column(length = 50, nullable = false)
	public String getCategoryName() {
		return categoryName;
	}

	@Id
	@SequenceGenerator(name = "PAYMENT_CATEGORY_SEQ_GEN", sequenceName = "T_PAYMENT_CATEGORY_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_CATEGORY_SEQ_GEN")
	public Long getId() {
		return id;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentCategory")
	public Set<PaymentMode> getPaymentModes() {
		return paymentModes;
	}

	@Enumerated
	@Column(nullable = false)
	public PaymentType getPaymentType() {
		return paymentType;
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

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPaymentModes(Set<PaymentMode> paymentModes) {
		this.paymentModes = paymentModes;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(State state) {
		this.state = state;
	}

}
