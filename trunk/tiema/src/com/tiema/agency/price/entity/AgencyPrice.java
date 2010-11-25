package com.tiema.agency.price.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.tiema.agency.company.entity.AgencyCompany;
import com.tiema.core.constant.State;

/**
 * @ClassName: AgencyPrice
 * @Description: 中介价格
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-23 下午11:06:43
 * 
 */
@Entity
@Table(name = "T_AGENCY_PRICE")
public class AgencyPrice implements Serializable {

	private static final long	serialVersionUID	= -6744008329929828031L;

	private Long				id;

	/** @Fields agencyCompany : 属于哪个中介公司的价格 */
	private AgencyCompany		agencyCompany;

	/** @Fields dailyCost : 平日成本价 */
	private Double				dailyCost;

	/** @Fields weekdayPrice : 平日价格 */
	private Double				weekdayPrice;

	/** @Fields holidayCost : 假日成本价 */
	private Double				holidayCost;

	/** @Fields holidayPrice : 假日价格 */
	private Double				holidayPrice;

	/** @Fields startDate : 开始日期 */
	private Date				startDate;

	/** @Fields endDate : 结束日期 */
	private Date				endDate;

	/** @Fields state : 记录状态：1有效，2无效 */
	private State				state;

	/** @Fields remark : 备注 */
	private String				remark;

	public AgencyPrice() {
	}

	public AgencyPrice(Long id, Double dailyCost, Double weekdayPrice, Double holidayCost, Double holidayPrice, Date startDate, Date endDate, State state, String remark) {
		super();
		this.id = id;
		this.dailyCost = dailyCost;
		this.weekdayPrice = weekdayPrice;
		this.holidayCost = holidayCost;
		this.holidayPrice = holidayPrice;
		this.startDate = startDate;
		this.endDate = endDate;
		this.state = state;
		this.remark = remark;
	}

	@ManyToOne
	public AgencyCompany getAgencyCompany() {
		return agencyCompany;
	}

	@Column(precision = 12, scale = 2)
	public Double getDailyCost() {
		return dailyCost;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	@Column(precision = 12, scale = 2)
	public Double getHolidayCost() {
		return holidayCost;
	}

	@Column(precision = 12, scale = 2)
	public Double getHolidayPrice() {
		return holidayPrice;
	}

	@Id
	@SequenceGenerator(name = "AGENCY_PRICE_SEQ_GEN", sequenceName = "T_AGENCY_PRICE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGENCY_PRICE_SEQ_GEN")
	public Long getId() {
		return id;
	}

	@Column(length = 400)
	public String getRemark() {
		return remark;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	@Column(nullable = false)
	@Enumerated
	public State getState() {
		return state;
	}

	/**
	 * @Title: getStateName
	 * @Description: 获得状态的字面意思
	 */
	@Transient
	public String getStateName() {
		return state.toString();
	}

	@Column(precision = 12, scale = 2)
	public Double getWeekdayPrice() {
		return weekdayPrice;
	}

	public void setAgencyCompany(AgencyCompany agencyCompany) {
		this.agencyCompany = agencyCompany;
	}

	public void setDailyCost(Double dailyCost) {
		this.dailyCost = dailyCost;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setHolidayCost(Double holidayCost) {
		this.holidayCost = holidayCost;
	}

	public void setHolidayPrice(Double holidayPrice) {
		this.holidayPrice = holidayPrice;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setWeekdayPrice(Double weekdayPrice) {
		this.weekdayPrice = weekdayPrice;
	}

}
