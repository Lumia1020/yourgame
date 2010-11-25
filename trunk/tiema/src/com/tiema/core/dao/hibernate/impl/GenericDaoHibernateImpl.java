package com.tiema.core.dao.hibernate.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.SerializationUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.tiema.core.dao.GenericDao;
import com.tiema.core.dao.hibernate.core.EnhancedExample;
import com.tiema.core.orm.Page;

/**
 * @ClassName: GenericDaoHibernateImpl
 * @Description: 这是针对GenericDao接口的Hibernate实现，完成通用的CRUD操作。
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 28, 2010 10:42:19 AM
 * 
 * @param <T> 实体类
 * @param <ID> 实体类的主键ID
 * @param <DaoImpl> 针对每个实体类的DAO接口的实现类
 */
public abstract class GenericDaoHibernateImpl<T, ID extends Serializable, DaoImpl extends GenericDao<T, ID>> extends HibernateDaoSupport implements GenericDao<T, ID> {

	/** @Fields persistentClass : 保持实体对象类的类型 */
	private Class<T>	persistentClass;

	@SuppressWarnings("unchecked")
	public GenericDaoHibernateImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void delete(T entity) {
		try {
			this.getHibernateTemplate().delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAll(Collection<T> entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}

	@Override
	public void deleteById(ID id) {
		this.getHibernateTemplate().delete(this.findById(id));
	}

	public boolean executeUpdate(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		try {
			query.executeUpdate();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean executeByNativeSql(String sql, Object... params) {
		try {
			Query query = getSession().createSQLQuery(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			}
			query.executeUpdate();
			return true;
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> find(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findBy(final String propertyName, final Object value) {
		DetachedCriteria dc = DetachedCriteria.forClass(this.persistentClass);
		dc.add(Restrictions.eq(propertyName, value));
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(Criterion... criterion) {
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(getPersistentClass());
		for (Criterion c : criterion) {
			detachedCrit.add(c);
		}
		return getHibernateTemplate().findByCriteria(detachedCrit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults) {
		return this.getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final int firstResult, final int maxResults, final Order order, final Criterion... criterions) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(getPersistentClass());
				for (Criterion c : criterions) {
					criteria.add(c);
				}
				if (order != null) {
					criteria.addOrder(order);
				}

				criteria.setFirstResult(firstResult);
				criteria.setMaxResults(maxResults);

				return criteria.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Order order, Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		if (order != null)
			crit.addOrder(order);
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public Page findByCriteria(final Page page) {
		return (Page) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				long totalCount = 0;
				List result = null;
				Page p = (Page) SerializationUtils.clone(page);

				try {
					Criteria c = page.getCriteria().getExecutableCriteria(session);
					totalCount = ((Long) c.setProjection(Projections.rowCount()).uniqueResult()).longValue();
					c.setProjection(null);
					result = null;
					if (page.getPageSize() == 0) {
						result = c.list();
					} else {
						result = c.setFirstResult((page.getPageNo() - 1) * page.getPageSize()).setMaxResults(page.getPageSize()).list();
					}
					p.setSuccess(true);
				} catch (RuntimeException e) {
					p.setSuccess(false);
					e.printStackTrace();
				}

				p.setResult(result);
				p.setTotalCount(totalCount);
				return p;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByExample(T exampleInstance) {
		return this.getHibernateTemplate().findByExample(EnhancedExample.createDefault(exampleInstance, true));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		detachedCrit.add(example);
		return getHibernateTemplate().findByCriteria(detachedCrit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Class<?> c, ID id) {
		return (T) this.getHibernateTemplate().get(c, id);
	}

	@Override
	public T findById(ID id) {
		return this.getHibernateTemplate().get(this.getPersistentClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByIds(List<ID> ids) {
		ClassMetadata meta = getSessionFactory().getClassMetadata(persistentClass);
		Criterion criterion = Restrictions.in(meta.getIdentifierPropertyName(), ids);
		return getSession().createCriteria(persistentClass).add(criterion).list();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findByNativeSql(final String sql, final String[] paramNames, final Object[] values) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				for (int i = 0; i < paramNames.length; i++) {
					query.setParameter(paramNames[i], values[i]);
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findByNativeSql(final String sql, final Object... params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNativeSql(final String sql, final Class[] classes) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				for (int i = 0; i < classes.length; i++) {
					query.addEntity(classes[i]);
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNativeSql(final String sql, final Object[] params, final Class[] classes) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
				for (int i = 0; i < classes.length; i++) {
					query.addEntity(classes[i]);
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findEntities() {
		return this.getHibernateTemplate().find("FROM " + this.getPersistentClass().getName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findEntities(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findEntities(final String hql, final Object[] params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findEntities(final String hql, final Object[] params, final int start, final int limit) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findEntity(final String hql) {
		return (T) getSession().createQuery(hql).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findEntity(final String hql, final Object[] params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return (T) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findLikeEntity(T entity, String[] propertyNames) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		for (String property : propertyNames) {
			Object value = null;//PropertyUtils.getProperty(entity, property);
			if (value instanceof String) {
				criteria.add(Restrictions.like(property, (String) value, MatchMode.ANYWHERE));
				criteria.addOrder(Order.asc(property));
			} else {
				criteria.add(Restrictions.eq(property, value));
				criteria.addOrder(Order.asc(property));
			}
		}
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E findUnique(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (E) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) getSession().createCriteria(persistentClass).add(criterion).uniqueResult();
	}

	public long findUniqueResult(DetachedCriteria dc) {
		dc.setProjection(Projections.rowCount());
		Criteria criteria = dc.getExecutableCriteria(getSession());
		return (Long) criteria.uniqueResult();
	}

	@Override
	public void flush() {
		this.getHibernateTemplate().flush();
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public Integer getRowCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		return (Integer) this.getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public void merge(T entity) {
		this.getHibernateTemplate().merge(entity);
	}

	@Override
	public T save(T entity) {
		this.getHibernateTemplate().save(entity);
		return entity;
	}

	public void setPersistentClass(Class<T> entityType) {
		this.persistentClass = entityType;
	}

	@Resource
	public void setSessionFactory0(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public T update(T entity) {
		this.getHibernateTemplate().update(entity);
		return entity;
	}
}
