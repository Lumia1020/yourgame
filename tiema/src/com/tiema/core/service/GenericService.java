package com.tiema.core.service;

import java.io.Serializable;
import java.util.List;

import com.tiema.core.orm.Page;

/**
 * @ClassName: GenericService
 * @Description: 泛型服务层接口定义,定义一般service层都需要的方法
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 1, 2010 1:06:10 PM
 * 
 */
public interface GenericService<T, ID extends Serializable> {

	/**
	 * @Title: add
	 * @Description: 增加实体的业务逻辑定义
	 * @param entity 要增加的实体对象
	 * @return T 增加后返回的实体
	 * @throws Exception 
	 */
	public T add(T entity) throws Exception ;

	/**
	 * @Title: findEntities
	 * @Description: 查找实体集合的业务逻辑
	 * @return List<T> 实体对象的集合
	 */
	public List<T> findEntities() ;

	/**
	 * @Title: findById
	 * @Description: 根据id查找实体的业务逻辑
	 * @param id 实体id
	 * @return T 找到的实体对象
	 */
	public T findById(ID id) ;

	/**
	 * @Title: findUnique
	 * @Description: 根据hql查找对象
	 * @param hql 查询语句
	 * @param values 参数
	 */
	public T findUnique(String hql, Object... values);

	/**
	 * @Title: findEntities
	 * @Description: hql查找对象
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<T> findEntities(String hql, Object... values);

	/**
	 * @Title: delete
	 * @Description: 删除实体的业务逻辑
	 * @param entity 需要删除的实体对象
	 */
	public void delete(T entity) ;

	/**
	 * @Title: deleteById
	 * @Description: 根据实体ID删除实体的业务逻辑
	 * @param id 需要删除的实体的id
	 */
	public void deleteById(ID id) ;

	/**
	 * @Title: update
	 * @Description: 更新实体对象
	 * @param entity 需要更新的对象
	 * @return T 更新后的对象
	 */
	public T update(T entity) ;

	/**
	 * @Title: findPage
	 * @Description: 分页查找
	 * @param page 分页条件
	 * @return Page 查询后的分页数据结果
	 */
	public Page findPage(Page page);


}
