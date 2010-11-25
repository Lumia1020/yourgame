package test.tiema.user.core;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test-beans.xml" })
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	/**
	 * 刷新sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * 
	 * sessionFactory名默认为"sessionFactory".
	 * 
	 * @see #flush(String)
	 */
	protected void flush() {
		flush("sessionFactory");
	}

	/**
	 * 刷新sessionFactory,强制Hibernate执行SQL以验证ORM配置. 因为没有执行commit操作,不会更改测试数据库.
	 * 
	 * @param sessionFactoryName applicationContext中sessionFactory的名称.
	 */
	protected void flush(final String sessionFactoryName) {
		((SessionFactory) applicationContext.getBean(sessionFactoryName)).getCurrentSession().flush();
	}
}
