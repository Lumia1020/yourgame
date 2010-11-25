package com.tiema.membership.category.validator;

import javax.annotation.Resource;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.tiema.membership.category.entity.MembershipCategory;
import com.tiema.membership.category.service.MembershipCategoryService;

/**
 * @ClassName: MembershipCategoryExistsValidator
 * @Description: 检查会籍种类名的在数据库种是否存在的校验器，只有categoryName的时候就检查会籍种类名， 如果还有id的话就检查不等于这个id但是等于这个categoryName的会籍种类记录是否存在
 * @author yourgame <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-14 下午10:40:54
 * 
 */
public class MembershipCategoryExistsValidator extends FieldValidatorSupport {

	/** @Fields idName : id的映射名称 */
	private String						idName;

	/** @Fields roleService : 会籍种类业务接口 */
	private MembershipCategoryService	membershipCategoryService;

	@Resource
	public void setMembershipCategoryService(MembershipCategoryService membershipCategoryService) {
		this.membershipCategoryService = membershipCategoryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.validator.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object obj) throws ValidationException {
		Long id = null;
		String categoryName = null;
		String fieldName = getFieldName();
		Object object = getFieldValue(fieldName, obj);

		if (object == null || "".equals(object)) {
			return;
		}
		categoryName = object.toString();

		object = getFieldValue(this.getIdName(), obj);
		if (object != null) {
			id = Long.valueOf(object.toString());
		}

		if (categoryName == null || "".equals(categoryName)) {
			return;
		}
		MembershipCategory category = new MembershipCategory(id, categoryName);

		if (membershipCategoryService.exists(category)) {
			addFieldError(fieldName, obj);
		}

	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

}
