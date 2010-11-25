package com.tiema.customer.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.customer.dao.CustomerDao;
import com.tiema.customer.entity.Customer;

/**
* @ClassName: CustomerDaoImpl
* @Description: Hibernate实现的CustomerDao
* @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
* @date 2010-11-17 上午01:09:38
*
*/
@Component("customerDao")
public class CustomerDaoImpl extends GenericDaoHibernateImpl<Customer, Long, CustomerDao> implements CustomerDao {

	public CustomerDaoImpl() {
	}


}
