package com.tiema.membership.category.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.membership.category.dao.MembershipCategoryDao;
import com.tiema.membership.category.entity.MembershipCategory;

/**
 * @ClassName: MembershipCategoryDaoImpl
 * @Description: Hibernate实现的MembershipCategoryDao
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 3:52:57 PM
 * 
 */
@Component("membershipCategoryDao")
public class MembershipCategoryDaoImpl extends GenericDaoHibernateImpl<MembershipCategory, Long, MembershipCategoryDao> implements MembershipCategoryDao {

	public MembershipCategoryDaoImpl() {
	}

}
