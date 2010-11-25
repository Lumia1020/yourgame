package com.tiema.payment.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tiema.core.action.BaseAction;
import com.tiema.core.constant.State;
import com.tiema.core.dao.hibernate.core.EnhancedExample;
import com.tiema.core.orm.Page;
import com.tiema.customer.service.exception.CustomerQueryFailureException;
import com.tiema.customer.service.exception.CustomerSaveFailureException;
import com.tiema.payment.entity.PaymentCategory;
import com.tiema.payment.service.PaymentCategoryService;
import com.tiema.payment.service.exception.PaymentCategoryUpdateFailureException;

/**
 * @ClassName: PaymentCategoryAction
 * @Description: 付款方式种类管理
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 5:47:57 PM
 * 
 */
@Component
@Scope("prototype")
public class PaymentCategoryAction extends BaseAction {

	private static final long		serialVersionUID	= 5949605418556262923L;

	private static final Logger		log					= LoggerFactory.getLogger(PaymentCategoryAction.class);

	/** @Fields paymentCategoryId : 付款方式种类id */
	private Long					paymentCategoryId;

	/** @Fields paymentCategory : 付款方式种类 */
	private PaymentCategory			paymentCategory;

	/** @Fields paymentCategories : 付款方式种类集合 */
	private List<PaymentCategory>	paymentCategories;

	/** @Fields page : 分页数据 */
	private Page					page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean					success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>		jsons				= new HashMap<String, Object>();

	private PaymentCategoryService	paymentCategoryService;

	/**
	 * @Title: add
	 * @Description: 新增一个付款方式种类
	 */
	public String add() throws Exception {
		log.debug("新增付款方式种类开始");
		paymentCategory.setState(State.VALID);
		try {
			setPaymentCategory(paymentCategoryService.add(paymentCategory));
			setSuccess(true);
			log.debug("新增付款方式种类成功");
		} catch (CustomerSaveFailureException e) {
			log.debug("新增付款方式种类失败", e);
			jsons.put("messages", e.getMessage());
			this.paymentCategory = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: find
	 * @Description: 查找付款方式种类的分页信息
	 */
	public String find() {
		DetachedCriteria dc = DetachedCriteria.forClass(PaymentCategory.class);
		if (paymentCategory != null) {
			dc.add(EnhancedExample.createDefaultAll(paymentCategory, true));
		}
		getPage().setCriteria(dc);
		this.setPage(paymentCategoryService.findPage(getPage()));
		return SUCCESS;
	}

	/**
	 * @Title: load
	 * @Description: 加载一个付款方式种类对象
	 */
	public String load() {
		try {
			setPaymentCategory(paymentCategoryService.findById(paymentCategoryId));
			setSuccess(true);
		} catch (CustomerQueryFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: update
	 * @Description: 更新付款方式种类信息
	 */
	public String update() {
		try {
			setPaymentCategory(paymentCategoryService.update(paymentCategory));
			setSuccess(true);
		} catch (PaymentCategoryUpdateFailureException e) {
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

	public Long getPaymentCategoryId() {
		return paymentCategoryId;
	}

	public PaymentCategory getPaymentCategory() {
		return paymentCategory;
	}

	public List<PaymentCategory> getPaymentCategories() {
		return paymentCategories;
	}

	public void setPaymentCategoryId(Long paymentCategoryId) {
		this.paymentCategoryId = paymentCategoryId;
	}

	public void setPaymentCategory(PaymentCategory paymentCategory) {
		this.paymentCategory = paymentCategory;
	}

	public void setPaymentCategories(List<PaymentCategory> paymentCategories) {
		this.paymentCategories = paymentCategories;
	}

	@Resource
	public void setPaymentCategoryService(PaymentCategoryService paymentCategoryService) {
		this.paymentCategoryService = paymentCategoryService;
	}

}
