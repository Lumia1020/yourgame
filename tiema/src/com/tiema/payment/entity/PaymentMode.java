package com.tiema.payment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tiema.core.constant.State;

/**
 * @ClassName: PaymentMode
 * @Description: 具体的付款方式
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 11:03:17 AM
 * 
 */
@Entity
@Table(name = "T_PAYMENT_MODE")
public class PaymentMode implements Serializable {

	private static final long	serialVersionUID	= 380356392306230086L;

	private Long				id;

	/** @Fields modeName : 付款方式名称 */
	private String				modeName;

	/** @Fields paymentCategory : 所属付款方式类别-大类 */
	private PaymentCategory		paymentCategory;

	/** @Fields state : 状态:1可用,2不可用 */
	private State				state;

	/** @Fields remark : 备注 */
	private String				remark;

	public PaymentMode() {
	}

	@Id
	@SequenceGenerator(name = "PAYMENT_MODE_SEQ_GEN", sequenceName = "T_PAYMENT_MODE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_MODE_SEQ_GEN")
	public Long getId() {
		return id;
	}

	public String getModeName() {
		return modeName;
	}

	@ManyToOne
	@JoinColumn(name = "PAYMENT_CATEGORY_ID")
	public PaymentCategory getPaymentCategory() {
		return paymentCategory;
	}

	public String getRemark() {
		return remark;
	}

	@Column(nullable = false)
	@Enumerated
	public State getState() {
		return state;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public void setPaymentCategory(PaymentCategory paymentCategory) {
		this.paymentCategory = paymentCategory;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(State state) {
		this.state = state;
	}

}
