package com.tiema.agency.price.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.agency.price.dao.AgencyPriceDao;
import com.tiema.agency.price.entity.AgencyPrice;
import com.tiema.agency.price.service.AgencyPriceService;
import com.tiema.agency.price.service.exception.AgencyPriceDeleteFailureException;
import com.tiema.agency.price.service.exception.AgencyPriceSaveFailureException;
import com.tiema.agency.price.service.exception.AgencyPriceUpdateFailureException;
import com.tiema.core.orm.Page;
import com.tiema.util.MyUtils;

/**
* @ClassName: AgencyPriceServiceImpl
* @Description: 中介公司价格管理业务层实现
* @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
* @date 2010-11-23 下午11:22:14
*
*/
@Component("agencyPriceService")
public class AgencyPriceServiceImpl implements AgencyPriceService {

	private AgencyPriceDao	agencyPriceDao;

	@Override
	public AgencyPrice add(AgencyPrice entity) {
		try {
			return agencyPriceDao.save(entity);
		} catch (RuntimeException e) {
			throw new AgencyPriceSaveFailureException("中介公司价格保存失败!", e);
		}
	}

	@Override
	public boolean validateDateRange(AgencyPrice cp, boolean isUpdate) {
		Date startDate = cp.getStartDate();
		Date endDate = cp.getEndDate();
		Long agencyCompanyId = cp.getAgencyCompany().getId();

		String sql1 = "SELECT COUNT(1) COMPANY FROM T_AGENCY_PRICE t WHERE t.AGENCYCOMPANY_ID = ?"; 

		List<Map<String, Object>> list1 = agencyPriceDao.findByNativeSql(sql1, agencyCompanyId);
		BigDecimal c = (BigDecimal) list1.iterator().next().get("COMPANY");
		if (c.intValue() == 0) {
			return true;
		}


		StringBuilder sql = new StringBuilder(" SELECT COUNT(1) RESULT FROM  T_AGENCY_PRICE tt WHERE (");
		sql.append(" (TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') BETWEEN tt.STARTDATE AND tt.ENDDATE) ");
		sql.append(" OR (TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') BETWEEN tt.STARTDATE AND tt.ENDDATE) ");
		sql.append(" OR ((tt.STARTDATE BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')) ");
		sql.append(" AND (tt.ENDDATE BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')))) AND tt.AGENCYCOMPANY_ID = ? ");

		if(isUpdate){
			sql.append(" and tt.ID <> ? ");
		}
		String s = MyUtils.dateToString(startDate, "yyyy-MM-dd");
		String e = MyUtils.dateToString(endDate, "yyyy-MM-dd");

		List<Map<String, Object>> list = null;
		
		if(isUpdate){
			list = agencyPriceDao.findByNativeSql(sql.toString(), s, e, s, e, s, e,cp.getAgencyCompany().getId(),cp.getId());
		}else{
			list = agencyPriceDao.findByNativeSql(sql.toString(), s, e, s, e, s, e,cp.getAgencyCompany().getId());
		}

		Map<String, Object> m = list.iterator().next();
		BigDecimal o = (BigDecimal) m.get("RESULT");

		return o.intValue() == 0;
	}

	@Override
	public void delete(AgencyPrice entity) {
		try {
			agencyPriceDao.delete(entity);
		} catch (RuntimeException e) {
			throw new AgencyPriceDeleteFailureException("中介公司价格删除失败!", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		if (null == id) {
			throw new IllegalArgumentException("要删除的中介公司价格id不能为空!");
		}
		try {
			agencyPriceDao.deleteById(id);
		} catch (RuntimeException e) {
			throw new AgencyPriceDeleteFailureException("删除中介公司价格失败!", e);
		}
	}

	@Override
	public AgencyPrice update(AgencyPrice entity) {
		AgencyPrice c = agencyPriceDao.findById(entity.getId());
		MyUtils.copyProperties(c, entity);
		try {
			return agencyPriceDao.update(c);
		} catch (RuntimeException e) {
			throw new AgencyPriceUpdateFailureException("中介公司价格修改发生异常", e);
		}
	}

	@Override
	public AgencyPrice findById(Long id) {
		return agencyPriceDao.findById(id);
	}

	@Override
	public List<AgencyPrice> findEntities() {
		return agencyPriceDao.findEntities();
	}

	@Override
	public List<AgencyPrice> findEntities(String hql, Object... values) {
		return agencyPriceDao.findEntities(hql, values);
	}

	@Override
	public Page findPage(Page page) {
		return agencyPriceDao.findByCriteria(page);
	}

	@Override
	public AgencyPrice findUnique(String hql, Object... values) {
		return agencyPriceDao.findUnique(hql, values);
	}

	@Resource
	public void setAgencyPriceDao(AgencyPriceDao agencyPriceDao) {
		this.agencyPriceDao = agencyPriceDao;
	}

}
