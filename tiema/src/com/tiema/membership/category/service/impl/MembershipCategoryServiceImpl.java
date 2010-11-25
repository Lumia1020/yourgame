package com.tiema.membership.category.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tiema.core.constant.State;
import com.tiema.core.orm.Page;
import com.tiema.membership.category.dao.MembershipCategoryDao;
import com.tiema.membership.category.entity.MembershipCategory;
import com.tiema.membership.category.service.MembershipCategoryService;
import com.tiema.membership.category.service.exception.MembershipCategoryDeleteFailureException;
import com.tiema.membership.category.service.exception.MembershipCategoryQueryFailureException;
import com.tiema.membership.category.service.exception.MembershipCategorySaveFailureException;
import com.tiema.membership.category.service.exception.MembershipCategoryUpdateFailureException;
import com.tiema.seller.entity.Seller;
import com.tiema.util.MyUtils;

/**
 * @ClassName: RoleServiceImpl
 * @Description: 角色业务逻辑实行类
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 1, 2010 2:42:16 PM
 * 
 */
@Component("membershipCategoryService")
public class MembershipCategoryServiceImpl implements MembershipCategoryService {

	private MembershipCategoryDao	membershipCategoryDao;

	@Override
	public MembershipCategory add(MembershipCategory entity) {
		try {
			return membershipCategoryDao.save(entity);
		} catch (RuntimeException e) {
			throw new MembershipCategorySaveFailureException("会籍种类保存失败!", e);
		}
	}

	@Override
	public void delete(MembershipCategory entity) {
		try {
			membershipCategoryDao.delete(entity);
		} catch (RuntimeException e) {
			throw new MembershipCategoryDeleteFailureException("会籍种类删除失败!", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		if (null == id) {
			throw new IllegalArgumentException("要删除的会籍种类id不能为空!");
		}

		try {
			membershipCategoryDao.deleteById(id);
		} catch (RuntimeException e) {
			throw new MembershipCategoryDeleteFailureException("会籍种类删除失败!", e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean exists(MembershipCategory membershipCategory) {
		String hql = "from MembershipCategory r where r.categoryName = ?";
		List params = new ArrayList();
		params.add(membershipCategory.getCategoryName());
		if (membershipCategory.getId() != null) {
			hql += " and r.id <> ?";
			params.add(membershipCategory.getId());
		}
		List<MembershipCategory> list = membershipCategoryDao.findEntities(hql, params.toArray());
		return list.size() > 0;
	}

	@Override
	public MembershipCategory findById(Long id) {
		if (null == id) {
			throw new IllegalArgumentException("要查找的会籍种类id不能为空!");
		}
		try {
			return membershipCategoryDao.findById(id);
		} catch (RuntimeException e) {
			throw new MembershipCategoryQueryFailureException("会籍种类信息查找失败!", e);
		}
	}

	@Override
	public List<MembershipCategory> findEntities() {
		return membershipCategoryDao.findEntities();
	}

	public List<MembershipCategory> findEffectiveMembershipCategories() {
		DetachedCriteria dc = DetachedCriteria.forClass(MembershipCategory.class);
		dc.add(Restrictions.eq("state", State.VALID));
		return membershipCategoryDao.findByCriteria(dc);
	}

	@Override
	public List<MembershipCategory> findEntities(String hql, Object... values) {
		return membershipCategoryDao.findEntities(hql, values);
	}

	@Override
	public Page findPage(Page page) {
		return membershipCategoryDao.findByCriteria(page);
	}

	@Override
	public MembershipCategory findUnique(String hql, Object... values) {
		return membershipCategoryDao.findUnique(hql, values);
	}

	@Resource
	public void setMembershipCategoryDao(MembershipCategoryDao membershipCategoryDao) {
		this.membershipCategoryDao = membershipCategoryDao;
	}

	@Override
	public MembershipCategory update(MembershipCategory entity) {
		MembershipCategory c = membershipCategoryDao.findById(entity.getId());
		MyUtils.copyProperties(c, entity);
		try {
			return membershipCategoryDao.update(c);
		} catch (RuntimeException e) {
			throw new MembershipCategoryUpdateFailureException("会籍资料更新失败!", e);
		}
	}

}
