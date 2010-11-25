package com.tiema.golf.club.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.core.orm.Page;
import com.tiema.golf.club.dao.GolfClubDao;
import com.tiema.golf.club.entity.GolfClub;
import com.tiema.golf.club.service.GolfClubService;
import com.tiema.golf.club.service.exception.GolfClubDeleteFailureException;
import com.tiema.golf.club.service.exception.GolfClubSaveFailureException;
import com.tiema.golf.club.service.exception.GolfClubUpdateFailureException;
import com.tiema.util.MyUtils;

/**
 * @ClassName: GolfClubServiceImpl
 * @Description: 高尔夫俱乐部管理业务实现
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 18, 2010 3:50:00 PM
 * 
 */
@Component("golfClubService")
public class GolfClubServiceImpl implements GolfClubService {

	private GolfClubDao	golfClubDao;

	@Override
	public GolfClub add(GolfClub entity) {
		try {
			return golfClubDao.save(entity);
		} catch (RuntimeException e) {
			throw new GolfClubSaveFailureException("高尔夫俱乐部保存失败!", e);
		}
	}

	@Override
	public void delete(GolfClub entity) {
		try {
			golfClubDao.delete(entity);
		} catch (RuntimeException e) {
			throw new GolfClubDeleteFailureException("高尔夫俱乐部删除失败!", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		if (null == id) {
			throw new IllegalArgumentException("要删除的高尔夫俱乐部id不能为空!");
		}
		try {
			golfClubDao.deleteById(id);
		} catch (RuntimeException e) {
			throw new GolfClubDeleteFailureException("删除高尔夫俱乐部失败!", e);
		}
	}

	@Override
	public GolfClub update(GolfClub entity) {
		GolfClub c = golfClubDao.findById(entity.getId());
		MyUtils.copyProperties(c, entity);
		try {
			return golfClubDao.update(c);
		} catch (RuntimeException e) {
			throw new GolfClubUpdateFailureException("高尔夫俱乐部修改发生异常", e);
		}
	}

	@Override
	public GolfClub findById(Long id) {
		return golfClubDao.findById(id);
	}

	@Override
	public List<GolfClub> findEntities() {
		return golfClubDao.findEntities();
	}

	@Override
	public List<GolfClub> findEntities(String hql, Object... values) {
		return golfClubDao.findEntities(hql, values);
	}

	@Override
	public Page findPage(Page page) {
		return golfClubDao.findByCriteria(page);
	}

	@Override
	public GolfClub findUnique(String hql, Object... values) {
		return golfClubDao.findUnique(hql, values);
	}

	@Resource
	public void setGolfClubDao(GolfClubDao golfClubDao) {
		this.golfClubDao = golfClubDao;
	}

}
