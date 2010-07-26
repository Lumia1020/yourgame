package com.lhq.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.lhq.po.GetData;

public class ExportDB {

	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

//		// 读取配置文件hibernate.cfg.xml
//		Configuration cfg = new Configuration().configure();
//
//		// 创建SchemaExport对象
//		SchemaExport export = new SchemaExport(cfg);
//
//		// 创建数据库表
//		export.create(true, true);
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
//		String hql = "select a.section_name from t_zh_getdata a inner join(select press_code from t_zh_dispense where station_name='前山' and r_flag='1') b on a.press_code=b.press_code group by a.section_name order by cast(a.section_name as int)";
		String hql = "";
		SQLQuery q = session.createSQLQuery(hql);
		List list = q.list();
		System.out.println(list);
		
//		Permissions ap = new Permissions();
//		ap.setRoleName(null);
//		ap.setUuid(null);
//		session.save(ap);
//		User u = new User();
//		u.setDeptId(d.getDeptId());
//		u.setPid(ap.getUuid());
//		u.setUserId("b");
//		u.setPassword("b");
//		u.setUserName("管理员");
//		session.save(u);
		
		session.getTransaction().commit();
		HibernateUtils.closeSession(session);
	}
}
