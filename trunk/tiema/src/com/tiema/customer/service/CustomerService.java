package com.tiema.customer.service;

import java.util.List;

import com.tiema.core.service.GenericService;
import com.tiema.customer.entity.Customer;

/**
* @ClassName: CustomerService
* @Description: 客户资料管理业务接口
* @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
* @date 2010-11-17 上午01:14:06
*
*/
public interface CustomerService extends GenericService<Customer, Long> {

	/**
	 * @Title: exists
	 * @Description: 检查有效状态下的客户资料中是否有相同客户编号的数据
	 */
	public boolean exists(Customer customer);

	/**
	 * @Title: findEffectiveMembershipCategories
	 * @Description: 查找所有有效的客户资料,有效客户资料的状态为 State.VALID (VALID_STATE = 1)
	 * @return List<MembershipCategory> 客户资料集合
	 */
	public List<Customer> findEffectiveCustomers();

	/**
	 * @Title: invalidate
	 * @Description: 对指定客户资料做无效处理
	 * @param customerId 客户资料id
	 */
	public void invalidate(Long customerId);

}
