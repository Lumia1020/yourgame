package com.lhq.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lhq.dao.IDispenseDao;
import com.lhq.po.Dispense;

public class DispenseDao extends HibernateDaoSupport implements IDispenseDao {

	public List findByExample(Dispense dispense) {
		try {
			List results = getHibernateTemplate().findByExample(dispense);
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
	
	public Integer executeUpdate(final String hql, final String[] names, final String[] types, final Object[] values) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (names != null && values != null) {
					if (names.length != values.length) {
						throw new RuntimeException("参数和值的数量不匹配!");
					}
					for (int i = 0; i < names.length; i++) {
						if("string".equals(types[i])){
							query.setString(names[i], (String)values[i]);
							continue;
						}
						if("date".equals(types[i])){
							query.setDate(names[i], (Date)values[i]);
						}
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

	public Dispense findById(String id) {
		try {
			Dispense o = (Dispense) getHibernateTemplate().get(Dispense.class, id);
			return o;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from Dispense as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void saveOrUpdate(Dispense dispense) {
		try {
			getHibernateTemplate().saveOrUpdate(dispense);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Dispense saveDispense(Dispense dispense) {
		this.getHibernateTemplate().save(dispense);
		return dispense;
	}

	public boolean update(Dispense di) {
		boolean flag = false;
		try {
			this.getHibernateTemplate().update(di);
			flag = true;
		} catch (DataAccessException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

}
