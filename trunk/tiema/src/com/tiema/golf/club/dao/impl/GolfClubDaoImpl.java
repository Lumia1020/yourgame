package com.tiema.golf.club.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;
import com.tiema.golf.club.dao.GolfClubDao;
import com.tiema.golf.club.entity.GolfClub;

/**
 * @ClassName: GolfClubDaoImpl
 * @Description: Hibernate实现的GolfClubDao
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 18, 2010 3:54:03 PM
 * 
 */
@Component("golfClubDao")
public class GolfClubDaoImpl extends GenericDaoHibernateImpl<GolfClub, Long, GolfClubDao> implements GolfClubDao {

	public GolfClubDaoImpl() {
	}

}
