package com.gvp.action;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.gvp.core.Action;
import com.gvp.core.BaseAction;
import com.gvp.core.EnhancedExample;
import com.gvp.core.MyUtils;
import com.gvp.core.Page;
import com.gvp.core.TreeNode;
import com.gvp.po.Aids;
import com.gvp.po.Customer;
import com.gvp.po.Foundry;
import com.gvp.po.Materials;
import com.gvp.po.MaterialsView;
import com.gvp.po.OtherQuotePrice;
import com.gvp.po.PriceList;
import com.gvp.po.PriceListView;
import com.gvp.po.ProcessInfo;
import com.gvp.po.ProductCode;
import com.gvp.po.QuoteInfo;
import com.gvp.po.RefFiles;
import com.gvp.po.ReferenceInfo;
import com.gvp.po.Role;
import com.gvp.po.Species;
import com.gvp.po.SpeciesView;
import com.gvp.po.Specification;
import com.gvp.po.SpecificationView;
import com.gvp.po.Stuff;
import com.gvp.po.SystemLog;
import com.gvp.po.User;
import com.gvp.po.WorkflowLog;
import com.gvp.service.IPublicService;
import com.gvp.service.vo.QuoteInfoMapper;

@SuppressWarnings("serial")
public class PublicAction extends BaseAction {

	private IPublicService		publicService;

	private User				user;

	private Customer			customer;

	private Stuff				stuff;

	private QuoteInfo			quoteInfo;

	private Aids				aids;

	private Role				role;

	private RefFiles			refFiles;

	private Foundry				foundry;

	private Materials			materials;

	private ReferenceInfo		reference;

	private ProcessInfo			process;

	private boolean				success;

	private Page				page;

	private Map<String, Object>	infos	= new HashMap<String, Object>();

	private Species				species;

	private SpeciesView			speciesView;

	private Specification		specification;

	private SpecificationView	specificationView;

	private PriceList			priceList;

	private PriceListView		priceListView;

	private ProductCode			productCode;

	private SystemLog			log;

	private WorkflowLog			workflow;

	private OtherQuotePrice		otherPrice;

	private Date				startRecordTime;							//填单日期
	private Date				endRecordTime;

	private Date				startModifyTime;							//更新日期
	private Date				endModifyTime;

	private String				version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @Title: findQuoteInfoAdvanced
	 * @Description: 高级查询
	 * @return
	 */
	@Action(description = "高级查询")
	public String findQuoteInfoAdvanced() {
		/*
		 * SELECT tqi.* from t_quote_info tqi,t_materials tm where tqi.qid = tm.qid AND tqi.customerType = '主要客户' AND
		 * tqi.state = '已审核' AND tqi.dccNo LIKE '%6%' AND tqi.cid = 8 AND tqi.pageNo = '255' AND tqi.ownerId is NOT NULL
		 * AND tqi.recordTime BETWEEN '2010-01-01' and '2010-12-12' AND tqi.modifyTime BETWEEN '2010-09-01' and
		 * '2010-12-01' AND tm.productsName like '%0%' AND tm.stuffid = 3 AND tm.specid = 1 AND tm.speciesid = 14 AND
		 * tqi.productCode LIKE '%3%' union all select tqi.* from t_quote_info tqi where tqi.qid in (select tpc.qid from
		 * t_product_code tpc WHERE tpc.code like '%3%')
		 */
		StringBuilder sql = new StringBuilder("SELECT * FROM (SELECT tqi.* from t_quote_info tqi left join t_materials tm on tqi.qid = tm.qid where 1=1 ");
		List<Object> params = new ArrayList<Object>();

		if (customer != null) {
			//客户类型
			String customerType = customer.getCustomerType();
			if (StringUtils.isNotEmpty(customerType)) {
				sql.append(" AND tqi.customerType = ? ");
				params.add(customerType);
			}
		}

		//版本
		if ("原始".equals(version)) {
			sql.append(" AND tqi.ownerId is NULL ");
		}
		if ("非原始".equals(version)) {
			sql.append(" AND tqi.ownerId is NOT NULL ");
		}

		//填单日期
		if (startRecordTime != null && endRecordTime != null) {
			sql.append(" AND tqi.recordTime BETWEEN ? and ? ");
			params.add(startRecordTime);
			params.add(endRecordTime);
		}

		//更新日期
		if (startModifyTime != null && endModifyTime != null) {
			sql.append(" AND tqi.recordTime BETWEEN ? and ? ");
			params.add(startModifyTime);
			params.add(endModifyTime);
		}

		if (materials != null) {
			//产品名称
			String productsName = materials.getProductsName();
			if (StringUtils.isNotEmpty(productsName)) {
				sql.append(" AND tm.productsName like ? ");
				params.add("%" + productsName + "%");
			}
		}

		if (stuff != null) {
			//材质
			Integer stuffid = stuff.getStuffid();
			if (stuffid != null) {
				sql.append(" AND tm.stuffid = ? ");
				params.add(stuffid);
			}
		}
		
		if( specification != null){
			//规格
			Integer specid = specification.getSpecid();
			if (specid != null) {
				sql.append(" AND tm.specid = ? ");
				params.add(specid);
			}
		}

		if(species != null){
			//种类
			Integer speciesid = species.getSpeciesid();
			if (speciesid != null) {
				sql.append(" AND tm.speciesid = ? ");
				params.add(speciesid);
			}
		}
		
		if (quoteInfo != null) {
			Integer qid = quoteInfo.getQid();
			if (qid != null) {
				sql.append(" AND tqi.qid = ? ");
				params.add(qid);
			}
			//状态
			String state = quoteInfo.getState();
			if (StringUtils.isNotEmpty(state)) {
				sql.append(" AND tqi.state = ? ");
				params.add(state);
			}

			//dcc编码
			String dccNo = quoteInfo.getDccNo();
			if (StringUtils.isNotEmpty(dccNo)) {
				sql.append(" AND tqi.dccNo LIKE ? ");
				params.add("%" + dccNo + "%");
			}

			//客户id
			Integer cid = quoteInfo.getCid();
			if (cid != null) {
				sql.append(" AND tqi.cid = ? ");
				params.add(cid);
			}

			//文件夹页码
			String pageNo = quoteInfo.getPageNo();
			if (StringUtils.isNotEmpty(pageNo)) {
				sql.append(" AND tqi.pageNo = ? ");
				params.add(pageNo);
			}

			//产品编码
			String productCode = quoteInfo.getProductCode();
			if (StringUtils.isNotEmpty(productCode)) {
				sql.append(" AND tqi.productCode LIKE ? ");
				params.add("%" + productCode + "%");
				sql.append(" UNION ALL select tqi.* FROM t_quote_info tqi WHERE tqi.qid IN (SELECT tpc.qid FROM t_product_code tpc WHERE tpc.code LIKE ?) ");
				params.add("%" + productCode + "%");
			}
		}

		sql.append(") a");

		String sortByRecordTime = page.getParams().get("sortByRecordTime");
		String sortByPageNo = page.getParams().get("sortByPageNo");
		if (StringUtils.isNotEmpty(sortByPageNo) || StringUtils.isNotEmpty(sortByRecordTime)) {
			sql.append(" ORDER BY ");
			if (StringUtils.isNotEmpty(sortByPageNo)) {
				sql.append(" a.pageNo + 0 ");//将字段*1或者+0可以将MySQL字符串字段按数值排序
				if (StringUtils.isNotEmpty(sortByRecordTime)) {
					sql.append(" , ");
				}
			}
			if (StringUtils.isNotEmpty(sortByRecordTime)) {
				sql.append(" a.recordTime ");
			}
		}

		//sortByPageNo
		//sortByRecordTime

		this.page = publicService.getResultListBySpringJDBC(page, sql.toString(), params.toArray(), new QuoteInfoMapper());
		return SUCCESS;
	}

	/**
	 * 删除报时单的产品编号
	 * 
	 * @return
	 */
	@Action(description = "删除报时单的产品编码")
	public String deleteProductCode() {
		final String hql = "delete ProductCode where pcid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, null);
		return SUCCESS;
	}

	/**
	 * 删除报时单的其他报价信息
	 * 
	 * @return
	 */
	@Action(description = "删除报时单的其他报价信息")
	public String deleteOtherPrice() {
		final String hql = "delete OtherQuotePrice where oid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, null);
		return SUCCESS;
	}

	/**
	 * 删除外发加工信息
	 * 
	 * @return
	 */
	@Action(description = "删除外发加工信息")
	public String deleteFoundry() {
		final String hql = "delete Foundry where fid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, Integer.valueOf(page.getParams().get("qid")));
		this.infos.put("quoteInfo", publicService.findQuoteInfoById(Integer.valueOf(page.getParams().get("qid")), false).get("quoteInfo"));
		return SUCCESS;
	}

	/**
	 * 删除加工信息
	 * 
	 * @return
	 */
	@Action(description = "删除加工信息")
	public String deleteProcessInfo() {
		final String hql = "delete ProcessInfo where pid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, Integer.valueOf(page.getParams().get("qid")));
		this.infos.put("quoteInfo", publicService.findQuoteInfoById(Integer.valueOf(page.getParams().get("qid")), false).get("quoteInfo"));
		return SUCCESS;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@Action(description = "删除用户信息")
	public String deleteUser() {
		final String hql = "delete User where userid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, null);
		return SUCCESS;
	}

	/**
	 * 删除种类信息
	 * 
	 * @return
	 */
	@Action(description = "删除种类信息")
	public String deleteSpecies() {
		final String hql = "delete Species where speciesid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, null);
		return SUCCESS;
	}

	/**
	 * 删除材质信息
	 * 
	 * @return
	 */
	@Action(description = "删除材质信息")
	public String deleteStuff() {
		final String hql = "delete Stuff where stuffid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, null);
		return SUCCESS;
	}

	/**
	 * 删除规格信息
	 * 
	 * @return
	 */
	@Action(description = "删除规格信息")
	public String deleteSpecification() {
		final String hql = "delete Specification where specid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, null);
		return SUCCESS;
	}

	/**
	 * 删除供应商
	 * 
	 * @return
	 */
	@Action(description = "删除供应商信息")
	public String deleteProvider() {
		final String hql = "delete Provider where id in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, null);
		return SUCCESS;
	}

	/**
	 * 删除客户
	 * 
	 * @return
	 */
	@Action(description = "删除客户信息")
	public String deleteCustomer() {
		final String hql = "delete Customer where cid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, null);
		return SUCCESS;
	}

	/**
	 * 删除辅助信息
	 * 
	 * @return
	 */
	@Action(description = "删除辅助信息")
	public String deleteAids() {
		final String hql = "delete Aids where aid in (" + page.getParams().get("ids") + ")";
		this.success = publicService.deleteEntities(hql, Integer.valueOf(page.getParams().get("qid")));
		this.infos.put("quoteInfo", publicService.findQuoteInfoById(Integer.valueOf(page.getParams().get("qid")), false).get("quoteInfo"));
		return SUCCESS;
	}

	/**
	 * 删除参考文件信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(description = "删除参考文件信息")
	public String deleteRefFiles() {
		final String hql = "delete RefFiles where fid in (" + page.getParams().get("ids") + ")";

		List<RefFiles> list = publicService.getList("from RefFiles where fid in (" + page.getParams().get("ids") + ")");
		this.success = publicService.deleteEntities(hql, null);
		if (success) {
			String rootPath = getSession().getServletContext().getRealPath("/");
			for (Iterator<RefFiles> it = list.iterator(); it.hasNext();) {
				MyUtils.delFile(rootPath + it.next().getUrl());
			}
		}
		return SUCCESS;
	}

	/**
	 * 删除报时
	 * 
	 * @return
	 */
	@Action(description = "删除报时单")
	public String deleteQuoteInfo() {
		this.success = publicService.deleteQuoteInfo(page.getParams().get("ids"));
		return SUCCESS;
	}

	/**
	 * 更新外发加工信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改报时单其他报价")
	public String updateOtherPrice() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			OtherQuotePrice p = (OtherQuotePrice) publicService.updateEntity(otherPrice, otherPrice.getOid(), null);
			if (p != null) {
				this.infos.put("otherPrice", p);
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新报时单的产品编码
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改报时单产品编码")
	public String updateProductCode() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			ProductCode p = (ProductCode) publicService.updateEntity(productCode, productCode.getPcid(), null);
			if (p != null) {
				this.infos.put("productCode", p);
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新外发加工信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改外发加工信息")
	public String updateFoundry() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			Foundry p = (Foundry) publicService.updateEntity(foundry, foundry.getFid(), foundry.getQid());
			if (p != null) {
				this.infos.put("foundry", p);
				this.infos.put("quoteInfo", publicService.findQuoteInfoById(foundry.getQid(), false).get("quoteInfo"));
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新加工信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改加工信息")
	public String updateProcessInfo() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			ProcessInfo p = (ProcessInfo) publicService.updateEntity(process, process.getPid(), process.getQid());
			if (p != null) {
				this.infos.put("process", p);
				this.infos.put("quoteInfo", publicService.findQuoteInfoById(this.process.getQid(), false).get("quoteInfo"));
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新用户信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改用户信息")
	public String updateUser() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			User u = (User) publicService.updateEntity(user, user.getUserid(), null);
			if (u != null) {
				this.infos.put("user", u);
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新用户权限
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改用户权限")
	public String updateRole() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			Role u = (Role) publicService.updateEntity(role, role.getRid(), null);
			if (u != null) {
				this.infos.put("role", u);
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新辅助信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改辅助信息")
	public String updateAids() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			Aids c = (Aids) publicService.updateEntity(aids, aids.getAid(), aids.getQid());
			if (c != null) {
				this.infos.put("aids", c);
				this.infos.put("quoteInfo", publicService.findQuoteInfoById(aids.getQid(), false).get("quoteInfo"));
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新种类信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改种类信息")
	public String updateSpecies() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		species.setStuff(stuff);
		try {
			Species c = (Species) publicService.updateEntity(species, species.getSpeciesid(), null);
			if (c != null) {
				SpeciesView v = (SpeciesView) publicService.findByNativeSql("select * from v_species v where v.speciesid = " + c.getSpeciesid(), SpeciesView.class).get(0);
				this.infos.put("speciesView", v);
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新规格信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改规格信息")
	public String updateSpecification() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		specification.setSpecies(species);
		try {
			Specification c = (Specification) publicService.updateEntity(specification, specification.getSpecid(), null);
			if (c != null) {
				SpecificationView v = (SpecificationView) publicService.findByNativeSql("select * from v_specification v where v.specid = " + specification.getSpecid(),
						SpecificationView.class).get(0);
				this.infos.put("specificationView", v);
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新材质信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改材质信息")
	public String updateStuff() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		stuff.setProvider(provider);
		try {
			Stuff c = (Stuff) publicService.updateEntity(stuff, stuff.getStuffid(), null);
			if (c != null) {
//				StuffView v = (StuffView) publicService.findByNativeSql("select * from v_stuff v where v.stuffid = " + c.getStuffid(), StuffView.class).get(0);
				Stuff v = (Stuff) publicService.findByNativeSql("select * from t_stuff v where v.stuffid = " + c.getStuffid(), Stuff.class).get(0);
				this.infos.put("stuff", v);
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}


	/**
	 * 更新客户
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改客户信息")
	public String updateCustomer() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			Customer c = (Customer) publicService.updateEntity(customer, customer.getCid(), null);
			if (c != null) {
				this.infos.put("customer", c);
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新参考文件信息
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Action(description = "修改参考文件信息")
	public String updateRefFiles() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			RefFiles c = (RefFiles) publicService.updateEntity(refFiles, refFiles.getFid(), null);
			if (c != null) {
				this.infos.put("refFiles", c);
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新生产材料信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改生成材料信息")
	public String updateMaterials() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			this.materials.adjust();
			this.materials.setSpecification(specification);
			Materials c = (Materials) publicService.updateEntity(materials, materials.getMid(), materials.getQid());
			if (c != null) {
				MaterialsView v = (MaterialsView) publicService.findByNativeSql("select v.* from v_materials v where v.mid = " + c.getMid(), MaterialsView.class).get(0);
				this.infos.put("materials", v);
				this.infos.put("quoteInfo", publicService.findQuoteInfoById(materials.getQid(), false).get("quoteInfo"));
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新参考信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "修改参考信息")
	public String updateReferenceInfo() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			ReferenceInfo c = (ReferenceInfo) publicService.updateEntity(reference, reference.getRid(), reference.getQid());
			if (c != null) {
				this.infos.put("reference", c);
				this.infos.put("quoteInfo", publicService.findQuoteInfoById(reference.getQid(), false).get("quoteInfo"));
				this.success = true;
			}
		} catch (RuntimeException e) {
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新报时信息
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Action(description = "修改报时单信息")
	public String updateQuoteInfo() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		try {
			this.infos.put("quoteInfo", publicService.updateEntity(quoteInfo, quoteInfo.getQid(), null));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}


	/**
	 * 保存客户信息
	 * 
	 * @return
	 */
	@Action(description = "新增客户信息")
	public String saveCustomer() {
		Page p = new Page();
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		dc.add(Example.create(new Customer(customer.getCustomerName())));
		p.setResult(dc);
		p = publicService.getResultList(p);
		Iterator<?> cit = p.getRoot().iterator();
		if (cit.hasNext()) {
			this.infos.put("msg", "此客户名称已经存在!");
			this.success = false;
		} else {
			try {
				this.infos.put("customer", publicService.saveEntity(this.customer));
				this.success = true;
			} catch (RuntimeException e) {
				this.success = false;
				this.infos.put("msg", MyUtils.getExceptionMessages(e));
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	/**
	 * 保存外发加工信息
	 * 
	 * @return
	 */
	@Action(description = "新增外发加工信息")
	public String saveFoundry() {
		try {
			this.infos.put("foundry", publicService.saveEntity(this.foundry, this.foundry.getQid()));
			this.infos.put("quoteInfo", publicService.findQuoteInfoById(foundry.getQid(), false).get("quoteInfo"));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 保存报时单其他报价信息
	 * 
	 * @return
	 */
	@Action(description = "新增报时单其他报价")
	public String saveOtherPrice() {
		try {
			this.infos.put("otherPrice", publicService.saveEntity(this.otherPrice, null));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 保存报时单的产品编码
	 * 
	 * @return
	 */
	@Action(description = "新增报时单的产品编码")
	public String saveProductCode() {
		try {
			this.infos.put("productCode", publicService.saveEntity(this.productCode, null));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 保存加工信息
	 * 
	 * @return
	 */
	@Action(description = "新增加工信息")
	public String saveProcessInfo() {
		try {
			this.infos.put("process", publicService.saveEntity(this.process, this.process.getQid()));
			this.infos.put("quoteInfo", publicService.findQuoteInfoById(this.process.getQid(), false).get("quoteInfo"));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 保存辅助信息
	 * 
	 * @return
	 */
	@Action(description = "新增辅助信息")
	public String saveAids() {
		try {
			this.infos.put("aids", publicService.saveEntity(this.aids, this.aids.getQid()));
			this.infos.put("quoteInfo", publicService.findQuoteInfoById(aids.getQid(), false).get("quoteInfo"));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 完成报时表
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Action(description = "完善报时单信息")
	public String completeQuoteInfo() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("quoteInfo", quoteInfo);
		map.put("materials", materials);
		map.put("reference", reference);
		Object o;
		try {
			o = publicService.completeQuoteInfo(map);
			this.infos.put("quoteInfo", o);
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 添加生产材料信息
	 * 
	 * @return
	 */
	@Action(description = "新增生成材料信息")
	public String saveMaterials() {
		try {
			this.materials.adjust();
			materials.setSpecification(specification);
			this.infos.put("materials", publicService.saveEntity(this.materials, this.materials.getQid()));
			this.infos.put("quoteInfo", publicService.findQuoteInfoById(materials.getQid(), false).get("quoteInfo"));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 保存参考信息
	 * 
	 * @return
	 */
	@Action(description = "新增参考信息")
	public String saveReferenceInfo() {
		try {
			this.infos.put("reference", publicService.saveEntity(this.reference, this.reference.getQid()));
			this.infos.put("quoteInfo", publicService.findQuoteInfoById(reference.getQid(), false).get("quoteInfo"));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 新增供应商材料单价调节记录
	 */
	@Action(description = "新增供应商材料单价调节记录")
	public String adjustQuoteInfos() {
		try {
			this.priceList.setRecordTime(new Date());

			priceList.setSpecification(specification);
			PriceList p = publicService.adjustQuoteInfos(priceList,quoteInfo.getCid());
			//			StringBuilder sql = new StringBuilder();

			//			sql.append(" SELECT tst.stuffName,");
			//			sql.append(" ts.speciesName,");
			//			sql.append(" tsp.specName,");
			//			sql.append(" tp.price,");
			//			sql.append(" tp.remark,");
			//			sql.append(" tp.recordTime,");
			//			sql.append(" tp.listid");
			//			sql.append(" FROM t_price_list    AS tp,");
			//			sql.append(" t_species       AS ts,");
			//			sql.append(" t_specification AS tsp,");
			//			sql.append(" t_stuff         AS tst");
			//			sql.append(" WHERE tp.stuffid = tst.stuffid");
			//			sql.append(" AND tp.speciesid = ts.speciesid");
			//			sql.append(" AND tp.specid = tsp.specid");
			//			sql.append(" AND tp.listid = ? ");
			//			this.infos.put("priceList", publicService.findBySpringSql(sql.toString(), new Object[] { p.getListid() }, new PriceListMapper()).get(0));
			PriceListView v = (PriceListView) publicService.findByNativeSql("select * from v_price_list v where v.listid = " + p.getListid(), PriceListView.class).get(0);
			this.infos.put("priceListView", v);
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 新增规格信息
	 * 
	 * @return
	 */
	@Action(description = "新增规格信息")
	public String saveSpecification() {
		specification.setSpecies(species);
		try {
			specification = (Specification) publicService.saveEntity(this.specification);
			SpecificationView v = (SpecificationView) publicService.findByNativeSql("select * from v_specification v where v.specid = " + specification.getSpecid(),
					SpecificationView.class).get(0);
			this.infos.put("specificationView", v);
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 新增种类信息
	 * 
	 * @return
	 */
	@Action(description = "新增种类信息")
	public String saveSpecies() {
		species.setStuff(stuff);
		try {
			species = (Species) publicService.saveEntity(this.species);
			SpeciesView v = (SpeciesView) publicService.findByNativeSql("select * from v_species v where v.speciesid = " + species.getSpeciesid(), SpeciesView.class).get(0);
			this.infos.put("speciesView", v);
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 新增材质信息
	 * 
	 * @return
	 */
	@Action(description = "新增材质信息")
	public String saveStuff() {
		try {
			stuff = (Stuff) publicService.saveEntity(this.stuff);
			Stuff v = (Stuff) publicService.findByNativeSql("select * from t_stuff where stuffid=" + stuff.getStuffid(), Stuff.class).get(0);
			this.infos.put("stuff", v);
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 新增报时单审核操作流程
	 * 
	 * @return
	 */
	public String saveWorkflowLog() {
		try {
			User user = (User) getSession().getAttribute("user");
			this.workflow.setRecordTime(new Date());
			this.workflow.setUserid(user.getUserid());
			this.workflow.setUsername(user.getUsername());

			this.infos.put("workflow", publicService.saveWorkflowLog(this.workflow));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 新增报时单
	 * 
	 * @return
	 */
	@Action(description = "新增报时单")
	public String saveQuoteInfo() {
		try {
			this.infos.put("quoteInfo", publicService.saveEntity(this.quoteInfo));
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 保存用户信息
	 * 
	 * @return
	 */
	@Action(description = "新增帐号信息")
	public String saveUser() {
		User u = null;
		try {
			Role r = new Role(false);
			r = (Role) publicService.saveEntity(r);
			user.setRid(r.getRid());
			u = (User) publicService.saveEntity(user);
			if (u.getUserid() != null) {
				this.infos.put("user", u); // 更新后的给extjs
				this.success = true;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.infos.put("msg", e.getMessage());// 错误信息给ext
			this.success = false;
		}
		return SUCCESS;
	}

	/**
	 * 获得辅助信息参考文件集合
	 */
	@Action(description = "查询辅助信息的文件列表")
	public String findRefFilesList() {
		DetachedCriteria dc = DetachedCriteria.forClass(RefFiles.class);
		if (refFiles != null) {
			dc.add(EnhancedExample.createDefault(refFiles));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 获得辅助信息集合
	 */
	@Action(description = "查询辅助信息")
	public String findAidsList() {
		DetachedCriteria dc = DetachedCriteria.forClass(Aids.class);
		if (aids != null) {
			dc.add(Example.create(aids));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 获得产品编码集合
	 */
	@Action(description = "查询报时表的编码集合")
	public String findProductCodeList() {
		DetachedCriteria dc = DetachedCriteria.forClass(ProductCode.class);
		if (productCode != null) {
			dc.add(Example.create(productCode));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 获得报时单其他报价信息
	 */
	@Action(description = "查询报时表其他报价")
	public String findOtherPriceList() {
		DetachedCriteria dc = DetachedCriteria.forClass(OtherQuotePrice.class);
		if (otherPrice != null) {
			dc.add(Example.create(otherPrice));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 获得外发加工信息集合
	 */
	@Action(description = "查询外发加工信息")
	public String findFoundryList() {
		DetachedCriteria dc = DetachedCriteria.forClass(Foundry.class);
		if (foundry != null) {
			dc.add(Example.create(foundry));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 获得加工信息集合
	 */
	@Action(description = "查询加工信息")
	public String findProcessInfoList() {
		DetachedCriteria dc = DetachedCriteria.forClass(com.gvp.po.ProcessInfo.class);
		if (process != null) {
			dc.add(Example.create(process));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 用户分页
	 */
	@Action(description = "查询用户列表")
	public String findUserList() {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		if (user != null) {
			dc.add(EnhancedExample.createDefault(user));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 根据id查找角色
	 */
	public String findRoleById() {
		DetachedCriteria dc = DetachedCriteria.forClass(Role.class);
		if (role != null) {
			dc.add(Restrictions.eq("rid", role.getRid()));
			this.page.setResult(dc);
			this.page = publicService.getResultList(page);
			Iterator<?> it = page.getRoot().iterator();
			if (it.hasNext()) {
				this.infos.put("role", it.next());
				this.success = true;
			}
		}
		return SUCCESS;
	}

	/**
	 * 根据报时单id查找审核流程信息
	 * 
	 * @return
	 */
	public String findWorkflowByQid() {
		if (workflow != null) {
			List<?> list = publicService.getList(WorkflowLog.class, workflow.getQid());
			this.infos.put("workflowList", list);
			this.success = true;
		}
		return SUCCESS;
	}

	/**
	 * 拷贝报时信息
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Action(description = "复制报时单")
	public String copyQuoteInfos() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("quoteInfo", quoteInfo);
		try {
			this.infos = publicService.copyQuoteInfos(map);
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			this.infos.put("msg", MyUtils.getExceptionMessages(e));
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 获得关联报时信息
	 * 
	 * @return
	 */
	@Action(description = "查找与报时单关联的所有副本")
	@SuppressWarnings("unchecked")
	public String findRelationQuoteInfoList() {
		if (quoteInfo != null) {
			Page p = new Page();
			DetachedCriteria dd = DetachedCriteria.forClass(QuoteInfo.class);
			dd.add(Restrictions.eq("qid", quoteInfo.getQid()));
			p.setResult(dd);
			p = publicService.getResultList(p);
			Iterator<QuoteInfo> it = p.getRoot().iterator();
			if (it.hasNext()) {
				quoteInfo = it.next();
			}
			DetachedCriteria dc = DetachedCriteria.forClass(QuoteInfo.class);
			dc.add(Restrictions.eq("ownerId", quoteInfo.getQid()));
			this.page.setResult(dc);
			this.page = publicService.getResultList(page);
			if (quoteInfo.getOwnerId() != null) {
				DetachedCriteria odd = DetachedCriteria.forClass(QuoteInfo.class);
				odd.add(Restrictions.eq("qid", quoteInfo.getOwnerId()));
				Page pp = new Page();
				pp.setResult(odd);
				pp = publicService.getResultList(pp);
				this.page.getRoot().addAll(pp.getRoot());
			}
		}
		return SUCCESS;
	}

	/**
	 * 客户类型分组类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	public String findTreeData() throws ParseException {
		DetachedCriteria dc = DetachedCriteria.forClass(QuoteInfo.class);
		String method = this.page.getParams().get("method");
		String field = this.page.getParams().get("field");
		List<TreeNode> list = new ArrayList<TreeNode>();

		if ("findCustomerList".equals(method)) {
			List l = this.publicService.getList("SELECT cid,customerName from QuoteInfo WHERE customerType = ? GROUP BY cid,customerName", new String[] { field });
			for (Iterator<Object[]> it = l.iterator(); it.hasNext();) {
				Object[] o = it.next();
				TreeNode tn = new TreeNode();
				tn.setField(o[0].toString());
				tn.setText(o[1].toString());
				tn.setMethod("getYears");
				list.add(tn);
			}
		}
		if ("getYears".equals(method)) {
			List<QuoteInfo> years = this.publicService.getList("FROM QuoteInfo WHERE customerName = ? GROUP BY YEAR(recordTime)", new Object[] { field });
			Calendar date = Calendar.getInstance();
			for (Iterator<QuoteInfo> it = years.iterator(); it.hasNext();) {
				QuoteInfo qi = it.next();
				TreeNode tn = new TreeNode();
				date.setTime(qi.getRecordTime());
				tn.setText(String.valueOf(date.get(Calendar.YEAR)));
				tn.setField(qi.getCustomerName());
				tn.setMethod("getProductType");
				list.add(tn);
			}
		}
		if ("getProductType".equals(method)) {
			List tlist = this.publicService.getList("FROM Customer WHERE customerName = ? ", new Object[] { field });
			Customer c = (Customer) tlist.iterator().next();
			String[] p = StringUtils.split(c.getProductCode(), ", ;");

			String year = this.page.getParams().get("year");
			String cid = this.page.getParams().get("cid");
			String startDate = year + "-01-01";
			String endDate = year + "-12-31";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Integer iCid = Integer.valueOf(cid);
			Date dStartDate = df.parse(startDate);
			Date dEndDate = df.parse(endDate);
			for (String productCode : p) {
				DetachedCriteria d = DetachedCriteria.forClass(QuoteInfo.class);
				d.add(Restrictions.eq("cid", iCid));
				d.add(Restrictions.between("recordTime", dStartDate, dEndDate));
				d.add(Restrictions.like("productCode", "%" + productCode + "%"));
				Page dp = new Page();
				dp.setResult(d);
				List _l = this.publicService.getResultList(dp).getRoot();
				if (_l.isEmpty())
					continue;
				TreeNode tn = new TreeNode();
				tn.setText(productCode);
				tn.setField(productCode);
				tn.setMethod("findQuoteInfoList");
				list.add(tn);
			}
		}
		if ("findQuoteInfoList".equals(method)) {
			String year = this.page.getParams().get("year");
			String cid = this.page.getParams().get("cid");
			String startDate = year + "-01-01";
			String endDate = year + "-12-31";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			dc.add(Restrictions.eq("cid", Integer.valueOf(cid)));
			dc.add(Restrictions.like("productCode", "%" + field + "%"));
			dc.add(Restrictions.between("recordTime", df.parse(startDate), df.parse(endDate)));
			this.page.setResult(dc);
			this.page = this.publicService.getResultList(this.page);
			for (Iterator<QuoteInfo> it = this.page.getRoot().iterator(); it.hasNext();) {
				TreeNode tn = new TreeNode();
				QuoteInfo qi = it.next();
				tn.setText(qi.getProductCode());
				tn.setId(qi.getQid().toString());
				tn.setLeaf(true);
				list.add(tn);
			}
		}

		this.page.setRoot(list);
		return SUCCESS;
	}

	/**
	 * 查找未审核的报时表
	 * 
	 * @return
	 */
	@Action(description = "查找未审核的报时表")
	public String findUnCheckedQuoteInfoList() {
		DetachedCriteria dc = DetachedCriteria.forClass(QuoteInfo.class);
		dc.add(Restrictions.eq("state", "已提交审核申请"));
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	//	/**
	//	 * 获得报时信息
	//	 * 
	//	 * @return
	//	 */
	//	@SuppressWarnings("unchecked")
	//	@Action(description = "查询报时单")
	//	public String findQuoteInfoList() {
	//		DetachedCriteria dc = DetachedCriteria.forClass(QuoteInfo.class);
	//		String condition = page.getParams().get("condition");
	//		String queryLevel = page.getParams().get("queryLevel");
	//		String queryValue = page.getParams().get("queryValue");
	//		String sub = page.getParams().get("sub"); // 为空则表示查询所有关联
	//
	//		String version = page.getParams().get("version"); // 报时表版本 original(原版
	//															// native)
	//															// unoriginal(副本
	//															// copy)
	//
	//		if ("customerType".equals(queryLevel)) {
	//			dc.add(Restrictions.eq("customerType", queryValue));
	//		}
	//		if ("quoteInfo".equals(queryLevel)) {
	//			dc.add(Restrictions.eq("cid", Integer.parseInt(queryValue)));
	//		}
	//
	//		if ("native".equals(version)) {
	//			dc.add(Restrictions.isNull("ownerId"));
	//		}
	//		if ("copy".equals(version)) {
	//			dc.add(Restrictions.isNotNull("ownerId"));
	//		}
	//
	//		if (!MyUtils.isEmpty(condition)) {
	//			if (quoteInfo != null) {
	//				String state = quoteInfo.getState();
	//				if (quoteInfo.getQid() != null) {
	//					dc.add(Restrictions.eq("qid", quoteInfo.getQid()));
	//				} else {
	//					if (StringUtils.isNotEmpty(state)) {
	//						dc.add(Restrictions.eq("state", state));
	//					} else {
	//						dc.add(EnhancedExample.createDefault(quoteInfo));
	//					}
	//				}
	//				this.page.setResult(dc);
	//				this.page = publicService.getResultList(page);
	//			}
	//			if (StringUtils.isBlank(sub)) {
	//				if (materials != null) {
	//					DetachedCriteria dMaterials = DetachedCriteria.forClass(Materials.class);
	//					Page p = new Page();
	//					dMaterials.add(EnhancedExample.createDefault(materials));
	//					p.setResult(dMaterials);
	//					p = publicService.getResultList(p);
	//					Iterator<Materials> it = p.getRoot().iterator();
	//					if (it.hasNext()) {
	//						StringBuffer hql = new StringBuffer("FROM QuoteInfo WHERE qid IN(");
	//						while (it.hasNext()) {
	//							Materials m = it.next();
	//							hql.append(m.getQid());
	//							if (it.hasNext()) {
	//								hql.append(",");
	//							}
	//						}
	//						hql.append(")");
	//
	//						List results = publicService.getList(hql.toString());
	//						this.page.getRoot().addAll(results);
	//					}
	//				}
	//				if (refFiles != null) {
	//					DetachedCriteria dd = DetachedCriteria.forClass(RefFiles.class);
	//					Page p = new Page();
	//					dd.add(EnhancedExample.createDefault(refFiles));
	//					p.setResult(dd);
	//					p = publicService.getResultList(p);
	//					Iterator<RefFiles> it = p.getRoot().iterator();
	//					if (it.hasNext()) {
	//						StringBuffer hql = new StringBuffer("FROM QuoteInfo WHERE qid IN (");
	//						while (it.hasNext()) {
	//							RefFiles r = it.next();
	//							hql.append(r.getQid());
	//							if (it.hasNext()) {
	//								hql.append(",");
	//							}
	//						}
	//						hql.append(")");
	//
	//						List results = publicService.getList(hql.toString());
	//						this.page.getRoot().addAll(results);
	//					}
	//				}
	//			}
	//		} else {
	//			if (quoteInfo != null) {
	//				String state = quoteInfo.getState();
	//				if (quoteInfo.getQid() != null) {
	//					dc.add(Restrictions.eq("qid", quoteInfo.getQid()));
	//				} else {
	//					if (StringUtils.isNotEmpty(state)) {
	//						dc.add(Restrictions.eq("state", state));
	//					} else {
	//						dc.add(EnhancedExample.createDefault(quoteInfo));
	//					}
	//				}
	//			}
	//			this.page.setResult(dc);
	//			this.page = publicService.getResultList(page);
	//		}
	//		return SUCCESS;
	//	}

	/**
	 * 获得报时信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(description = "查询报时单")
	public String findQuoteInfoList() {
		String condition = page.getParams().get("condition");
		String queryLevel = page.getParams().get("queryLevel");
		String queryValue = page.getParams().get("queryValue");
		String sub = page.getParams().get("sub"); // 为空则表示查询所有关联
		String version = page.getParams().get("version"); // 报时表版本 original(原版  native)  unoriginal(副本  copy)

		List params = new ArrayList();

		StringBuilder sql = new StringBuilder("select this_.*  from  t_quote_info this_ where 1=1 ");

		if ("customerType".equals(queryLevel)) {
			sql.append(" and this_.customerType = ? ");
			params.add(queryValue);
		}
		if ("quoteInfo".equals(queryLevel)) {
			sql.append(" and this_.cid = ? ");
			params.add(queryValue);
		}
		if ("native".equals(version)) {
			sql.append(" and this_.ownerId is null ");
		}
		if ("copy".equals(version)) {
			sql.append(" and this_.ownerId is not null ");
		}

		if (!MyUtils.isEmpty(condition)) {
			if (quoteInfo != null) {
				String state = quoteInfo.getState();
				if (quoteInfo.getQid() != null) {
					sql.append(" and this_.qid = ? ");
					params.add(quoteInfo.getQid());
				} else {
					if (StringUtils.isNotEmpty(state)) {
						sql.append(" and this_.state = ? ");
						params.add(state);
					} else {
						sql.append(" and (lower(this_.customerName) like ? ");
						sql.append(" or lower(this_.customerType) like ? ");
						sql.append(" or lower(this_.productCode) like ? ");
						sql.append(" or lower(this_.quoter) like ? ");
						sql.append(" or lower(this_.price) like ? ");
						sql.append(" or lower(this_.dccNo) like ? ");
						sql.append(" or lower(this_.pageNo) like ? )");

						params.add("%" + quoteInfo.getCustomerName() + "%");
						params.add("%" + quoteInfo.getCustomerType() + "%");
						params.add("%" + quoteInfo.getProductCode() + "%");
						params.add("%" + quoteInfo.getQuoter() + "%");
						params.add("%" + quoteInfo.getPrice() + "%");
						params.add("%" + quoteInfo.getDccNo() + "%");
						params.add("%" + quoteInfo.getPageNo() + "%");
					}
				}
			}
			if (StringUtils.isBlank(sub)) {
				if (materials != null) {
					sql.append(" union all ");
					sql.append(" select q.* from t_quote_info q ");
					sql.append(" where q.qid in ( ");
					sql.append(" select m.qid from t_materials m ");
					sql.append(" where ( lower(m.productsName) like ? or lower(m.materialsName) like ? or lower(m.diameter) like ?)) ");
					params.add("%" + materials.getProductsName() + "%");
					params.add("%" + materials.getMaterialsName() + "%");
					params.add("%" + materials.getDiameter() + "%");
				}
				if (refFiles != null) {
					sql.append(" union all");
					sql.append(" select q.* from t_quote_info q ");
					sql.append(" where q.qid in (");
					sql.append(" select r.qid from t_ref_files r ");
					sql.append(" where (lower(r.remark) like ?)) ");
					params.add("%" + refFiles.getRemark() + "%");
				}
			}
		} else {
			if (quoteInfo != null) {
				String state = quoteInfo.getState();
				if (quoteInfo.getQid() != null) {
					sql.append("and this_.qid = ? ");
					params.add(quoteInfo.getQid());
				} else {
					if (StringUtils.isNotEmpty(state)) {
						sql.append(" and this_.state = ? ");
						params.add(state);
					} else {
						sql.append(" and (lower(this_.customerName) like ? ");
						sql.append(" or lower(this_.customerType) like ? ");
						sql.append(" or lower(this_.productCode) like ? ");
						sql.append(" or lower(this_.quoter) like ? ");
						sql.append(" or lower(this_.price) like ? ");
						sql.append(" or lower(this_.dccNo) like ? ");
						sql.append(" or lower(this_.pageNo) like ? )");

						params.add("%" + quoteInfo.getCustomerName() + "%");
						params.add("%" + quoteInfo.getCustomerType() + "%");
						params.add("%" + quoteInfo.getProductCode() + "%");
						params.add("%" + quoteInfo.getQuoter() + "%");
						params.add("%" + quoteInfo.getPrice() + "%");
						params.add("%" + quoteInfo.getDccNo() + "%");
						params.add("%" + quoteInfo.getPageNo() + "%");
					}
				}
			}
		}
		this.page = publicService.getResultListBySpringJDBC(page, sql.toString(), params.toArray(), new QuoteInfoMapper());
		return SUCCESS;
	}

	//	/**
	//	 * 获得报时信息
	//	 * 
	//	 * @return
	//	 */
	//	@SuppressWarnings("unchecked")
	//	@Action(description = "查询报时单")
	//	public String findQuoteInfoList() {
	//		DetachedCriteria dc = DetachedCriteria.forClass(QuoteInfo.class);
	//		String condition = page.getParams().get("condition");
	//		String queryLevel = page.getParams().get("queryLevel");
	//		String queryValue = page.getParams().get("queryValue");
	//		
	//		if ("customerType".equals(queryLevel)) {
	//			dc.add(Restrictions.eq("customerType", queryValue));
	//		}
	//		if ("quoteInfo".equals(queryLevel)) {
	//			dc.add(Restrictions.eq("cid", Integer.parseInt(queryValue)));
	//		}
	//		
	//		if (!MyUtils.isEmpty(condition)) {
	//			if (quoteInfo != null) {
	//				if (quoteInfo.getQid() != null) {
	//					dc.add(Restrictions.eq("qid", quoteInfo.getQid()));
	//				} else {
	//					dc.add(EnhancedExample.createDefault(quoteInfo));
	//				}
	//				this.page.setResult(dc);
	//				this.page = publicService.getResultList(page);
	//			}
	//			if (materials != null) {
	//				DetachedCriteria dMaterials = DetachedCriteria.forClass(Materials.class);
	//				Page p = new Page();
	//				dMaterials.add(EnhancedExample.createDefault(materials));
	//				p.setResult(dMaterials);
	//				p = publicService.getResultList(p);
	//				Iterator<Materials> it = p.getRoot().iterator();
	//				if (it.hasNext()) {
	//					StringBuffer hql = new StringBuffer("FROM QuoteInfo WHERE qid IN(");
	//					while (it.hasNext()) {
	//						Materials m = it.next();
	//						hql.append(m.getQid());
	//						if (it.hasNext()) {
	//							hql.append(",");
	//						}
	//					}
	//					hql.append(")");
	//					
	//					List results = publicService.getList(hql.toString());
	//					this.page.getRoot().addAll(results);
	//				}
	//			}
	//			if (refFiles != null) {
	//				DetachedCriteria dd = DetachedCriteria.forClass(RefFiles.class);
	//				Page p = new Page();
	//				dd.add(EnhancedExample.createDefault(refFiles));
	//				p.setResult(dd);
	//				p = publicService.getResultList(p);
	//				Iterator<RefFiles> it = p.getRoot().iterator();
	//				if (it.hasNext()) {
	//					StringBuffer hql = new StringBuffer("FROM QuoteInfo WHERE qid IN (");
	//					while (it.hasNext()) {
	//						RefFiles r = it.next();
	//						hql.append(r.getQid());
	//						if (it.hasNext()) {
	//							hql.append(",");
	//						}
	//					}
	//					hql.append(")");
	//					
	//					List results = publicService.getList(hql.toString());
	//					this.page.getRoot().addAll(results);
	//				}
	//			}
	//		} else {
	//			if (null != quoteInfo.getQid()) {
	//				dc.add(Restrictions.eq("qid", quoteInfo.getQid()));
	//			}
	//			this.page.setResult(dc);
	//			this.page = publicService.getResultList(page);
	//		}
	//		return SUCCESS;
	//	}

	/**
	 * 根据id找生产材料信息
	 * 
	 * @return
	 */
	public String findMaterialsById() {
		Integer qid = quoteInfo.getQid();
		try {
			List list = publicService.getList(MaterialsView.class, qid);
			if (list != null && !list.isEmpty()) {
				this.infos.put("materials", list.get(0));
			}
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 根据id找参考信息
	 * 
	 * @return
	 */
	public String findReferenceInfoById() {
		Integer qid = quoteInfo.getQid();
		try {
			List list = publicService.getList(ReferenceInfo.class, qid);
			if (list != null && !list.isEmpty()) {
				this.infos.put("reference", list.get(0));
			}
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 根据报时表获得报时表所有相关数据
	 * 
	 * @return
	 */
	public String findQuoteInfoById() {
		Integer qid = quoteInfo.getQid();
		Boolean relation = Boolean.valueOf(page.getParams().get("relation"));
		try {
			this.infos = publicService.findQuoteInfoById(qid, relation);
			this.success = true;
		} catch (RuntimeException e) {
			this.success = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 获得客户列表
	 * 
	 * @return
	 */
	@Action(description = "查找客户信息")
	public String findCustomerList() {
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		if (customer != null) {
			dc.add(EnhancedExample.createDefault(customer));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 查找系统日志
	 * 
	 * @return
	 */
	@Action(description = "查找系统日志")
	public String findSystemLogList() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemLog.class);
		if (log != null) {
			dc.add(EnhancedExample.createDefault(log, false));
		}
		dc.addOrder(Order.desc("recordTime"));
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 查找供应商材料单价调整信息
	 * 
	 * @return
	 */
	@Action(description = "查找供应商材料单价修改信息")
	public String findPriceList() {
		//		StringBuilder sql = new StringBuilder();
		//		sql.append(" SELECT tst.stuffName,");
		//		sql.append(" ts.speciesName,");
		//		sql.append(" tsp.specName,");
		//		sql.append(" tp.price,");
		//		sql.append(" tp.remark,");
		//		sql.append(" tp.recordTime,");
		//		sql.append(" tp.listid");
		//		sql.append(" FROM t_price_list    AS tp,");
		//		sql.append(" t_species       AS ts,");
		//		sql.append(" t_specification AS tsp,");
		//		sql.append(" t_stuff         AS tst");
		//		sql.append(" WHERE tp.stuffid = tst.stuffid");
		//		sql.append(" AND tp.speciesid = ts.speciesid");
		//		sql.append(" AND tp.specid = tsp.specid");

		//		List<String> params = new ArrayList<String>();
		//		if (priceList != null) {
		//			sql.append(" AND (tp.remark like ? OR tst.stuffName like ? OR ts.speciesName like ? OR tsp.specName like ? )");
		//			StringBuilder likeParam = new StringBuilder();
		//			likeParam.append("%").append(priceList.getRemark()).append("%");
		//			String param = likeParam.toString();
		//			params.add(param);
		//			params.add(param);
		//			params.add(param);
		//			params.add(param);
		//		}
		//
		//		this.page = publicService.getResultListBySpringJDBC(page, sql.toString(), params.toArray(), new PriceListMapper());
		DetachedCriteria dc = DetachedCriteria.forClass(PriceListView.class);
		if (priceListView != null) {
			dc.add(EnhancedExample.createDefault(priceListView,false));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 查找种类信息
	 * 
	 * @return
	 */
	@Action(description = "查找种类信息")
	public String findSpeciesList() {
		String isAndRelation = page.getParams().get("isAndRelation");
		boolean relation = false;
		if("true".equals(isAndRelation)){
			relation = true;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SpeciesView.class);
		if (speciesView != null) {
			dc.add(EnhancedExample.createDefault(speciesView,relation));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 查找材质信息
	 * 
	 * @return
	 */
	@Action(description = "查找材质信息")
	public String findStuffList() {
		DetachedCriteria dc = DetachedCriteria.forClass(Stuff.class);
		if (stuff != null) {
			dc.add(EnhancedExample.createDefault(stuff,true));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 查找规格信息
	 * 
	 * @return
	 */
	@Action(description = "查找规格信息")
	public String findSpecificationList() {
		DetachedCriteria dc = DetachedCriteria.forClass(SpecificationView.class);
		if (specificationView != null) {
			boolean isAndRelation = Boolean.valueOf(page.getParams().get("isAndRelation"));
			dc.add(EnhancedExample.createDefault(specificationView,isAndRelation));
		}
		this.page.setResult(dc);
		this.page = publicService.getResultList(page);
		return SUCCESS;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(description = "修改密码")
	public String changePassword() throws Exception {
		String old = this.page.getParams().get("pwd_old");
		String pwd1 = this.page.getParams().get("pwd1");
		String pwd2 = this.page.getParams().get("pwd2");
		User u = (User) getSession().getAttribute("user");
		if (u.getPassword().equals(old)) {
			if (pwd1.equals(pwd2)) {
				u.setPassword(pwd1);
				u = (User) publicService.updateEntity(u, u.getUserid(), null);
				getSession().setAttribute("user", u);
				this.success = true;
			} else {
				this.infos.put("msg", "两次密码不一致!");
			}
		} else {
			this.infos.put("msg", "原始密码验证失败!");
		}
		return SUCCESS;
	}

	/**
	 * 注销
	 * 
	 * @return
	 */
	@Action(description = "退出系统")
	public String logout() {
		getSession().removeAttribute("user");
		success = true;
		return SUCCESS;
	}

	/**
	 * 登录方法
	 * 
	 * @return
	 */
	@Action(description = "登录系统")
	public String login() {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Example.create(user));
		page.setResult(dc);
		page = publicService.getResultList(page);
		Iterator it = page.getRoot().iterator();
		if (it.hasNext()) {
			User u = (User) it.next();
			getSession().setAttribute("user", u);
			Page p = new Page();
			DetachedCriteria rdc = DetachedCriteria.forClass(Role.class);
			rdc.add(Restrictions.eq("rid", u.getRid()));
			p.setResult(rdc);
			p = publicService.getResultList(p);
			Iterator roleit = p.getRoot().iterator();
			if (roleit.hasNext()) {
				getSession().setAttribute("role", p.getRoot().iterator().next());
			}
			this.success = true;
			this.infos.put("path", "page/default.jsp");
		} else {
			this.infos.put("error_msg", "登录失败,请检查帐号,密码的正确性!");
		}
		return SUCCESS;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, Object> getInfos() {
		return infos;
	}

	public void setInfos(Map<String, Object> infos) {
		this.infos = infos;
	}

	public void setPublicService(IPublicService userService) {
		this.publicService = userService;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public QuoteInfo getQuoteInfo() {
		return quoteInfo;
	}

	public void setQuoteInfo(QuoteInfo quoteInfo) {
		this.quoteInfo = quoteInfo;
	}

	public Foundry getFoundry() {
		return foundry;
	}

	public void setFoundry(Foundry foundry) {
		this.foundry = foundry;
	}

	public Aids getAids() {
		return aids;
	}

	public void setAids(Aids aids) {
		this.aids = aids;
	}

	public RefFiles getRefFiles() {
		return refFiles;
	}

	public void setRefFiles(RefFiles refFiles) {
		this.refFiles = refFiles;
	}

	public Materials getMaterials() {
		return materials;
	}

	public void setMaterials(Materials materials) {
		this.materials = materials;
	}

	public void setReference(ReferenceInfo reference) {
		this.reference = reference;
	}

	public ReferenceInfo getReference() {
		return reference;
	}

	public ProcessInfo getProcess() {
		return process;
	}

	public void setProcess(ProcessInfo processInfo) {
		this.process = processInfo;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Stuff getStuff() {
		return stuff;
	}

	public void setStuff(Stuff stuff) {
		this.stuff = stuff;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public ProductCode getProductCode() {
		return productCode;
	}

	public void setProductCode(ProductCode productCode) {
		this.productCode = productCode;
	}

	public Date getStartModifyTime() {
		return startModifyTime;
	}

	public void setStartModifyTime(Date startModifyTime) {
		this.startModifyTime = startModifyTime;
	}

	public Date getEndModifyTime() {
		return endModifyTime;
	}

	public void setEndModifyTime(Date endModifyTime) {
		this.endModifyTime = endModifyTime;
	}

	public Date getStartRecordTime() {
		return startRecordTime;
	}

	public void setStartRecordTime(Date startRecordTime) {
		this.startRecordTime = startRecordTime;
	}

	public Date getEndRecordTime() {
		return endRecordTime;
	}

	public void setEndRecordTime(Date endRecordTime) {
		this.endRecordTime = endRecordTime;
	}

	public OtherQuotePrice getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(OtherQuotePrice otherPrice) {
		this.otherPrice = otherPrice;
	}

	public WorkflowLog getWorkflow() {
		return workflow;
	}

	public void setWorkflow(WorkflowLog work) {
		this.workflow = work;
	}

	public SystemLog getLog() {
		return log;
	}

	public void setLog(SystemLog log) {
		this.log = log;
	}


	public SpecificationView getSpecificationView() {
		return specificationView;
	}

	public void setSpecificationView(SpecificationView specificationView) {
		this.specificationView = specificationView;
	}

	public SpeciesView getSpeciesView() {
		return speciesView;
	}

	public void setSpeciesView(SpeciesView speciesView) {
		this.speciesView = speciesView;
	}

	public PriceListView getPriceListView() {
		return priceListView;
	}

	public void setPriceListView(PriceListView priceListView) {
		this.priceListView = priceListView;
	}

	public PriceList getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}
}
