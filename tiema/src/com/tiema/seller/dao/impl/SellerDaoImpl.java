package com.tiema.seller.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.seller.dao.SellerDao;
import com.tiema.seller.entity.Seller;

/**
 * @ClassName: SellerDaoImpl
 * @Description: Hibernate实现的SellerDao
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-18 下午11:52:51
 * 
 */
@Component("sellerDao")
public class SellerDaoImpl extends GenericDaoHibernateImpl<Seller, Long, SellerDao> implements SellerDao {

	public SellerDaoImpl() {
	}

}
