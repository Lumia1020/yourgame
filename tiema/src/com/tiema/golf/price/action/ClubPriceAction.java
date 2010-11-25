package com.tiema.golf.price.action;

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
import com.tiema.golf.price.entity.ClubPrice;
import com.tiema.golf.price.service.ClubPriceService;
import com.tiema.golf.price.service.exception.ClubPriceDeleteFailureException;
import com.tiema.golf.price.service.exception.ClubPriceQueryFailureException;
import com.tiema.golf.price.service.exception.ClubPriceSaveFailureException;
import com.tiema.golf.price.service.exception.ClubPriceUpdateFailureException;

/**
 * @ClassName: ClubPriceAction
 * @Description: 俱乐部预定报价管理
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-19 下午11:51:35
 * 
 */
@Component
@Scope("prototype")
public class ClubPriceAction extends BaseAction {

	private static final long	serialVersionUID	= 6957104553503370807L;

	private static final Logger	log					= LoggerFactory.getLogger(ClubPriceAction.class);

	/** @Fields clubPriceId : 俱乐部报价id */
	private Long				clubPriceId;

	/** @Fields clubPrice : 俱乐部报价 */
	private ClubPrice			clubPrice;

	/** @Fields clubPrices : 俱乐部报价集合 */
	private List<ClubPrice>		clubPrices;

	/** @Fields page : 分页数据 */
	private Page				page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean				success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>	jsons				= new HashMap<String, Object>();

	private ClubPriceService	clubPriceService;

	/**
	 * @Title: add
	 * @Description: 新增一个俱乐部报价
	 */
	public String add() throws Exception {
		log.debug("新增俱乐部报价开始");
		clubPrice.setState(State.VALID);
		try {
			setClubPrice(clubPriceService.add(clubPrice));
			setSuccess(true);
			log.debug("新增俱乐部报价成功");
		} catch (ClubPriceSaveFailureException e) {
			log.debug("新增俱乐部报价失败", e);
			jsons.put("messages", e.getMessage());
			this.clubPrice = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String findAllByGolfClubId() {
		String hql = "SELECT new ClubPrice(c.id,c.dailyCost,c.weekdayPrice,c.holidayCost,c.holidayPrice,c.startDate,c.endDate,c.state,c.remark) FROM ClubPrice c WHERE c.golfClub.id = ? ORDER BY c.startDate";
		try {
			this.setClubPrices(clubPriceService.findEntities(hql, clubPrice.getGolfClub().getId()));
			setSuccess(true);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: find
	 * @Description: 查找俱乐部报价的分页信息
	 */
	public String find() {
		DetachedCriteria dc = DetachedCriteria.forClass(ClubPrice.class);
		if (clubPrice != null) {
			dc.add(EnhancedExample.createDefaultAll(clubPrice, true));
		}
		getPage().setCriteria(dc);
		this.setPage(clubPriceService.findPage(getPage()));
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
			clubPriceService.deleteById(clubPriceId);
			setSuccess(true);
		} catch (ClubPriceDeleteFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
		}
		return SUCCESS;
	}

	public ClubPrice getClubPrice() {
		return clubPrice;
	}

	public Long getClubPriceId() {
		return clubPriceId;
	}

	public List<ClubPrice> getClubPrices() {
		return clubPrices;
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
	 * @Description: 加载一个俱乐部报价对象
	 */
	public String load() {
		try {
			setClubPrice(clubPriceService.findById(clubPriceId));
			setSuccess(true);
		} catch (ClubPriceQueryFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setClubPrice(ClubPrice clubPrice) {
		this.clubPrice = clubPrice;
	}

	public void setClubPriceId(Long clubPriceId) {
		this.clubPriceId = clubPriceId;
	}

	public void setClubPrices(List<ClubPrice> clubPrices) {
		this.clubPrices = clubPrices;
	}

	@Resource
	public void setClubPriceService(ClubPriceService clubPriceService) {
		this.clubPriceService = clubPriceService;
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
	 * @Description: 更新俱乐部报价信息
	 */
	public String update() {
		try {
			setClubPrice(clubPriceService.update(clubPrice));
			setSuccess(true);
		} catch (ClubPriceUpdateFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
