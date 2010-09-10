package com.gvp.test;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.gvp.po.Customer;
import com.gvp.po.Role;
import com.gvp.po.Species;
import com.gvp.po.Specification;
import com.gvp.po.Stuff;
import com.gvp.po.User;

public class ExportDB {

	public static void main(String[] args) {
		// 读取配置文件hibernate.cfg.xml
		Configuration cfg = new Configuration().configure();

		// 创建SchemaExport对象
		SchemaExport export = new SchemaExport(cfg);

		// 创建数据库表
		export.create(true, true);
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		Role r = new Role(true);
		session.save(r);
		User user = new User();
		user.setUsername("a");
		user.setPassword("a");
		user.setName("Jack");
		user.setRid(r.getRid());
		session.save(user);
		Role r2 = new Role(false);
		session.save(r2);
		User u = new User();
		u.setUsername("b");
		u.setPassword("b");
		u.setName("廖瀚卿");
		u.setRid(r2.getRid());
		session.save(u);
		
		Customer u1 = new Customer("中国电信","主要客户","33_44");
		Customer u2 = new Customer("中国移动","主要客户","33_44");
		Customer u3 = new Customer("中国联通","其他客户","33_44");
		Customer u4 = new Customer("中国网通","其他客户","33_44");
		session.save(u1);
		session.save(u2);
		session.save(u3);
		session.save(u4);
		
		Stuff s1 = new Stuff(null,"金");
		Stuff s2 = new Stuff(null,"银");
		Stuff s3 = new Stuff(null,"铜");
		Stuff s4 = new Stuff(null,"铁");
		session.save(s1);
		session.save(s2);
		session.save(s3);
		session.save(s4);
		
		Species c1 = new Species(null,"白金",s1.getStuffid(),s1.getStuffName());
		Species c2 = new Species(null,"黄金",s1.getStuffid(),s1.getStuffName());
		Species c3 = new Species(null,"乌金",s1.getStuffid(),s1.getStuffName());
		Species c4 = new Species(null,"纯金",s1.getStuffid(),s1.getStuffName());
		Species c5 = new Species(null,"奥金",s1.getStuffid(),s1.getStuffName());
		
		Species e1 = new Species(null,"白银",s2.getStuffid(),s2.getStuffName());
		Species e2 = new Species(null,"纯银",s2.getStuffid(),s2.getStuffName());
		Species e3 = new Species(null,"20银",s2.getStuffid(),s2.getStuffName());
		Species e4 = new Species(null,"C30银",s2.getStuffid(),s2.getStuffName());
		Species e5 = new Species(null,"37dd",s2.getStuffid(),s2.getStuffName());
		
		session.save(c1);
		session.save(c2);
		session.save(c3);
		session.save(c4);
		session.save(c5);
		session.save(e1);
		session.save(e2);
		session.save(e3);
		session.save(e4);
		session.save(e5);
		

		Specification f1 = new Specification(null,"3721",s1.getStuffid(),s1.getStuffName(),c1.getSpeciesid(),c1.getSpeciesName(),"30");
		Specification f2 = new Specification(null,"3309",s2.getStuffid(),s2.getStuffName(),e1.getSpeciesid(),e1.getSpeciesName(),"20.33");
		Specification f3 = new Specification(null,"1003",s1.getStuffid(),s1.getStuffName(),c2.getSpeciesid(),c2.getSpeciesName(),"33.2");
		Specification f4 = new Specification(null,"3986",s1.getStuffid(),s1.getStuffName(),c3.getSpeciesid(),c3.getSpeciesName(),"19.3");
		Specification f5 = new Specification(null,"3098",s1.getStuffid(),s1.getStuffName(),c4.getSpeciesid(),c4.getSpeciesName(),"800");
		Specification f6 = new Specification(null,"2876",s2.getStuffid(),s2.getStuffName(),e2.getSpeciesid(),e2.getSpeciesName(),"3.02");
		Specification f7 = new Specification(null,"9854",s2.getStuffid(),s2.getStuffName(),e3.getSpeciesid(),e3.getSpeciesName(),"20");
		Specification f8 = new Specification(null,"0965",s2.getStuffid(),s2.getStuffName(),e4.getSpeciesid(),e4.getSpeciesName(),"330");
		
		
		session.save(f1);
		session.save(f2);
		session.save(f3);
		session.save(f4);
		session.save(f5);
		session.save(f6);
		session.save(f7);
		session.save(f8);
		session.getTransaction().commit();
		HibernateUtils.closeSession(session);
	}
}
