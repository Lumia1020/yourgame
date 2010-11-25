package com.tiema.agency.company.dao.impl;

import org.springframework.stereotype.Component;

import com.tiema.agency.company.dao.AgencyCompanyDao;
import com.tiema.agency.company.entity.AgencyCompany;
import com.tiema.core.dao.hibernate.impl.GenericDaoHibernateImpl;

/**
 * @ClassName: AgencyCompanyDaoImpl
 * @Description: Hibernate实现的AgencyDao
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 19, 2010 2:32:42 PM
 * 
 */
@Component("agencyCompanyDao")
public class AgencyCompanyDaoImpl extends GenericDaoHibernateImpl<AgencyCompany, Long, AgencyCompanyDao> implements AgencyCompanyDao {

	public AgencyCompanyDaoImpl() {
	}

}
