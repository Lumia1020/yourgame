package com.tiema.customer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tiema.core.action.BaseAction;
import com.tiema.core.constant.State;
import com.tiema.core.dao.hibernate.core.EnhancedExample;
import com.tiema.core.orm.Page;
import com.tiema.customer.entity.Customer;
import com.tiema.customer.service.CustomerService;
import com.tiema.customer.service.exception.CustomerNumberExistsException;
import com.tiema.customer.service.exception.CustomerQueryFailureException;
import com.tiema.customer.service.exception.CustomerSaveFailureException;
import com.tiema.customer.service.exception.CustomerUpdateFailureException;

/**
 * @ClassName: CustomerAction
 * @Description: 客户管理用来管理客户资料,包括收集客户录入客户信息,根据情况对客户资料进行维护,维护包括修改客户资料,删除客户资料
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 17, 2010 9:54:34 AM
 * 
 */
@Component
@Scope("prototype")
public class CustomerAction extends BaseAction {

	private static final long	serialVersionUID	= -4845015122561501560L;

	private static final Logger	log					= LoggerFactory.getLogger(CustomerAction.class);

	/** @Fields customerId : 客户资料id */
	private Long				customerId;

	/** @Fields customer : 客户资料实体 */
	private Customer			customer;

	/** @Fields customers : 客户资料集合 */
	private List<Customer>		customers;

	/** @Fields page : 分页数据 */
	private Page				page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean				success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>	jsons				= new HashMap<String, Object>();

	/** @Fields roleService : 客户资料业务接口 */
	private CustomerService		customerService;

	/**
	 * @Title: add
	 * @Description: 新增一个客户资料
	 */
	public String add() throws Exception {
		log.debug("新增客户资料开始");
		customer.setState(State.VALID);
		try {
			setCustomer(customerService.add(customer));
			setSuccess(true);
			log.debug("新增客户资料成功");
		} catch (IllegalArgumentException e) {
			jsons.put("messages", e.getMessage());
			this.customer = null;
			e.printStackTrace();
		} catch (CustomerSaveFailureException e) {
			log.debug("新增客户资料失败", e);
			jsons.put("messages", e.getMessage());
			this.customer = null;
			e.printStackTrace();
		} catch (CustomerNumberExistsException e) {
			log.debug("客户编码已经存在", e);
			jsons.put("messages", e.getMessage());
			this.customer = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: find
	 * @Description: 查找客户资料的分页信息
	 */
	public String find() {
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		dc.add(Restrictions.eq("state", State.VALID)); //默认只查找有效的客户资料
		if (customer != null) {
			dc.add(EnhancedExample.createDefaultAll(customer, true));
		}
		getPage().setCriteria(dc);
		this.setPage(customerService.findPage(getPage()));
		return SUCCESS;
	}


	/**
	 * @Title: load
	 * @Description: 加载一个客户资料对象
	 */
	public String load() {
		try {
			setCustomer(customerService.findById(customerId));
			setSuccess(true);
		} catch (CustomerQueryFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: invalidate
	 * @Description: 设定客户资料无效
	 */
	public String invalidate() {
		try {
			customerService.invalidate(customerId);
			setSuccess(true);
		} catch (CustomerUpdateFailureException e) {
			log.debug("设定客户资料无效时,发生异常!", e);
			this.jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: update
	 * @Description: 更新客户资料信息
	 */
	public String update() {
		try {
			setCustomer(customerService.update(customer));
			setSuccess(true);
		} catch (CustomerUpdateFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Page getPage() {
		return page;
	}

	@Resource
	public void setPage(Page page) {
		this.page = page;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, Object> getJsons() {
		return jsons;
	}

	public void setJsons(Map<String, Object> jsons) {
		this.jsons = jsons;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	@Resource
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

}
