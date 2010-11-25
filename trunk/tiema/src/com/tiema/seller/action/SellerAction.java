package com.tiema.seller.action;

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
import com.tiema.seller.entity.Seller;
import com.tiema.seller.service.SellerService;
import com.tiema.seller.service.exception.SellerQueryFailureException;
import com.tiema.seller.service.exception.SellerSaveFailureException;
import com.tiema.seller.service.exception.SellerUpdateFailureException;

/**
 * @ClassName: SellerAction
 * @Description: 销售员管理
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-19 上午12:10:58
 * 
 */
@Component
@Scope("prototype")
public class SellerAction extends BaseAction {

	private static final long	serialVersionUID	= -2286486642996173823L;

	private static final Logger	log					= LoggerFactory.getLogger(SellerAction.class);

	/** @Fields sellerId : 销售员id */
	private Long				sellerId;

	/** @Fields seller : 销售员实体对象 */
	private Seller				seller;

	/** @Fields sellers : 销售员实体集合 */
	private List<Seller>		sellers;

	/** @Fields page : 分页数据 */
	private Page				page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean				success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>	jsons				= new HashMap<String, Object>();

	/** @Fields sellerService : 销售员业务接口 */
	private SellerService		sellerService;

	/**
	 * @Title: add
	 * @Description: 新增一个销售员
	 */
	public String add() throws Exception {
		log.debug("新增销售员开始");
		seller.setState(State.VALID);
		try {
			setSeller(sellerService.add(seller));
			setSuccess(true);
			log.debug("新增销售员成功");
		} catch (SellerSaveFailureException e) {
			log.debug("新增销售员失败", e);
			jsons.put("messages", e.getMessage());
			this.seller = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: find
	 * @Description: 查找销售员的分页信息
	 */
	public String find() {
		DetachedCriteria dc = DetachedCriteria.forClass(Seller.class);
		if (seller != null) {
			dc.add(EnhancedExample.createDefaultAll(seller, true));
		}
		getPage().setCriteria(dc);
		this.setPage(sellerService.findPage(getPage()));
		return SUCCESS;
	}

	public Map<String, Object> getJsons() {
		return jsons;
	}

	public Page getPage() {
		return page;
	}

	public Seller getSeller() {
		return seller;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public List<Seller> getSellers() {
		return sellers;
	}

	public boolean getSuccess() {
		return success;
	}

	/**
	 * @Title: load
	 * @Description: 加载一个销售员对象
	 */
	public String load() {
		try {
			setSeller(sellerService.findById(sellerId));
			setSuccess(true);
		} catch (SellerQueryFailureException e) {
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

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public void setSellers(List<Seller> sellers) {
		this.sellers = sellers;
	}

	@Resource
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @Title: update
	 * @Description: 更新销售员信息
	 */
	public String update() {
		try {
			setSeller(sellerService.update(seller));
			setSuccess(true);
		} catch (SellerUpdateFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
