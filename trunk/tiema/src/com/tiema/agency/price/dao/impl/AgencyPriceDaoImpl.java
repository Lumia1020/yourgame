package com.tiema.agency.price.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.agency.price.dao.AgencyPriceDao;
import com.tiema.agency.price.entity.AgencyPrice;
import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;

/**
 * @ClassName: AgencyPriceDaoImpl
 * @Description: Hibernate实现的dao
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-20 上午12:59:39
 * 
 */
@Component("agencyPriceDao")
public class AgencyPriceDaoImpl extends GenericDaoHibernateImpl<AgencyPrice, Long, AgencyPriceDao> implements AgencyPriceDao {

	public AgencyPriceDaoImpl() {
	}

}
