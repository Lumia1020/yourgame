package com.tiema.membership.category.service;

import java.util.List;

import com.tiema.core.service.GenericService;
import com.tiema.membership.category.entity.MembershipCategory;

/**
 * @ClassName: MembershipCategoryService
 * @Description: 会籍种类业务接口
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 16, 2010 3:53:34 PM
 * 
 */
public interface MembershipCategoryService extends GenericService<MembershipCategory, Long> {

	/**
	 * @Title: exists
	 * @Description: 检查同名的会籍种类名称是否存在,如果membershipCategory对象中只包含了categoryName的value则仅仅查询数据库中是否包含了该名称的会籍种类,如果membershipCategory.id也包含了值的话,就要加上 <>
	 *               membershipCategory.id的判断
	 */
	public boolean exists(MembershipCategory membershipCategory);

	/**
	 * @Title: findEffectiveMembershipCategories
	 * @Description: 查找所有有效的会籍种类,有效会籍种类的状态为 State.VALID (VALID_STATE = 1)
	 * @return List<MembershipCategory> 会籍种类集合
	 */
	public List<MembershipCategory> findEffectiveMembershipCategories();

}
