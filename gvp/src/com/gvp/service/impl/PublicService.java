package com.gvp.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.springframework.jdbc.core.RowMapper;

import com.gvp.core.MyUtils;
import com.gvp.core.Page;
import com.gvp.dao.IPublicDao;
import com.gvp.po.Aids;
import com.gvp.po.Foundry;
import com.gvp.po.Materials;
import com.gvp.po.MaterialsView;
import com.gvp.po.OtherQuotePrice;
import com.gvp.po.PriceList;
import com.gvp.po.ProcessInfo;
import com.gvp.po.ProductCode;
import com.gvp.po.QuoteInfo;
import com.gvp.po.RefFiles;
import com.gvp.po.ReferenceInfo;
import com.gvp.po.User;
import com.gvp.po.WorkflowLog;
import com.gvp.service.IPublicService;

public class PublicService implements IPublicService {

	private IPublicDao publicDao;

	public void setPublicDao(IPublicDao userDao) {
		this.publicDao = userDao;
	}

	public Page getResultListBySpringJDBC(Page page, String sql, Object[] params) {
		StringBuilder resultSql = new StringBuilder("select tb.* from ( ");
		resultSql.append(sql);
		resultSql.append(" ) tb LIMIT ");
		resultSql.append(page.getStart());
		resultSql.append(" , ");
		resultSql.append(page.getLimit());

		System.out.println(resultSql.toString());

		List<?> results = publicDao.findBySpringSql(resultSql.toString(), params);
		int count = publicDao.findBySpringSql(sql, params).size();
		page.setRoot(results);
		page.setTotalProperty(count);

		return page;
	}

	public Page getResultListBySpringJDBC(Page page, String sql, Object[] params, RowMapper rowMapper) {
		StringBuilder resultSql = new StringBuilder("select tb.* from ( ");
		resultSql.append(sql);
		resultSql.append(" ) tb LIMIT ");
		resultSql.append(page.getStart());
		resultSql.append(" , ");
		resultSql.append(page.getLimit());

		List<?> results = publicDao.findBySpringSql(resultSql.toString(), params, rowMapper);
		int count = publicDao.findBySpringSql(sql, params).size();
		page.setRoot(results);
		page.setTotalProperty(count);

		return page;
	}

	public Page getResultListBySpringJDBC(Page page, String sql, Object[] params, Class<?> elementType) {
		StringBuilder resultSql = new StringBuilder("select tb.* from ( ");
		resultSql.append(sql);
		resultSql.append(" ) tb LIMIT ");
		resultSql.append(page.getStart());
		resultSql.append(" , ");
		resultSql.append(page.getLimit());

		List<?> results = publicDao.findBySpringSql(resultSql.toString(), params, elementType);
		int count = publicDao.findBySpringSql(sql, params).size();
		page.setRoot(results);
		page.setTotalProperty(count);

		return page;
	}

	@SuppressWarnings("unchecked")
	public User login(User user) {
		List<User> users = publicDao.findByExample(user);
		if (users != null && !users.isEmpty()) { return users.get(0); }
		return null;
	}

	public Object updateEntity(Object obj, Integer id, Integer qid) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Object u = publicDao.findEntityById(obj, id);
		MyUtils.copyProperties(u, obj);
		publicDao.update(u);
		if (qid != null) {
			this.process(qid);
		}
		return u;
	}

	public Page getResultList(Page page) {
		try {
			if (page.getLimit() == 0) {
				page.setRoot(publicDao.findByCriteria(page.getResult()));
			} else {
				page.setRoot(publicDao.findByCriteria(page.getResult(), page.getStart(), page.getLimit()));
				page.setTotalProperty(publicDao.findByCount(page.getCount()));
			}
			page.setSuccess(true);
		} catch (RuntimeException e) {
			page.setSuccess(false);
			e.printStackTrace();
		}
		return page;
	}

	public Boolean deleteEntities(String hql, String[] names, String[] values) {
		int rows = publicDao.executeUpdate(hql, names, values);
		return rows > 0;
	}

	/**
	 * 计算
	 * 
	 * @param qid
	 */
	private void process(Integer qid) {
		QuoteInfo qi = (QuoteInfo) publicDao.findEntityById(new QuoteInfo(), qid);

		Double price = 0d;

		List mlist = publicDao.findByHQL("from Materials where qid=" + qid);
		for (Iterator it = mlist.iterator(); it.hasNext();) {
			Materials m = (Materials) it.next();
			price += Double.valueOf(m.getPrice());
			price -= Double.valueOf(m.getJiansha());
		}

		List flist = publicDao.findByHQL("from Foundry where qid=" + qid);
		for (Iterator it = flist.iterator(); it.hasNext();) {
			Foundry f = (Foundry) it.next();
			price += Double.valueOf(f.getProcessPrice());
		}

		List plist = publicDao.findByHQL("from ProcessInfo where qid=" + qid);
		for (Iterator it = plist.iterator(); it.hasNext();) {
			ProcessInfo pi = (ProcessInfo) it.next();
			price += Double.valueOf(pi.getProcessPrice());
		}

		List alist = publicDao.findByHQL("from Aids where qid=" + qid);
		for (Iterator it = alist.iterator(); it.hasNext();) {
			Aids a = (Aids) it.next();
			price += Double.valueOf(a.getPrice());
		}

		List rlist = publicDao.findByHQL("from ReferenceInfo where qid=" + qid);
		Iterator rlistIt = rlist.iterator();
		if (rlistIt.hasNext()) {
			ReferenceInfo ri = (ReferenceInfo) rlistIt.next();
			price += Double.valueOf(ri.getFreight());
		}

		//计算其他价格
		List olist = publicDao.findByHQL("from OtherQuotePrice where qid=" + qid);
		if (!olist.isEmpty() && olist.size() > 0) {
			Double ov = price - Double.parseDouble(qi.getPrice());
			for (Iterator it = olist.iterator(); it.hasNext();) {
				OtherQuotePrice p = (OtherQuotePrice) it.next();
				p.setPrice(Double.toString(Double.parseDouble(p.getPrice()) + ov));
				this.publicDao.update(p);
			}
		}

		qi.setPrice(price.toString());
		this.publicDao.saveOrUpdate(qi);
	}

	public Object saveEntity(Object entity) {
		return this.publicDao.saveEntity(entity);
	}

	public WorkflowLog saveWorkflowLog(WorkflowLog workflow) {
		if (workflow != null) {
			this.publicDao.saveEntity(workflow);
			QuoteInfo qi = (QuoteInfo) this.publicDao.findEntityById(new QuoteInfo(), workflow.getQid());
			qi.setState(workflow.getState());
			this.publicDao.update(qi);
			return workflow;
		}
		return null;
	}

	public Object saveEntity(Object entity, Integer qid) {
		Object obj = publicDao.saveEntity(entity);
		if (qid != null) {
			this.process(qid);
		}
		return obj;
	}

	public Object completeQuoteInfo(Map<String, Object> map) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		try {
			QuoteInfo quoteInfo = (QuoteInfo) map.get("quoteInfo");
			Materials materials = (Materials) map.get("materials");
			ReferenceInfo reference = (ReferenceInfo) map.get("reference");
			Object u = publicDao.findEntityById(quoteInfo, quoteInfo.getQid());
			MyUtils.copyProperties(u, quoteInfo);
			publicDao.saveOrUpdate(u);
			publicDao.saveEntity(materials);
			publicDao.saveEntity(reference);
			return u;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteQuoteInfo(String ids) {
		List classes = new ArrayList();
		classes.add(QuoteInfo.class.getName());
		classes.add(Aids.class.getName());
		classes.add(Foundry.class.getName());
		classes.add(ProcessInfo.class.getName());
		classes.add(ReferenceInfo.class.getName());
		classes.add(Materials.class.getName());
		classes.add(RefFiles.class.getName());

		boolean flag = false;
		try {
			for (Iterator it = classes.iterator(); it.hasNext();) {
				publicDao.executeUpdate(new StringBuffer("delete ").append(it.next()).append(" where qid in ( ")
						.append(ids).append(" )").toString());
			}
			flag = true;
		} catch (RuntimeException e) {
			flag = false;
			e.printStackTrace();
		}

		return flag;
	}

	public boolean deleteEntities(String hql, Integer qid) {
		int rows = publicDao.executeUpdate(hql);
		if (qid != null) {
			this.process(qid);
		}
		return rows > 0;
	}

	public List getList(Class clazz, Integer qid) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.eq("qid", qid));
		Page p = new Page();
		p.setResult(dc);
		p = this.getResultList(p);
		return p.getRoot();
	}

	@SuppressWarnings("unchecked")
	public Map copyQuoteInfos(Map<String, Object> map) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Map<String, Object> my = new HashMap<String, Object>();
		QuoteInfo q = (QuoteInfo) map.get("quoteInfo");
		QuoteInfo qq = (QuoteInfo) publicDao.findEntityById(q, q.getQid());
		MyUtils.copyProperties(q, qq);
		q.setOwnerId(qq.getQid());
		q.setModifyTime(new Date());
		q.setRecordTime(qq.getRecordTime());
		q = (QuoteInfo) publicDao.saveEntity(q);
		my.put("quoteInfo", q);
		
		DetachedCriteria dc = DetachedCriteria.forClass(ProductCode.class);
		dc.add(Restrictions.eq("qid", qq.getQid()));
		List otherQuotePriceList = publicDao.findByCriteria(dc);
		for(Iterator it = otherQuotePriceList.iterator(); it.hasNext();){
			ProductCode oop = (ProductCode) it.next();
			ProductCode _oop = new ProductCode();
			MyUtils.copyProperties(_oop, oop);
			_oop.setQid(q.getQid());
			_oop.setPcid(null);
			publicDao.saveEntity(_oop);
		}

		List processList = this.getList(ProcessInfo.class, qq.getQid());
		List plist = new ArrayList();
		if(processList != null && !processList.isEmpty()){
			for (Iterator<ProcessInfo> pit = processList.iterator(); pit.hasNext();) {
				ProcessInfo pi = pit.next();
				ProcessInfo _pi = new ProcessInfo();
				MyUtils.copyProperties(_pi, pi);
				
				_pi.setPid(null);
				_pi.setQid(q.getQid());
				_pi = (ProcessInfo) publicDao.saveEntity(_pi);
				plist.add(_pi);
			}
		}
		my.put("process", plist);

		List aidsList = this.getList(Aids.class, qq.getQid());
		List alist = new ArrayList();
		if(aidsList != null && !aidsList.isEmpty()){
			for (Iterator<Aids> pit = aidsList.iterator(); pit.hasNext();) {
				Aids pi = pit.next();
				Aids _pi = new Aids();
				MyUtils.copyProperties(_pi, pi);
				
				_pi.setAid(null);
				_pi.setQid(q.getQid());
				_pi = (Aids) publicDao.saveEntity(_pi);
				alist.add(_pi);
			}
		}
		my.put("aids", alist);

		List foundryList = this.getList(Foundry.class, qq.getQid());
		List flist = new ArrayList();
		if(foundryList != null && !foundryList.isEmpty()){
			for (Iterator<Foundry> pit = foundryList.iterator(); pit.hasNext();) {
				Foundry pi = pit.next();
				Foundry _pi = new Foundry();
				MyUtils.copyProperties(_pi, pi);
				_pi.setFid(null);
				_pi.setQid(q.getQid());
				_pi = (Foundry) publicDao.saveEntity(_pi);
				flist.add(_pi);
			}
		}
		my.put("foundry", flist);

		List materialsList = this.getList(Materials.class, qq.getQid());
		List mlist = new ArrayList();
		if(materialsList != null && !materialsList.isEmpty()){
			for (Iterator<Materials> pit = materialsList.iterator(); pit.hasNext();) {
				Materials pi = pit.next();
				Materials _pi = new Materials();
				MyUtils.copyProperties(_pi, pi);

				_pi.setMid(null);
				_pi.setQid(q.getQid());
				_pi = (Materials) publicDao.saveEntity(_pi);
				MaterialsView _i = (MaterialsView) publicDao.findByNativeSql("select v.* from v_materials v where v.mid = " + _pi.getMid(), MaterialsView.class).get(0);
				mlist.add(_i);
			}
		}
		my.put("materials", mlist);

		List referenceInfoList = this.getList(ReferenceInfo.class, qq.getQid());
		List rlist = new ArrayList();
		if(referenceInfoList != null && !referenceInfoList.isEmpty()){
			for (Iterator<ReferenceInfo> pit = referenceInfoList.iterator(); pit.hasNext();) {
				ReferenceInfo pi = pit.next();
				ReferenceInfo _pi = new ReferenceInfo();
				MyUtils.copyProperties(_pi, pi);

				_pi.setRid(null);
				_pi.setQid(q.getQid());
				_pi = (ReferenceInfo) publicDao.saveEntity(_pi);
				rlist.add(pi);
			}
		}
		my.put("reference", rlist);

		List refFilesList = this.getList(RefFiles.class, qq.getQid());
		List rflist = new ArrayList();
		if(refFilesList != null && !refFilesList.isEmpty()){
			for (Iterator<RefFiles> pit = refFilesList.iterator(); pit.hasNext();) {
				RefFiles pi = pit.next();
				RefFiles _pi = new RefFiles();
				MyUtils.copyProperties(_pi, pi);
				
				_pi.setFid(null);
				_pi.setQid(q.getQid());
				_pi = (RefFiles) publicDao.saveEntity(_pi);
				rflist.add(pi);
			}
		}
		my.put("refFiles", rflist);

		return my;
	}

	public Map<String, Object> findQuoteInfoById(Integer qid, Boolean relation) {
		Map<String, Object> infos = new HashMap<String, Object>();
		infos.put("quoteInfo", this.getList(QuoteInfo.class, qid));
		if (relation) {
			infos.put("aids", this.getList(Aids.class, qid));
			infos.put("foundry", this.getList(Foundry.class, qid));
			infos.put("otherPrice", this.getList(OtherQuotePrice.class, qid));
			infos.put("materials", this.getList(MaterialsView.class, qid));
			infos.put("process", this.getList(ProcessInfo.class, qid));
			infos.put("reference", this.getList(ReferenceInfo.class, qid));
			infos.put("refFiles", this.getList(RefFiles.class, qid));
			infos.put("productCode", this.getList(ProductCode.class, qid));
		}
		return infos;
	}

	@SuppressWarnings("unchecked")
	public List getList(String hql, Object[] params) {
		return publicDao.findByHQL(hql, params);
	}

	public List getList(String hql) {
		return publicDao.findByHQL(hql);
	}

	public List<?> findByNativeSql(String hql, Class<?> c, Object[] params, Type[] types) {
		return publicDao.findByNativeSql(hql, c, params, types);
	}

	public List findByNativeSql(String hql, Class c) {
		return publicDao.findByNativeSql(hql, c);
	}

	@SuppressWarnings("unchecked")
	public PriceList adjustQuoteInfos(PriceList priceList,Integer cid) {
		this.publicDao.saveEntity(priceList);
		Integer specid = priceList.getSpecification().getSpecid();
		List params = new ArrayList();
		List<Type> types = new ArrayList<Type>();

		String hql = "SELECT a.* FROM ( SELECT tm.* FROM t_materials tm WHERE tm.qid IN( SELECT t.qid FROM t_quote_info t WHERE t.ownerId IS NOT NULL AND t.qid IN( SELECT m.qid FROM t_materials m WHERE m.specid IN(?) ) ) )AS a left join t_quote_info tqi on tqi.qid = a.qid where 1=1";
		params.add(specid);
		types.add(new IntegerType());
		if(cid != null){
			hql += " and tqi.cid = ? ";
			params.add(cid);
			types.add(new IntegerType());
		}
		List<Materials> materialsList = this.publicDao.findByNativeSql(hql, Materials.class, params.toArray(), types.toArray(new Type[]{}));

		for (Iterator<Materials> it = materialsList.iterator(); it.hasNext();) {
			Materials m = it.next();
			m.setAdjustDate(new Date());//设定调节日期
			m.setAdjustRemark(priceList.getRemark());//新增调节备注
			m.setMaterialPrice(priceList.getPrice());
			m.adjust();
			this.publicDao.update(m);
			this.process(m.getQid());
			QuoteInfo qi = (QuoteInfo) publicDao.findByHQL("from QuoteInfo qi where qi.qid = ?",new Object[]{m.getQid()}).get(0);
			qi.setAdjustDate(new Date());
			publicDao.update(qi);
		}
		return priceList;
	}

	public List<?> findBySpringSql(String sql, Object[] params, RowMapper rowMapper) {
		return this.publicDao.findBySpringSql(sql, params, rowMapper);
	}

}
