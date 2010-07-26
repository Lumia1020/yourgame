package com.lhq.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lhq.dao.IGetDataDao;
import com.lhq.po.GetData;
import com.lhq.test.HibernateUtils;

public class GetDataDao extends HibernateDaoSupport implements IGetDataDao {

	public List findByExample(GetData getData) {
		try {
			List results = getHibernateTemplate().findByExample(getData);
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
	
	public GetData findById(String id) {
		try {
			GetData o = (GetData) getHibernateTemplate().get(GetData.class, id);
			return o;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from GetData as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void saveOrUpdate(GetData getData) {
		try {
			getHibernateTemplate().saveOrUpdate(getData);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public GetData saveGetData(GetData getData) {
		this.getHibernateTemplate().save(getData);
		return getData;
	}

	public boolean update(GetData di) {
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
