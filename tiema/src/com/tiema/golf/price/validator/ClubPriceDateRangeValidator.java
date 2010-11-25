package com.tiema.golf.price.validator;

import java.util.Date;

import javax.annotation.Resource;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.tiema.golf.club.entity.GolfClub;
import com.tiema.golf.price.entity.ClubPrice;
import com.tiema.golf.price.service.ClubPriceService;

/**
 * @ClassName: ClubPriceDateRangeValidator
 * @Description: 检查俱乐部价格启用有效日期和结束日期范围是否可用,在新增和修改的时候进行检查
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 23, 2010 11:50:43 AM
 * 
 */
public class ClubPriceDateRangeValidator extends FieldValidatorSupport {

	private Boolean				update;

	private String				startDateFieldName;
	private String				endDateFieldName;
	private String				clubPriceIdFieldName;
	private String				golfClubIdFieldName;

	/** @Fields clubPriceService : 俱乐部价格业务对象 */
	private ClubPriceService	clubPriceService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.validator.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object obj) throws ValidationException {
		if (obj == null)
			return;

		ClubPrice cp = null;
		if (obj instanceof ClubPrice) {
			cp = (ClubPrice) obj;
		} else {
			Object startDate = getFieldValue(getStartDateFieldName(), obj);
			Object endDate = getFieldValue(getEndDateFieldName(), obj);
			Object clubPriceId = getFieldValue(getClubPriceIdFieldName(), obj);
			Object golfClubId = getFieldValue(getGolfClubIdFieldName(), obj);

			cp = new ClubPrice();
			if (startDate != null) {
				cp.setStartDate((Date) startDate);
			}
			if (endDate != null) {
				cp.setEndDate((Date) endDate);
			}
			if (clubPriceId != null) {
				cp.setId((Long) clubPriceId);
			}
			if (golfClubId != null) {
				GolfClub gc = new GolfClub((Long) golfClubId);
				cp.setGolfClub(gc);
			}
		}

		boolean valid = clubPriceService.validateDateRange(cp, getUpdate());
		if (!valid) {
			addFieldError(getFieldName(), obj);
		}
	}

	@Resource
	public void setClubPriceService(ClubPriceService clubPriceService) {
		this.clubPriceService = clubPriceService;
	}

	public String getStartDateFieldName() {
		return startDateFieldName;
	}

	public String getEndDateFieldName() {
		return endDateFieldName;
	}

	public void setStartDateFieldName(String startDateFieldName) {
		this.startDateFieldName = startDateFieldName;
	}

	public void setEndDateFieldName(String endDateFieldName) {
		this.endDateFieldName = endDateFieldName;
	}

	public String getClubPriceIdFieldName() {
		return clubPriceIdFieldName;
	}

	public String getGolfClubIdFieldName() {
		return golfClubIdFieldName;
	}

	public void setClubPriceIdFieldName(String clubPriceIdFieldName) {
		this.clubPriceIdFieldName = clubPriceIdFieldName;
	}

	public void setGolfClubIdFieldName(String golfClubIdFieldName) {
		this.golfClubIdFieldName = golfClubIdFieldName;
	}

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

}
