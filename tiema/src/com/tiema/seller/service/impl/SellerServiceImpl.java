package com.tiema.seller.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.core.orm.Page;
import com.tiema.seller.dao.SellerDao;
import com.tiema.seller.entity.Seller;
import com.tiema.seller.service.SellerService;
import com.tiema.seller.service.exception.SellerDeleteFailureException;
import com.tiema.seller.service.exception.SellerSaveFailureException;
import com.tiema.seller.service.exception.SellerUpdateFailureException;
import com.tiema.util.MyUtils;

/**
 * @ClassName: SellerServiceImpl
 * @Description: 销售员服务层接口实现
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-19 上午12:03:50
 * 
 */
@Component("sellerService")
public class SellerServiceImpl implements SellerService {

	private SellerDao	sellerDao;

	@Override
	public Seller add(Seller entity) {
		try {
			return sellerDao.save(entity);
		}
		catch (RuntimeException e) {
			throw new SellerSaveFailureException("销售员保存失败!", e);
		}
	}

	@Override
	public void delete(Seller entity) {
		try {
			sellerDao.delete(entity);
		}
		catch (RuntimeException e) {
			throw new SellerDeleteFailureException("销售员删除失败!", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		if (null == id) { throw new IllegalArgumentException("要删除的销售员id不能为空!"); }
		try {
			sellerDao.deleteById(id);
		}
		catch (RuntimeException e) {
			throw new SellerDeleteFailureException("删除销售员失败!", e);
		}
	}

	@Override
	public Seller update(Seller entity) {
		Seller c = sellerDao.findById(entity.getId());
		MyUtils.copyProperties(c, entity);
		try {
			return sellerDao.update(c);
		}
		catch (RuntimeException e) {
			throw new SellerUpdateFailureException("销售员修改发生异常", e);
		}
	}

	@Override
	public Seller findById(Long id) {
		return sellerDao.findById(id);
	}

	@Override
	public List<Seller> findEntities() {
		return sellerDao.findEntities();
	}

	@Override
	public List<Seller> findEntities(String hql, Object... values) {
		return sellerDao.findEntities(hql, values);
	}

	@Override
	public Page findPage(Page page) {
		return sellerDao.findByCriteria(page);
	}

	@Override
	public Seller findUnique(String hql, Object... values) {
		return sellerDao.findUnique(hql, values);
	}

	@Resource
	public void setSellerDao(SellerDao sellerDao) {
		this.sellerDao = sellerDao;
	}

}
