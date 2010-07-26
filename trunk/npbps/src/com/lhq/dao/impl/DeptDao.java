package com.lhq.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lhq.dao.IDeptDao;
import com.lhq.po.Dept;

public class DeptDao extends HibernateDaoSupport implements IDeptDao {

	public List findByExample(Dept dept) {
		try {
			List results = getHibernateTemplate().findByExample(dept);
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
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

	public Integer executeUpdate(final String hql) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.executeUpdate();
			}
		});
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

	public List findByHQL(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	public Dept findById( Integer id) {
		try {
			Dept o = (Dept) getHibernateTemplate().get(Dept.class, id);
			return o;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from Dept as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void saveOrUpdate(Dept dept) {
		try {
			getHibernateTemplate().saveOrUpdate(dept);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Dept saveDept(Dept dept) {
		this.getHibernateTemplate().save(dept);
		return dept;
	}

}
