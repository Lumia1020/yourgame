package test.tiema.user.action;

import java.util.HashMap;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test.tiema.user.core.BaseTest;

import com.tiema.user.action.UserAction;
import com.tiema.user.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test-beans.xml" })
public class UserActionTest extends BaseTest {

	private UserAction userAction;


	@Before
	public void setUp() {
		userAction.setSession(new HashMap<String, Object>());
		userAction.setParameters(new HashMap<String, String[]>());
		userAction.setRequest(new HashMap<String, Object>());
		userAction.setApplication(new HashMap<String, Object>());
	}

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
		Assert.assertNotNull(userAction.getUser().getId());
	}

	@Test
	public void testLogin() throws Exception {
		int r = this.countRowsInTable("t_user");
		User user = new User();
		user.setUsername("abc");
		user.setPassword("abc");
		user.setChineseName("廖瀚卿");
		userAction.setUser(user);
		userAction.add();
		flush();
		int r2 = this.countRowsInTable("t_user");
		userAction.login();
		Assert.assertTrue(r2 != r);
		Assert.assertTrue(userAction.getSuccess());
	}

}
