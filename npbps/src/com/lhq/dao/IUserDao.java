package com.lhq.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lhq.po.User;

public interface IUserDao {

	/**
	 * 根据示例查找用户集合
	 * 
	 * @param user
	 * @return
	 */
	public List findByExample(User user);

	/**
	 * 根据属性value找
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List findByProperty(String propertyName, Object value);

	/**
	 * 保存或者更新信息
	 * 
	 * @param user
	 */
	public void saveOrUpdate(User user);

	/**
	 * 根据hql找用户信息
	 * 
	 * @param hql
	 * @return
	 */
	public List findByHQL(String hql);

	/**
	 * 根据离线查询用户记录总数
	 * 
	 * @param dc
	 * @return
	 */
	public int findByCount(DetachedCriteria dc);

	/**
	 * 根据离线查用户集合
	 * 
	 * @param dc
	 * @return
	 */
	public List findByCriteria(DetachedCriteria dc);

	/**
	 * 根据离线分页
	 * 
	 * @param dc
	 * @param start
	 * @param limit
	 * @return
	 */
	public List findByCriteria(DetachedCriteria dc, int start, int limit);

	/**
	 * <code>
	 * String hqlUpdate = "update Customer set name = :newName where name = :oldName";
	 * //names:[newName,oldName] value:['abc','cba']
	 * </code>
	 * 
	 * @param hql
	 *            要执行的语句
	 * @param names
	 *            参数名称
	 * @param values
	 *            参数value
	 * @return
	 */
	public Integer executeUpdate(String hql, String[] names, String[] values);

	public User saveUser(User user);

	public User findById(Integer id);

	public Integer executeUpdate(String hql);

}
