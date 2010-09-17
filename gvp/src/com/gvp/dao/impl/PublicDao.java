package com.gvp.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gvp.core.IJdbcDao;
import com.gvp.dao.IPublicDao;

public class PublicDao extends HibernateDaoSupport implements IPublicDao {

	private IJdbcDao jdbc;

	public void setJdbc(IJdbcDao jdbc) {
		this.jdbc = jdbc;
	}

	public List<?> findBySpringSql(String sql, Object[] params, RowMapper rowMapper) {
		return jdbc.executeQueryList(sql, params, rowMapper);
	}

	public List<?> findBySpringSql(String sql, Object[] params, Class<?> elementType) {
		return jdbc.executeQueryList(sql, params, elementType);
	}

	public int queryForInt(String sql, Object[] args) {
		return jdbc.queryForInt(sql, args);
	}
	
	public List<?> findBySpringSql(String sql,Object[] params){
		return jdbc.executeQueryList(sql, params);
	}

	public Object findEntityById(Object obj, Integer id) {
		try {
			Object o = getHibernateTemplate().get(obj.getClass().getName(), id);
			return o;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * String hql = "select tm.* from t_materials tm where tm.qid in (select
	 * t.qid from t_quote_info t where t.ownerId is not null and t.qid in
	 * (select m.qid from t_materials m where m.specid in (?)))";
	 * this.publicDao.findByNativeSql(hql, Materials.class, new Object[] {
	 * specid },
	 */
	public List findByNativeSql(String hql, Class c, Object[] params, Type[] types) {
		SQLQuery query = getSession().createSQLQuery(hql);

		if (c != null) {
			query.addEntity(c);
		}
		if (params != null) {
			query.setParameters(params, new Type[] { new IntegerType() });
		}
		return query.list();
	}

	public List findByNativeSql(String hql, Class c) {
		SQLQuery query = getSession().createSQLQuery(hql);
		if (c != null) {
			query.addEntity(c);
		}
		return query.list();
	}

	public List findByExample(Object obj) {
		try {
			List results = getHibernateTemplate().findByExample(obj);
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from User as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void update(Object instance) {
		getHibernateTemplate().update(instance);
	}

	public void saveOrUpdate(Object instance) {
		try {
			getHibernateTemplate().saveOrUpdate(instance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByHQL(String hql, Object[] params) {
		return this.getHibernateTemplate().find(hql, params);
	}

	public List findByHQL(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	public int findByCount(DetachedCriteria dc) {
		dc.setProjection(Projections.rowCount());
		Criteria criteria = dc.getExecutableCriteria(getSession());
		return (Integer) criteria.uniqueResult();
	}

	public List findByCriteria(DetachedCriteria dc) {
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	public List findByCriteria(DetachedCriteria dc, int start, int limit) {
		Criteria criteria = dc.getExecutableCriteria(getSession());
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	public Integer executeUpdate(final String hql) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.executeUpdate();
			}
		});
	}

	public Integer executeUpdate(final String hql, final String[] names, final String[] values) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (names != null && values != null) {
					if (names.length != values.length) {
						throw new RuntimeException("参数和值的数量不匹配!");
					}
					for (int i = 0; i < names.length; i++) {
						query.setString(names[i], values[i]);
					}
				}
				return query.executeUpdate();
			}
		});
	}

	public Object saveEntity(Object entity) {
		this.getHibernateTemplate().save(entity);
		return entity;
	}

}
