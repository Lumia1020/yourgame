package com.tiema.payment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tiema.core.orm.Page;
import com.tiema.payment.dao.PaymentCategoryDao;
import com.tiema.payment.entity.PaymentCategory;
import com.tiema.payment.service.PaymentCategoryService;
import com.tiema.payment.service.exception.PaymentCategoryDeleteFailureException;
import com.tiema.payment.service.exception.PaymentCategorySaveFailureException;
import com.tiema.payment.service.exception.PaymentCategoryUpdateFailureException;
import com.tiema.util.MyUtils;

/**
 * @ClassName: PaymentCategoryServiceImpl
 * @Description: 付款方式种类业务层实现类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 5:43:08 PM
 * 
 */
@Component("paymentCategoryService")
public class PaymentCategoryServiceImpl implements PaymentCategoryService {

	private PaymentCategoryDao	paymentCategoryDao;

	@Override
	public PaymentCategory add(PaymentCategory entity) {
		try {
			return paymentCategoryDao.save(entity);
		} catch (RuntimeException e) {
			throw new PaymentCategorySaveFailureException("付款方式种类保存失败!", e);
		}
	}

	@Override
	public void delete(PaymentCategory entity) {
		try {
			paymentCategoryDao.delete(entity);
		} catch (RuntimeException e) {
			throw new PaymentCategoryDeleteFailureException("付款方式种类删除失败!", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		if (null == id) {
			throw new IllegalArgumentException("要删除的付款方式种类id不能为空!");
		}
		try {
			paymentCategoryDao.deleteById(id);
		} catch (RuntimeException e) {
			throw new PaymentCategoryDeleteFailureException("删除付款方式种类失败!", e);
		}
	}

	@Override
	public PaymentCategory update(PaymentCategory entity) {
		PaymentCategory c = paymentCategoryDao.findById(entity.getId());
		MyUtils.copyProperties(c, entity);
		try {
			return paymentCategoryDao.update(c);
		} catch (RuntimeException e) {
			throw new PaymentCategoryUpdateFailureException("付款方式种类修改发生异常", e);
		}
	}

	@Override
	public PaymentCategory findById(Long id) {
		return paymentCategoryDao.findById(id);
	}

	@Override
	public List<PaymentCategory> findEntities() {
		return paymentCategoryDao.findEntities();
	}

	@Override
	public List<PaymentCategory> findEntities(String hql, Object... values) {
		return paymentCategoryDao.findEntities(hql, values);
	}

	@Override
	public Page findPage(Page page) {
		return paymentCategoryDao.findByCriteria(page);
	}

	@Override
	public PaymentCategory findUnique(String hql, Object... values) {
		return paymentCategoryDao.findUnique(hql, values);
	}

	@Resource
	public void setPaymentCategoryDao(PaymentCategoryDao paymentCategoryDao) {
		this.paymentCategoryDao = paymentCategoryDao;
	}

}
