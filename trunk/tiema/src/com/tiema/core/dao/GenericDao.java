package com.tiema.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.tiema.core.orm.Page;

/**
 * @ClassName: GenericDao
 * @Description: 负责域对象的创建、读取（按主键）、更新和删除（creations, reads, updates, and deletions，CRUD）。
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 26, 2010 12:22:18 AM
 */
public interface GenericDao<T, ID extends Serializable> {

	/**
	 * @Title: delete
	 * @Description: 从数据库中删除指定对象.Remove an object from persistent storage in the database
	 * @param entity
	 */
	public void delete(T entity);

	/**
	 * @Title: deleteAll
	 * @Description: 批量删除集合中的实体对象
	 * @param entities 实体集合
	 * @return void
	 */
	public void deleteAll(Collection<T> entities);

	/**
	 * @Title: deleteById
	 * @Description: 根据id删除数据库实体对象
	 * @param id 实体id
	 * @return void
	 */
	public void deleteById(ID id);

	/**
	 * @Title: deleteByNativeSql
	 * @Description: 根据原生语句删除数据
	 * @param sql 删除数据的sql
	 * @param params 删除数据sql的参数值
	 * @return boolean 是否成功删除
	 */
	public boolean executeByNativeSql(String sql, Object... params);

	/**
	 * @Title: findByCriteria
	 * @Description: 通过动态查询条件查询
	 * @param criterion 动态查询条件
	 * @return List<T> 对象集合
	 */
	public List<T> findByCriteria(Criterion... criterion);

	/**
	 * @Title: findByCriteria
	 * @Description: 通过离线查询对象检索实体集合
	 * @param dc 离线查询对象
	 * @return List<T> 对象集合
	 */
	public List<T> findByCriteria(DetachedCriteria criteria);

	/**
	 * @Title: findByCriteria
	 * @Description: 查找分页数据
	 * @param page 分页信息
	 * @return Page 分页后的结果对象
	 */
	public Page findByCriteria(Page page);

	/**
	 * @Title: findByCriteria
	 * @Description: 通过离线查询对象查询分页数据
	 * @param dc 离线查询对象
	 * @param firstResult 开始记录号
	 * @param maxResults 每页记录数
	 * @return List<T> 对象集合
	 */
	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

	/**
	 * @Title: findByCriteria
	 * @Description: 通过在线查询对象查询分页数据
	 * @param firstResult 开始记录号
	 * @param maxResults 每页记录数
	 * @param order 排序方式
	 * @param criterions 动态条件
	 * @return List<T>
	 * @throws
	 */
	public List<T> findByCriteria(int firstResult, int maxResults, Order order, Criterion... criterions);

	/**
	 * @Title: findByCriteria
	 * @Description: 通过动态查询条件查询，增加了排序功能
	 * @param order 排序条件
	 * @param criterion 动态查询条件
	 * @return List<T> 对象集合
	 */
	public List<T> findByCriteria(Order order, Criterion... criterion);

	/**
	 * @Title: findUniqueResult
	 * @Description: 分页查询获取记录总数
	 * @param dcCount 离线查询条件
	 */
	public long findUniqueResult(DetachedCriteria dcCount);

	/**
	 * @Title: findByExample
	 * @Description: 通过示例查找实体集合
	 * @param exampleInstance 实体样例
	 * @return List<T> 对象集合
	 */
	public List<T> findByExample(T exampleInstance);

	/**
	 * @Title: findByExample
	 * @Description: 通过示例查找实体集合
	 * @param exampleInstance 实体样例
	 * @param excludeProperty 排除的实体中的属性数字
	 * @return List<T> 对象集合
	 */
	List<T> findByExample(T exampleInstance, String[] excludeProperty);

	/**
	 * @Title: findById
	 * @Description: 根据ID和返回一个指定类的实体
	 * @param c 实体类型
	 * @param id 实体ID
	 * @return T 实体对象
	 */
	public T findById(Class<?> c, ID id);

	/**
	 * @Title: findById
	 * @Description: 根据id从数据库中查找出数据映射到对象上返回
	 * @param id 主键id
	 * @return 实体对象
	 */
	public T findById(ID id);

	/**
	 * @Title: findEntities
	 * @Description: 查找全部实体对象
	 * @return List<T> 对象集合
	 */
	public List<T> findEntities();

	/**
	 * @Title: findEntities
	 * @Description: 使用HQL查找实体集合
	 * @param hql hql查询语句
	 * @return List<T> 对象集合
	 */
	public List<T> findEntities(String hql);

	/**
	 * @Title: findEntities
	 * @Description: 使用带参数的HQL语句查询实体
	 * @param hql hql查询语句
	 * @param params hql查询语句的参数数组
	 * @return List<T> 对象集合
	 */
	public List<T> findEntities(String hql, Object... params);

	/**
	 * @Title: findEntities
	 * @Description: 使用带参数的HQL语句查询分页的实体集合
	 * @param hql hql查询语句
	 * @param params hql查询语句的参数数组
	 * @param start 开始记录号
	 * @param limit 每页记录数
	 * @return List<T> 对象集合
	 */
	public List<T> findEntities(String hql, Object[] params, int start, int limit);

	/**
	 * @Title: findEntity
	 * @Description: 使用HQL查找一个实体对象
	 * @param hql hql查询语句
	 * @return T 一个实体对象
	 */
	public T findEntity(String hql);

	/**
	 * @Title: findUnique
	 * @Description: 按HQL查询唯一对象.
	 * @param <X> 返回的唯一对象
	 * @param hql 查询语句
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public <X> X findUnique(String hql, Object[] values);

	/**
	 * @Title: find
	 * @Description: 按HQL查询对象列表.
	 * @param <X>
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public <X> List<X> find(final String hql, final Object... values);

	/**
	 * @Title: findEntity
	 * @Description: 使用HQL超找一个实体对象
	 * @param hql hql查询语句
	 * @param params 参数数组
	 * @return T 一个实体对象
	 */
	public T findEntity(String hql, Object[] params);

	/**
	 * @Title: findLikeExample
	 * @Description: 根据实体来查询,根据参数分别获取是体重的相应属性进行模糊查询,否则属性按equals查询
	 * @param entity 实体
	 * @param propertyNames 需要模糊查询的实体中的属性数组
	 * @return List<T> 对象集合
	 */
	List<T> findLikeEntity(T entity, String[] propertyNames);

	/**
	 * @Title: getRowCount
	 * @Description: 通过离线查询条件返回总记录数
	 * @param dc 离线查询条件
	 * @return Integer 总记录数
	 */
	public Integer getRowCount(DetachedCriteria criteria);

	/**
	 * @Title: merge
	 * @Description: 合并实体对象
	 * @param entity 实体对象
	 * @return void
	 */
	public void merge(T entity);

	/**
	 * @Title: save
	 * @Description: 持久化一个实例对象到数据库
	 * @param entity 要持久化的对象
	 * @return
	 */
	public T save(T entity);

	/**
	 * @Title: update
	 * @Description: 修改对象内容到数据库.Save changes made to a persistent object.
	 * @param entity
	 */
	public T update(T entity);

	public void flush();

	/**
	 * @Title: findByIds
	 * @Description: 按id列表获取对象.
	 * @param ids id集合
	 * @return 对象积和
	 */
	public List<T> findByIds(List<ID> ids);

	/**
	 * @Title: findUniqueBy
	 * @Description: 按属性查找唯一对象,匹配方式为相等.
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return
	 */
	public T findUniqueBy(String propertyName, Object value);

	/**
	 * @Title: findBy
	 * @Description: 按属性查找对象列表,匹配方式为相等.
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return
	 */
	public List<T> findBy(String propertyName, Object value);

	/**
	 * @Title: findByNativeSql
	 * @Description: 用原生sql查询,返回Map集合Map中key是数据库表的列名,value是数据库表列对应的value值
	 * @param sql 查询sql
	 * @param params 查询条件的参数value
	 */
	List<Map<String, Object>> findByNativeSql(String sql, Object... params);

	/**
	 * @Title: findByNativeSql
	 * @Description: 原生aql查询
	 * @param sql
	 * @param classes
	 * @return
	 */
	public List<T> findByNativeSql(String sql, Class[] classes);

	/**
	 * @Title: findByNativeSql
	 * @Description: 原生aql查询
	 * @param sql 查询sql
	 * @param params 查询条件的参数value
	 * @param classes 映射到值对象的
	 */
	public List<T> findByNativeSql(String sql, Object[] params, Class[] classes);

	/**
	 * @Title: findByNativeSql
	 * @Description: 原生语句查询,通过命名参数赋值
	 * @param
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> findByNativeSql(String sql, String[] paramNames, Object[] values);

	/**
	 * @Title: executeUpdate
	 * @Description: 执行HQL的更新操作
	 */
	boolean executeUpdate(String hql, Object... params);

}
