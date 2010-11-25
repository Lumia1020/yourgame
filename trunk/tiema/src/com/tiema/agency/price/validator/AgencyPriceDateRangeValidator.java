package com.tiema.agency.price.validator;

import java.util.Date;

import javax.annotation.Resource;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.tiema.agency.company.entity.AgencyCompany;
import com.tiema.agency.price.entity.AgencyPrice;
import com.tiema.agency.price.service.AgencyPriceService;

/**
 * @ClassName: AgencyPriceDateRangeValidator
 * @Description: 检查中介公司价格启用有效日期和结束日期范围是否可用，在新增和修改的时候进行检查
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-23 下午11:33:05
 * 
 */
public class AgencyPriceDateRangeValidator extends FieldValidatorSupport {

	private Boolean				update;

	private String				startDateFieldName;
	private String				endDateFieldName;
	private String				agencyPriceIdFieldName;
	private String				agencyCompanyIdFieldName;

	/** @Fields agencyPriceService : 中介公司价格业务对象 */
	private AgencyPriceService	agencyPriceService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.validator.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object obj) throws ValidationException {
		if (obj == null)
			return;

		AgencyPrice cp = null;
		if (obj instanceof AgencyPrice) {
			cp = (AgencyPrice) obj;
		} else {
			Object startDate = getFieldValue(getStartDateFieldName(), obj);
			Object endDate = getFieldValue(getEndDateFieldName(), obj);
			Object agencyPriceId = getFieldValue(getAgencyPriceIdFieldName(), obj);
			Object agencyCompanyId = getFieldValue(getAgencyCompanyIdFieldName(), obj);

			cp = new AgencyPrice();
			if (startDate != null) {
				cp.setStartDate((Date) startDate);
			}
			if (endDate != null) {
				cp.setEndDate((Date) endDate);
			}
			if (agencyPriceId != null) {
				cp.setId((Long) agencyPriceId);
			}
			if (agencyCompanyId != null) {
				AgencyCompany gc = new AgencyCompany((Long) agencyCompanyId);
				cp.setAgencyCompany(gc);
			}
		}

		boolean valid = agencyPriceService.validateDateRange(cp, getUpdate());
		if (!valid) {
			addFieldError(getFieldName(), obj);
		}
	}

	@Resource
	public void setAgencyPriceService(AgencyPriceService clubPriceService) {
		this.agencyPriceService = clubPriceService;
	}

	public Boolean getUpdate() {
		return update;
	}

	public String getStartDateFieldName() {
		return startDateFieldName;
	}

	public void setStartDateFieldName(String startDateFieldName) {
		this.startDateFieldName = startDateFieldName;
	}

	public String getEndDateFieldName() {
		return endDateFieldName;
	}

	public void setEndDateFieldName(String endDateFieldName) {
		this.endDateFieldName = endDateFieldName;
	}

	public String getAgencyPriceIdFieldName() {
		return agencyPriceIdFieldName;
	}

	public void setAgencyPriceIdFieldName(String agencyPriceIdFieldName) {
		this.agencyPriceIdFieldName = agencyPriceIdFieldName;
	}

	public String getAgencyCompanyIdFieldName() {
		return agencyCompanyIdFieldName;
	}

	public void setAgencyCompanyIdFieldName(String agencyCompanyIdFieldName) {
		this.agencyCompanyIdFieldName = agencyCompanyIdFieldName;
	}

	public AgencyPriceService getAgencyPriceService() {
		return agencyPriceService;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}


}
