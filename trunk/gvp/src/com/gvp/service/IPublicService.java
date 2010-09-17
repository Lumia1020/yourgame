package com.gvp.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.hibernate.type.Type;
import org.springframework.jdbc.core.RowMapper;

import com.gvp.core.Page;
import com.gvp.po.PriceList;
import com.gvp.po.QuoteInfo;
import com.gvp.po.User;
import com.gvp.po.WorkflowLog;
import com.gvp.service.vo.PriceListMapper;

public interface IPublicService {
	/**
	 * 登录
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user);

	public Page getResultList(Page page);

	public Boolean deleteEntities(String hql, String[] names, String[] values);

	public Object saveEntity(Object entity,Integer qid);

	public Object updateEntity(Object entity, Integer id,Integer qid) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

	public Object completeQuoteInfo(Map<String, Object> map) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

	public boolean deleteQuoteInfo(String ids);

	public boolean deleteEntities(String hql, Integer qid);

	public Map<String, Object> copyQuoteInfos(Map<String, Object> map) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

	public List getList(Class clazz, Integer qid);

	public Map<String, Object> findQuoteInfoById(Integer qid, Boolean relation);

	public List findByNativeSql(String string, Class clazz);

	public List getList(String hql, Object[] params);

	public List getList(String hql);

	public Object saveEntity(Object entity);

	/**
	 * 调节报时表的供应商材料单价
	 * @param priceList
	 * @return
	 */
	public PriceList adjustQuoteInfos(PriceList priceList);

	public WorkflowLog saveWorkflowLog(WorkflowLog workflow);

	Page getResultListBySpringJDBC(Page page, String sql, Object[] params, RowMapper rowMapper);

	Page getResultListBySpringJDBC(Page page, String sql, Object[] params, Class<?> elementType);

	Page getResultListBySpringJDBC(Page page, String sql, Object[] params);

	List<?> findByNativeSql(String hql, Class<?> c, Object[] params, Type[] types);

	public List<?> findBySpringSql(String string, Object[] params, RowMapper rowMapper);

}
