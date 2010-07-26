package com.lhq.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lhq.dao.IStationDao;
import com.lhq.po.Station;

public class StationDao extends HibernateDaoSupport implements IStationDao {

	public List findByExample(Station station) {
		try {
			List results = getHibernateTemplate().findByExample(station);
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

	public Station findById(  Integer id) {
		try {
			Station o = (Station) getHibernateTemplate().get(Station.class, id);
			return o;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from Station as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void saveOrUpdate(Station station) {
		try {
			getHibernateTemplate().saveOrUpdate(station);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Station saveStation(Station station) {
		this.getHibernateTemplate().save(station);
		return station;
	}

	public List findByNativeSql(String hql,Class c){
		SQLQuery query = getSession().createSQLQuery(hql);
		if(c != null){
			query.addEntity(c);
		}
		return query.list();
	}
	
	
	public List findByNativeSql(String hql,int start,int limit,Class c){
		SQLQuery query = getSession().createSQLQuery(hql).addEntity(c);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.list();
	}

}
