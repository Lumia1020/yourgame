package com.tiema.membership.category.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tiema.core.constant.State;

/**
 * @ClassName: MembershipCategory
 * @Description: 会籍种类实体
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 3:42:47 PM
 * 
 */
@Entity
@Table(name = "T_MEMBERSHIP_CATEGORY")
public class MembershipCategory implements Serializable {

	private static final long	serialVersionUID	= -102079689930578631L;

	/** @Fields id : 种类id */
	private Long				id;

	/** @Fields categoryName : 种类名称 */
	private String				categoryName;

	/** @Fields state : 状态:是否有效,如果失效了客户资料中就不能选择,否则不能,1有效,2无效 */
	private State				state;

	/** @Fields remark : 备注 */
	private String				remark;

	public MembershipCategory() {
	}

	public MembershipCategory(Long id, String categoryName) {
		this.id = id;
		this.categoryName = categoryName;
	}

	@Column(length = 50, unique = true, nullable = false)
	public String getCategoryName() {
		return categoryName;
	}

	@Id
	@SequenceGenerator(name = "MEMBERSHIP_CATEGORY_SEQ_GEN", sequenceName = "T_MEMBERSHIP_CATEGORY_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBERSHIP_CATEGORY_SEQ_GEN")
	public Long getId() {
		return id;
	}

	@Column(length = 400)
	public String getRemark() {
		return remark;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Column(nullable = false)
	@Enumerated
	public State getState() {
		return state;
	}

}
