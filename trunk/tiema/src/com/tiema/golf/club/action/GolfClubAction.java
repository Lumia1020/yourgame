package com.tiema.golf.club.action;

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
import com.tiema.golf.club.entity.GolfClub;
import com.tiema.golf.club.service.GolfClubService;
import com.tiema.golf.club.service.exception.GolfClubUpdateFailureException;

/**
 * @ClassName: GolfClubAction
 * @Description: 高尔夫俱乐部管理
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 18, 2010 3:41:13 PM
 * 
 */
@Component
@Scope("prototype")
public class GolfClubAction extends BaseAction {

	private static final long	serialVersionUID	= 7404269779898010541L;

	private static final Logger	log					= LoggerFactory.getLogger(GolfClubAction.class);

	/** @Fields golfClubId : 高尔夫俱乐部id */
	private Long				golfClubId;

	/** @Fields golfClub : 高尔夫俱乐部实体 */
	private GolfClub			golfClub;

	/** @Fields golfClubs : 高尔夫俱乐部集合 */
	private List<GolfClub>		golfClubs;

	/** @Fields page : 分页数据 */
	private Page				page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean				success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>	jsons				= new HashMap<String, Object>();

	/** @Fields golfClubService : 高尔夫俱乐部业务接口 */
	private GolfClubService		golfClubService;

	/**
	 * @Title: add
	 * @Description: 新增一个高尔夫俱乐部
	 */
	public String add() throws Exception {
		log.debug("新增高尔夫俱乐部开始");
		golfClub.setState(State.VALID);
		try {
			setGolfClub(golfClubService.add(golfClub));
			setSuccess(true);
			log.debug("新增高尔夫俱乐部成功");
		} catch (CustomerSaveFailureException e) {
			log.debug("新增高尔夫俱乐部失败", e);
			jsons.put("messages", e.getMessage());
			this.golfClub = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: find
	 * @Description: 查找高尔夫俱乐部的分页信息
	 */
	public String find() {
		DetachedCriteria dc = DetachedCriteria.forClass(GolfClub.class);
		if (golfClub != null) {
			dc.add(EnhancedExample.createDefaultAll(golfClub, true));
		}
		getPage().setCriteria(dc);
		this.setPage(golfClubService.findPage(getPage()));
		return SUCCESS;
	}

	/**
	 * @Title: load
	 * @Description: 加载一个高尔夫俱乐部对象
	 */
	public String load() {
		try {
			setGolfClub(golfClubService.findById(golfClubId));
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
	 * @Description: 更新高尔夫俱乐部信息
	 */
	public String update() {
		try {
			setGolfClub(golfClubService.update(golfClub));
			setSuccess(true);
		} catch (GolfClubUpdateFailureException e) {
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

	public Long getGolfClubId() {
		return golfClubId;
	}

	public void setGolfClubId(Long golfClubId) {
		this.golfClubId = golfClubId;
	}

	public GolfClub getGolfClub() {
		return golfClub;
	}

	public void setGolfClub(GolfClub golfClub) {
		this.golfClub = golfClub;
	}

	public List<GolfClub> getGolfClubs() {
		return golfClubs;
	}

	public void setGolfClubs(List<GolfClub> golfClubs) {
		this.golfClubs = golfClubs;
	}

	public Map<String, Object> getJsons() {
		return jsons;
	}

	public void setJsons(Map<String, Object> jsons) {
		this.jsons = jsons;
	}

	@Resource
	public void setGolfClubService(GolfClubService golfClubService) {
		this.golfClubService = golfClubService;
	}

}
