package com.tiema.golf.price.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.golf.price.dao.ClubPriceDao;
import com.tiema.golf.price.entity.ClubPrice;

/**
 * @ClassName: ClubPriceDaoImpl
 * @Description: Hibernate实现的dao
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-20 上午12:59:39
 * 
 */
@Component("clubPriceDao")
public class ClubPriceDaoImpl extends GenericDaoHibernateImpl<ClubPrice, Long, ClubPriceDao> implements ClubPriceDao {

	public ClubPriceDaoImpl() {
	}

}
