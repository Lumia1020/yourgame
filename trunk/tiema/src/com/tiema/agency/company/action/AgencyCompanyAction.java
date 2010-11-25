package com.tiema.agency.company.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tiema.agency.company.entity.AgencyCompany;
import com.tiema.agency.company.service.AgencyCompanyService;
import com.tiema.agency.company.service.exception.AgencyCompanyUpdateFailureException;
import com.tiema.core.action.BaseAction;
import com.tiema.core.constant.State;
import com.tiema.core.dao.hibernate.core.EnhancedExample;
import com.tiema.core.orm.Page;
import com.tiema.customer.service.exception.CustomerQueryFailureException;
import com.tiema.customer.service.exception.CustomerSaveFailureException;

/**
 * @ClassName: AgencyCompanyAction
 * @Description: 中介公司管理
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 19, 2010 2:28:41 PM
 * 
 */
@Component
@Scope("prototype")
public class AgencyCompanyAction extends BaseAction {

	private static final Logger		log					= LoggerFactory.getLogger(AgencyCompanyAction.class);

	private static final long		serialVersionUID	= -7599676712761422542L;

	/** @Fields agencyId : 中介公司id */
	private Long					agencyCompanyId;

	/** @Fields agencyCompany : 中介公司实体 */
	private AgencyCompany			agencyCompany;

	/** @Fields agencyCompanies : 中介公司实体集合 */
	private List<AgencyCompany>		agencyCompanies;

	/** @Fields page : 分页数据 */
	private Page					page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean					success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>		jsons				= new HashMap<String, Object>();

	/** @Fields agencyCompanyService : 中介公司业务接口 */
	private AgencyCompanyService	agencyCompanyService;

	/**
	 * @Title: add
	 * @Description: 新增一个中介公司
	 */
	public String add() throws Exception {
		log.debug("新增中介公司开始");
		agencyCompany.setState(State.VALID);
		try {
			setAgencyCompany(agencyCompanyService.add(agencyCompany));
			setSuccess(true);
			log.debug("新增中介公司成功");
		} catch (CustomerSaveFailureException e) {
			log.debug("新增中介公司失败", e);
			jsons.put("messages", e.getMessage());
			this.agencyCompany = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: find
	 * @Description: 查找中介公司的分页信息
	 */
	public String find() {
		DetachedCriteria dc = DetachedCriteria.forClass(AgencyCompany.class);
		if (agencyCompany != null) {
			dc.add(EnhancedExample.createDefaultAll(agencyCompany, true));
		}
		getPage().setCriteria(dc);
		this.setPage(agencyCompanyService.findPage(getPage()));
		return SUCCESS;
	}

	/**
	 * @Title: load
	 * @Description: 加载一个中介公司对象
	 */
	public String load() {
		try {
			setAgencyCompany(agencyCompanyService.findById(agencyCompanyId));
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
	 * @Description: 更新中介公司信息
	 */
	public String update() {
		try {
			setAgencyCompany(agencyCompanyService.update(agencyCompany));
			setSuccess(true);
		} catch (AgencyCompanyUpdateFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
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

	public Map<String, Object> getJsons() {
		return jsons;
	}

	public Page getPage() {
		return page;
	}

	public boolean getSuccess() {
		return success;
	}

	public Long getAgencyCompanyId() {
		return agencyCompanyId;
	}

	public AgencyCompany getAgencyCompany() {
		return agencyCompany;
	}

	public List<AgencyCompany> getAgencyCompanies() {
		return agencyCompanies;
	}

	public void setAgencyCompanyId(Long agencyCompanyId) {
		this.agencyCompanyId = agencyCompanyId;
	}

	public void setAgencyCompany(AgencyCompany agencyCompany) {
		this.agencyCompany = agencyCompany;
	}

	public void setAgencyCompanies(List<AgencyCompany> agencyCompanies) {
		this.agencyCompanies = agencyCompanies;
	}

	@Resource
	public void setAgencyCompanyService(AgencyCompanyService agencyCompanyService) {
		this.agencyCompanyService = agencyCompanyService;
	}

}
