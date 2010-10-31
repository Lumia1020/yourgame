package test.service;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tiema.user.entity.User;
import com.tiema.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/test-beans.xml")
public class UserServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource
	private UserService userService;

	@Test
	public void testAdd() throws Exception {
		User user = new User();
		user.setUsername("abc");
		user.setPassword("a");
		Assert.assertSame(userService.add(user), userService.findById(user.getId()));
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
	public void testFindUsers() {
		List list = userService.findUsers();
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
