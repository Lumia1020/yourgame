package com.tiema.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sun.org.apache.commons.beanutils.PropertyUtils;
import com.tiema.core.EnhancedExample;
import com.tiema.core.dao.GenericDao;

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
	private Class<T> persistentClass;

	public GenericDaoHibernateImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public void deleteAll(Collection<T> entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}

	@Override
	public void deleteById(ID id) {
		this.getHibernateTemplate().delete(this.findById(id));
	}

	@Override
	public List<T> findByCriteria(Criterion... criterion) {
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(getPersistentClass());
		for (Criterion c : criterion) {
			detachedCrit.add(c);
		}
		return getHibernateTemplate().findByCriteria(detachedCrit);
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
		return this.getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}

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

	public List<T> findByCriteria(Order order, Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		if (order != null)
			crit.addOrder(order);
		return crit.list();
	}

	@Override
	public List<T> findByExample(T exampleInstance) {
		return this.getHibernateTemplate().findByExample(EnhancedExample.createDefault(exampleInstance, true));
	}

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

	@Override
	public T findById(Class<?> c, ID id) {
		return (T) this.getHibernateTemplate().get(c, id);
	}

	@Override
	public T findById(ID id) {
		return this.getHibernateTemplate().get(this.getPersistentClass(), id);
	}

	@Override
	public List<T> findEntities() {
		return this.getHibernateTemplate().find("FROM " + this.getPersistentClass().getName());
	}

	@Override
	public List<T> findEntities(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

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

	@Override
	public T findEntity(final String hql) {
		return (T) getSession().createQuery(hql).uniqueResult();
	}

	@Override
	public T findEntity(final String hql, final Object[] params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return (T) query.uniqueResult();
	}

	@Override
	public List<T> findLikeEntity(T entity, String[] propertyNames) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		for (String property : propertyNames) {
			try {
				Object value = PropertyUtils.getProperty(entity, property);
				if (value instanceof String) {
					criteria.add(Restrictions.like(property, (String) value, MatchMode.ANYWHERE));
					criteria.addOrder(Order.asc(property));
				} else {
					criteria.add(Restrictions.eq(property, value));
					criteria.addOrder(Order.asc(property));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return getHibernateTemplate().findByCriteria(criteria);
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
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public void flush() {
		this.getHibernateTemplate().flush();
	}
}
