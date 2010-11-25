package test.tiema.user.service;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tiema.core.constant.State;
import com.tiema.user.entity.User;
import com.tiema.user.entity.UserView;
import com.tiema.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/test-beans.xml")
public class UserServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource
	private UserService	userService;

	@Test
	public void testUpdateUserReturnView() throws Exception {
		User user = new User();
		user.setUsername("abc");
		user.setPassword("a");
		user.setChineseName("abc");
		user.setState(State.VALID);
		userService.add(user);
		UserView v = null;
		user.setChineseName("bca");
		v = userService.updateUserReturnView(user);
		User _u = userService.findById(user.getId());
		Assert.assertEquals(_u.getChineseName(), v.getChineseName());
	}

	@Test
	public void testAdd() throws Exception {
		User user = new User();
		user.setUsername("abc");
		user.setChineseName("abc");
		user.setState(State.VALID);
		user.setPassword("a");
		Assert.assertSame(userService.add(user), userService.findById(user.getId()));
		Assert.assertNotNull(user.getId());
	}

	@Test
	public void testExistsString() throws Exception {
		User user = new User();
		user.setUsername("abc");
		user.setPassword("a");
		userService.add(user);
		Assert.assertTrue(userService.exists(user.getUsername()));
	}

	@Test
	public void testExistsUser() throws Exception {
		User user = new User();
		user.setUsername("abc");
		user.setPassword("a");
		Assert.assertSame(userService.add(user), userService.exists(user));
	}

	@Test
	public void testFindUsers() throws Exception {
		User user = new User();
		user.setUsername("abc");
		user.setPassword("a");
		userService.add(user);
		List list = userService.findEntities();
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testFindById() throws Exception {
		User user = new User();
		user.setUsername("abc");
		user.setPassword("a");
		userService.add(user);
		Assert.assertSame(userService.findById(user.getId()), user);
	}

}
