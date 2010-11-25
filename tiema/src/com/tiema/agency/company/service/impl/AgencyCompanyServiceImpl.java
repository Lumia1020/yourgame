package com.tiema.agency.company.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.agency.company.dao.AgencyCompanyDao;
import com.tiema.agency.company.entity.AgencyCompany;
import com.tiema.agency.company.service.AgencyCompanyService;
import com.tiema.agency.company.service.exception.AgencyCompanyDeleteFailureException;
import com.tiema.agency.company.service.exception.AgencyCompanySaveFailureException;
import com.tiema.agency.company.service.exception.AgencyCompanyUpdateFailureException;
import com.tiema.core.orm.Page;
import com.tiema.util.MyUtils;

/**
 * @ClassName: AgencyCompanyServiceImpl
 * @Description: 中介公司管理业务实现
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 19, 2010 2:36:41 PM
 * 
 */
@Component("agencyCompanyService")
public class AgencyCompanyServiceImpl implements AgencyCompanyService {

	private AgencyCompanyDao	agencyCompanyDao;

	@Override
	public AgencyCompany add(AgencyCompany entity) {
		try {
			return agencyCompanyDao.save(entity);
		}
		catch (RuntimeException e) {
			throw new AgencyCompanySaveFailureException("中介公司保存失败!", e);
		}
	}

	@Override
	public void delete(AgencyCompany entity) {
		try {
			agencyCompanyDao.delete(entity);
		}
		catch (RuntimeException e) {
			throw new AgencyCompanyDeleteFailureException("中介公司删除失败!", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		if (null == id) { throw new IllegalArgumentException("要删除的中介公司id不能为空!"); }
		try {
			agencyCompanyDao.deleteById(id);
		}
		catch (RuntimeException e) {
			throw new AgencyCompanyDeleteFailureException("删除中介公司失败!", e);
		}
	}

	@Override
	public AgencyCompany update(AgencyCompany entity) {
		AgencyCompany c = agencyCompanyDao.findById(entity.getId());
		MyUtils.copyProperties(c, entity);
		try {
			return agencyCompanyDao.update(c);
		}
		catch (RuntimeException e) {
			throw new AgencyCompanyUpdateFailureException("中介公司修改发生异常", e);
		}
	}

	@Override
	public AgencyCompany findById(Long id) {
		return agencyCompanyDao.findById(id);
	}

	@Override
	public List<AgencyCompany> findEntities() {
		return agencyCompanyDao.findEntities();
	}

	@Override
	public List<AgencyCompany> findEntities(String hql, Object... values) {
		return agencyCompanyDao.findEntities(hql, values);
	}

	@Override
	public Page findPage(Page page) {
		return agencyCompanyDao.findByCriteria(page);
	}

	@Override
	public AgencyCompany findUnique(String hql, Object... values) {
		return agencyCompanyDao.findUnique(hql, values);
	}

	@Resource
	public void setAgencyDao(AgencyCompanyDao agencyCompanyDao) {
		this.agencyCompanyDao = agencyCompanyDao;
	}

}
