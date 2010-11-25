package com.tiema.membership.category.action;

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
import com.tiema.membership.category.entity.MembershipCategory;
import com.tiema.membership.category.service.MembershipCategoryService;
import com.tiema.membership.category.service.exception.MembershipCategoryDeleteFailureException;
import com.tiema.membership.category.service.exception.MembershipCategoryQueryFailureException;
import com.tiema.membership.category.service.exception.MembershipCategorySaveFailureException;
import com.tiema.membership.category.service.exception.MembershipCategoryUpdateFailureException;

/**
 * @ClassName: MembershipCategoryAction
 * @Description: 会籍种类管理
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 4:15:45 PM
 * 
 */
@Component
@Scope("prototype")
public class MembershipCategoryAction extends BaseAction {

	private static final long			serialVersionUID	= 3910730378208076891L;

	private static final Logger			log					= LoggerFactory.getLogger(MembershipCategoryAction.class);

	/** @Fields membershipCategoryId : 会籍种类id */
	private Long						membershipCategoryId;

	/** @Fields membershipCategory : 会籍种类实体 */
	private MembershipCategory			membershipCategory;

	/** @Fields membershipCategories : 会籍种类集合 */
	private List<MembershipCategory>	membershipCategories;

	/** @Fields membershipCategoryService : 会籍种类业务接口 */
	private MembershipCategoryService	membershipCategoryService;

	/** @Fields page : 分页数据 */
	private Page						page;

	/** @Fields success : 表示某个操作的结果 */
	private boolean						success;

	/** @Fields jsons : 返回给前端的json串 */
	private Map<String, Object>			jsons				= new HashMap<String, Object>();

	/**
	 * @Title: add
	 * @Description: 新增一个会籍种类
	 */
	public String add() throws Exception {
		log.debug("新增会籍种类开始");
		membershipCategory.setState(State.VALID);
		try {
			membershipCategoryService.add(membershipCategory);
			setSuccess(true);
			log.debug("新增会籍种类成功");
		} catch (MembershipCategorySaveFailureException e) {
			log.debug("新增会籍种类失败", e);
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: delete
	 * @Description: 删除会籍种类
	 */
	public String delete() {
		try {
			membershipCategoryService.deleteById(membershipCategoryId);
			setSuccess(true);
		} catch (MembershipCategoryDeleteFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: find
	 * @Description: 查找会籍种类的分页信息
	 */
	@SuppressWarnings("unchecked")
	public String find() {
		DetachedCriteria dc = DetachedCriteria.forClass(MembershipCategory.class);
		if (membershipCategory != null) {
			dc.add(EnhancedExample.createDefaultAll(membershipCategory, true));
		}
		getPage().setCriteria(dc);
		this.setPage(membershipCategoryService.findPage(getPage()));
		return SUCCESS;
	}

	/**
	 * @Title: findAll
	 * @Description: 查找所有有效的会籍种类
	 */
	public String findAll() {
		setMembershipCategories(membershipCategoryService.findEffectiveMembershipCategories());
		return SUCCESS;
	}

	public Map<String, Object> getJsons() {
		return jsons;
	}

	public MembershipCategory getMembershipCategory() {
		return membershipCategory;
	}

	public Page getPage() {
		return page;
	}

	public boolean getSuccess() {
		return success;
	}

	/**
	 * @Title: load
	 * @Description: 加载一个会籍种类对象
	 */
	public String load() {
		try {
			setMembershipCategory(membershipCategoryService.findById(membershipCategoryId));
			setSuccess(true);
		} catch (MembershipCategoryQueryFailureException e) {
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
	public void setMembershipCategoryService(MembershipCategoryService membershipCategoryService) {
		this.membershipCategoryService = membershipCategoryService;
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
	 * @Description: 更新会籍种类信息
	 */
	public String update() {
		try {
			setMembershipCategory(membershipCategoryService.update(membershipCategory));
			setSuccess(true);
		} catch (MembershipCategoryUpdateFailureException e) {
			log.error(e.getMessage());
			jsons.put("messages", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setMembershipCategory(MembershipCategory membershipCategory) {
		this.membershipCategory = membershipCategory;
	}

	public Long getMembershipCategoryId() {
		return membershipCategoryId;
	}

	public void setMembershipCategoryId(Long membershipCategoryId) {
		this.membershipCategoryId = membershipCategoryId;
	}

	public List<MembershipCategory> getMembershipCategories() {
		return membershipCategories;
	}

	public void setMembershipCategories(List<MembershipCategory> membershipCategories) {
		this.membershipCategories = membershipCategories;
	}

}
