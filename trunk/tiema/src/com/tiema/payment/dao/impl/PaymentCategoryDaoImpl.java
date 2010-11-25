package com.tiema.payment.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.payment.dao.PaymentCategoryDao;
import com.tiema.payment.entity.PaymentCategory;

/**
 * @ClassName: PaymentCategoryDaoImpl
 * @Description: Hibernate实现
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 5:41:23 PM
 * 
 */
@Component("paymentCategoryDao")
public class PaymentCategoryDaoImpl extends GenericDaoHibernateImpl<PaymentCategory, Long, PaymentCategoryDao> implements PaymentCategoryDao {

	public PaymentCategoryDaoImpl() {
	}

}
