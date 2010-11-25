package com.tiema.customer.validator;

import javax.annotation.Resource;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.tiema.customer.entity.Customer;
import com.tiema.customer.service.CustomerService;

/**
 * @ClassName: CustomerNumberExistsValidator
 * @Description: 检查有效客户的客户编号是否重复
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 18, 2010 10:27:03 AM
 * 
 */
public class CustomerNumberExistsValidator extends FieldValidatorSupport {

	/** @Fields roleService : 客户资料业务接口 */
	private CustomerService	customerService;

	@Resource
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.validator.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object obj) throws ValidationException {
		String fieldName = getFieldName();
		Object object = getFieldValue(fieldName, obj);

		if (object == null || "".equals(object)) {
			return;
		}
		Customer customer = new Customer(object.toString());

		if (customerService.exists(customer)) {
			addFieldError(fieldName, obj);
		}

	}

}
