package com.tiema.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tiema.core.constant.State;
import com.tiema.core.orm.Page;
import com.tiema.customer.dao.CustomerDao;
import com.tiema.customer.entity.Customer;
import com.tiema.customer.service.CustomerService;
import com.tiema.customer.service.exception.CustomerDeleteFailureException;
import com.tiema.customer.service.exception.CustomerNumberExistsException;
import com.tiema.customer.service.exception.CustomerSaveFailureException;
import com.tiema.customer.service.exception.CustomerUpdateFailureException;
import com.tiema.membership.category.dao.MembershipCategoryDao;
import com.tiema.membership.category.entity.MembershipCategory;
import com.tiema.util.MyUtils;

/**
 * @ClassName: CustomerServiceImpl
 * @Description: 客户资料管理的业务逻辑实现类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 17, 2010 10:50:02 AM
 * 
 */
@Component("customerService")
public class CustomerServiceImpl implements CustomerService {

	private CustomerDao	customerDao;
	
	private MembershipCategoryDao membershipCategoryDao;

	@Resource
	public void setMembershipCategoryDao(MembershipCategoryDao membershipCategoryDao) {
		this.membershipCategoryDao = membershipCategoryDao;
	}

	@Override
	public Customer add(Customer entity) throws CustomerNumberExistsException {
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		dc.add(Restrictions.eq("customerNumber", entity.getCustomerNumber()));
		dc.add(Restrictions.eq("state", State.VALID));

		List<Customer> list = customerDao.findByCriteria(dc);
		if (list != null && !list.isEmpty()) {
			throw new CustomerNumberExistsException("客户编号 \"" + entity.getCustomerNumber() + "\" 已经存在!");
		}
		try {
			if(null == entity.getMembershipCategory()){
				throw new IllegalArgumentException("会籍种类的id为空!");
			}
			Customer c = customerDao.save(entity);
			MembershipCategory mc = membershipCategoryDao.findById(c.getMembershipCategory().getId()); 
			c.setMembershipCategory(mc);
			return c;
		} catch (RuntimeException e) {
			throw new CustomerSaveFailureException("客户资料保存失败!", e);
		}
	}

	@Override
	public void delete(Customer entity) {
		try {
			customerDao.delete(entity);
		} catch (RuntimeException e) {
			throw new CustomerDeleteFailureException("客户资料删除失败!", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		if (null == id) {
			throw new IllegalArgumentException("要删除的客户资料id不能为空!");
		}
		try {
			customerDao.deleteById(id);
		} catch (RuntimeException e) {
			throw new CustomerDeleteFailureException("删除客户资料失败!", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(Customer customer) {
		String hql = "from Customer r where r.customerNumber = ? and r.state = 1";
		List params = new ArrayList();
		params.add(customer.getCustomerNumber());
		if (customer.getId() != null) {
			hql += " and r.id <> ?";
			params.add(customer.getId());
		}
		List<Customer> list = customerDao.findEntities(hql, params.toArray());
		return list.size() > 0;

	}

	@Override
	public Customer findById(Long id) {
		return customerDao.findById(id);
	}

	@Override
	public List<Customer> findEntities() {
		return customerDao.findEntities();
	}

	@Override
	public List<Customer> findEntities(String hql, Object... values) {
		return customerDao.findEntities(hql, values);
	}

	@Override
	public Page findPage(Page page) {
		return customerDao.findByCriteria(page);
	}

	@Override
	public Customer findUnique(String hql, Object... values) {
		return customerDao.findUnique(hql, values);
	}

	@Resource
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public Customer update(Customer entity) {
		Customer c = customerDao.findById(entity.getId());
		MyUtils.copyProperties(c, entity);
		try {
			customerDao.update(c);
			MembershipCategory mc = membershipCategoryDao.findById(c.getMembershipCategory().getId()); 
			c.setMembershipCategory(mc);
			return c;
		} catch (RuntimeException e) {
			throw new CustomerUpdateFailureException("客户资料修改发生异常", e);
		}
	}

	@Override
	public List<Customer> findEffectiveCustomers() {
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		dc.add(Restrictions.eq("state", State.VALID));
		return customerDao.findByCriteria(dc);
	}

	@Override
	public void invalidate(Long customerId) {
		try {
			customerDao.executeUpdate("update Customer c set c.state = ? where c.id = ?", State.INVALID, customerId);
		} catch (RuntimeException e) {
			throw new CustomerUpdateFailureException("设定客户资料状态时发生异常",e);
		}
	}

}
