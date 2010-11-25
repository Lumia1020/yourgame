package com.tiema.agency.price.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tiema.agency.price.entity.AgencyPrice;
import com.tiema.agency.price.service.AgencyPriceService;
import com.tiema.agency.price.service.exception.AgencyPriceDeleteFailureException;
import com.tiema.agency.price.service.exception.AgencyPriceQueryFailureException;
import com.tiema.agency.price.service.exception.AgencyPriceSaveFailureException;
import com.tiema.agency.price.service.exception.AgencyPriceUpdateFailureException;
import com.tiema.core.action.BaseAction;
import com.tiema.core.constant.State;
import com.tiema.core.dao.hibernate.core.EnhancedExample;
import com.tiema.core.orm.Page;

/**
 * @ClassName: AgencyPriceAction
 * @Description: 中介公司价格管理
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-23 下午11:37:59
 * 
 */
@Component
@Scope("prototype")
public class AgencyPriceAction extends BaseAction {

	private static final long	serialVersionUID	= -87372495714912349L;

	private static final Logger	log					= LoggerFactory.getLogger(AgencyPriceAction.class);

	/** @Fields agencyPriceId : 中介公司报价id */
	private Long				agencyPriceId;

	/** @Fields agencyPrice : 中介公司报价 */
	private AgencyPrice			agencyPrice;

	/** @Fields agencyPrices : 中介公司报价集合 */
	private List<AgencyPrice>	agencyPrices;

	/** @Fields page : 分页数据 */
	private Page				page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean				success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>	jsons				= new HashMap<String, Object>();

	private AgencyPriceService	agencyPriceService;

	/**
	 * @Title: add
	 * @Description: 新增一个中介公司报价
	 */
	public String add() throws Exception {
		log.debug("新增中介公司报价开始");
		agencyPrice.setState(State.VALID);
		try {
			setAgencyPrice(agencyPriceService.add(agencyPrice));
			setSuccess(true);
			log.debug("新增中介公司报价成功");
		} catch (AgencyPriceSaveFailureException e) {
			log.debug("新增中介公司报价失败", e);
			jsons.put("messages", e.getMessage());
			this.agencyPrice = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String findAllByAgencyCompanyId() {
		String hql = "SELECT new AgencyPrice(c.id,c.dailyCost,c.weekdayPrice,c.holidayCost,c.holidayPrice,c.startDate,c.endDate,c.state,c.remark) FROM AgencyPrice c WHERE c.agencyCompany.id = ? ORDER BY c.startDate";
		try {
			this.setAgencyPrices(agencyPriceService.findEntities(hql, agencyPrice.getAgencyCompany().getId()));
			setSuccess(true);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: find
	 * @Description: 查找中介公司报价的分页信息
	 */
	public String find() {
		DetachedCriteria dc = DetachedCriteria.forClass(AgencyPrice.class);
		if (agencyPrice != null) {
			dc.add(EnhancedExample.createDefaultAll(agencyPrice, true));
		}
		getPage().setCriteria(dc);
		this.setPage(agencyPriceService.findPage(getPage()));
		return SUCCESS;
	}

	/**
	 * @Title: delete
	 * @Description: 删除价格
	 * @param
	 * @return String
	 * @throws
	 */
	public String delete() {
		try {
			agencyPriceService.deleteById(agencyPriceId);
			setSuccess(true);
		} catch (AgencyPriceDeleteFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
		}
		return SUCCESS;
	}

	public AgencyPrice getAgencyPrice() {
		return agencyPrice;
	}

	public Long getAgencyPriceId() {
		return agencyPriceId;
	}

	public List<AgencyPrice> getAgencyPrices() {
		return agencyPrices;
	}

	public Map<String, Object> getJsons() {
		return jsons;
	}

	public Page getPage() {
		return page;
	}

	public boolean getSuccess() {
		return success;
	}

	/**
	 * @Title: load
	 * @Description: 加载一个中介公司报价对象
	 */
	public String load() {
		try {
			setAgencyPrice(agencyPriceService.findById(agencyPriceId));
			setSuccess(true);
		} catch (AgencyPriceQueryFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAgencyPrice(AgencyPrice clubPrice) {
		this.agencyPrice = clubPrice;
	}

	public void setAgencyPriceId(Long clubPriceId) {
		this.agencyPriceId = clubPriceId;
	}

	public void setAgencyPrices(List<AgencyPrice> clubPrices) {
		this.agencyPrices = clubPrices;
	}

	@Resource
	public void setAgencyPriceService(AgencyPriceService clubPriceService) {
		this.agencyPriceService = clubPriceService;
	}

	public void setJsons(Map<String, Object> jsons) {
		this.jsons = jsons;
	}

	@Resource
	public void setPage(Page page) {
		this.page = page;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @Title: update
	 * @Description: 更新中介公司报价信息
	 */
	public String update() {
		try {
			setAgencyPrice(agencyPriceService.update(agencyPrice));
			setSuccess(true);
		} catch (AgencyPriceUpdateFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
