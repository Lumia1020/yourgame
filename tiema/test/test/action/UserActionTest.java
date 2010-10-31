package test.action;


import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tiema.user.action.UserAction;
import com.tiema.user.dao.UserDao;
import com.tiema.user.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test-beans.xml" })
public class UserActionTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Override
	protected int countRowsInTable(String tableName) {
		return super.countRowsInTable(tableName);
	}

	private UserAction userAction;

	private UserDao userDao;


	@Resource
	public void setUserAction(UserAction userAction) {
		this.userAction = userAction;
	}


	@Test
	public void testAdd() throws Exception {
		User user = new User();
		user.setUsername("nihao");
		user.setPassword("sdf");
		userAction.setUser(user);
		userAction.add();
//		userDao.flush();
		Assert.assertNotNull(userAction.getUser().getId());
//		Long id = user.getId();
//		String q = simpleJdbcTemplate.queryForObject("select username from t_user where id=?", String.class, id);
//		Assert.assertEquals(q, "nihao");
	}

	@Test
	public void testLogin() throws Exception {
		int r = this.countRowsInTable("t_user");
		User user = new User();
		user.setUsername("abc");
		user.setPassword("abc");
		userAction.setUser(user);
		userAction.add();
		userDao.flush();
		int r2 = this.countRowsInTable("t_user");
		userAction.login();
		Assert.assertTrue(r2 != r);
		Assert.assertTrue(userAction.getSuccess());
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
