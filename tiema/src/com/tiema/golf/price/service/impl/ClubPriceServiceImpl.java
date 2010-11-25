package com.tiema.golf.price.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.core.orm.Page;
import com.tiema.golf.price.dao.ClubPriceDao;
import com.tiema.golf.price.entity.ClubPrice;
import com.tiema.golf.price.service.ClubPriceService;
import com.tiema.golf.price.service.exception.ClubPriceDeleteFailureException;
import com.tiema.golf.price.service.exception.ClubPriceSaveFailureException;
import com.tiema.golf.price.service.exception.ClubPriceUpdateFailureException;
import com.tiema.util.MyUtils;

/**
 * @ClassName: AgencyPriceServiceImpl
 * @Description: 俱乐部报价管理业务层实现
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-20 上午01:02:21
 * 
 */
@Component("clubPriceService")
public class ClubPriceServiceImpl implements ClubPriceService {

	private ClubPriceDao	clubPriceDao;

	@Override
	public ClubPrice add(ClubPrice entity) {
		try {
			return clubPriceDao.save(entity);
		} catch (RuntimeException e) {
			throw new ClubPriceSaveFailureException("高尔夫俱乐部价格保存失败!", e);
		}
	}

	@Override
	public boolean validateDateRange(ClubPrice cp, boolean isUpdate) {
		Date startDate = cp.getStartDate();
		Date endDate = cp.getEndDate();
		Long golfClubId = cp.getGolfClub().getId();

		String sql1 = "SELECT COUNT(1) CLUB FROM T_CLUB_PRICE t WHERE t.GOLFCLUB_ID = ?"; //检查表里是不是有这个球会的记录

		List<Map<String, Object>> list1 = clubPriceDao.findByNativeSql(sql1, golfClubId);
		BigDecimal c = (BigDecimal) list1.iterator().next().get("CLUB");
		if (c.intValue() == 0) {
			return true;
		}


		StringBuilder sql = new StringBuilder(" SELECT COUNT(1) RESULT FROM  T_CLUB_PRICE tt WHERE (");
		sql.append(" (TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') BETWEEN tt.STARTDATE AND tt.ENDDATE) ");
		sql.append(" OR (TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') BETWEEN tt.STARTDATE AND tt.ENDDATE) ");
		sql.append(" OR ((tt.STARTDATE BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')) ");
		sql.append(" AND (tt.ENDDATE BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')))) AND tt.GOLFCLUB_ID = ? ");

		if(isUpdate){
			sql.append(" and tt.ID <> ? ");
		}
		String s = MyUtils.dateToString(startDate, "yyyy-MM-dd");
		String e = MyUtils.dateToString(endDate, "yyyy-MM-dd");

		List<Map<String, Object>> list = null;
		
		if(isUpdate){
			list = clubPriceDao.findByNativeSql(sql.toString(), s, e, s, e, s, e,cp.getGolfClub().getId(),cp.getId());
		}else{
			list = clubPriceDao.findByNativeSql(sql.toString(), s, e, s, e, s, e,cp.getGolfClub().getId());
		}

		Map<String, Object> m = list.iterator().next();
		BigDecimal o = (BigDecimal) m.get("RESULT");

		return o.intValue() == 0;
	}

	@Override
	public void delete(ClubPrice entity) {
		try {
			clubPriceDao.delete(entity);
		} catch (RuntimeException e) {
			throw new ClubPriceDeleteFailureException("高尔夫俱乐部价格删除失败!", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		if (null == id) {
			throw new IllegalArgumentException("要删除的高尔夫俱乐部价格id不能为空!");
		}
		try {
			clubPriceDao.deleteById(id);
		} catch (RuntimeException e) {
			throw new ClubPriceDeleteFailureException("删除高尔夫俱乐部价格失败!", e);
		}
	}

	@Override
	public ClubPrice update(ClubPrice entity) {
		ClubPrice c = clubPriceDao.findById(entity.getId());
		MyUtils.copyProperties(c, entity);
		try {
			return clubPriceDao.update(c);
		} catch (RuntimeException e) {
			throw new ClubPriceUpdateFailureException("高尔夫俱乐部价格修改发生异常", e);
		}
	}

	@Override
	public ClubPrice findById(Long id) {
		return clubPriceDao.findById(id);
	}

	@Override
	public List<ClubPrice> findEntities() {
		return clubPriceDao.findEntities();
	}

	@Override
	public List<ClubPrice> findEntities(String hql, Object... values) {
		return clubPriceDao.findEntities(hql, values);
	}

	@Override
	public Page findPage(Page page) {
		return clubPriceDao.findByCriteria(page);
	}

	@Override
	public ClubPrice findUnique(String hql, Object... values) {
		return clubPriceDao.findUnique(hql, values);
	}

	@Resource
	public void setClubPriceDao(ClubPriceDao clubPriceDao) {
		this.clubPriceDao = clubPriceDao;
	}

}
