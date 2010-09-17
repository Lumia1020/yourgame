package com.gvp.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;
import org.springframework.jdbc.core.RowMapper;

import com.gvp.po.User;

public interface IPublicDao{


	/**
	 * 根据示例找
	 * 
	 * @return
	 */
	public List findByExample(Object obj);

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
	public void saveOrUpdate(Object obj);

	/**
	 * 根据hql找用户信息
	 * 
	 * @param hql
	 * @param params 
	 * @return
	 */
	public List findByHQL(String hql, Object[] params);

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

	public Object saveEntity(Object entity);

	public Object findEntityById(Object o, Integer id);

	public Integer executeUpdate(String hql);

	List findByNativeSql(String hql, Class c);

	List findByHQL(String hql);

	List findByNativeSql(String hql, Class c, Object[] params, Type[] types);

	void update(Object instance);

	/**
	 * Spring jdbc的分页
	 * @param sql
	 * @param params
	 * @param rowMapper
	 * @return
	 */
	List<?> findPageBySpringSQL(String sql, Object[] params, RowMapper rowMapper);

	List<?> findPageBySpringSQL(String sql, Object[] params, Class<?> elementType);

	int queryForInt(String sql, Object[] args);

	List<?> findBySpringSQL(String sql, Object[] params);

}
