package test.tiema.user.dao;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tiema.user.dao.UserDao;
import com.tiema.user.entity.User;
import com.tiema.user.entity.UserView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test-beans.xml" })
public class UserDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private UserDao userDao;

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Test
	public void testFindUserView(){
		List list = userDao.findByCriteria(DetachedCriteria.forClass(UserView.class));
		Assert.assertTrue(list.size() > 0);
		Assert.assertNotNull(list);
	}


	@Test
	public void testDelete() {
		User u = new User();
		u.setUsername("abc");
		u.setPassword("abc");
		userDao.save(u);
		User _u = userDao.findById(u.getId());
		Assert.assertNotNull(_u);
		userDao.delete(u);
		User __u = userDao.findById(u.getId());
		Assert.assertNull(__u);
	}

	@Test
	public void testDeleteAll() {
		List list = userDao.findEntities();
		int rows = countRowsInTable("t_user");
		Assert.assertEquals(list.size(), rows);
		userDao.deleteAll(list);
		userDao.flush();
		Assert.assertEquals(0, countRowsInTable("t_user"));
	}

	@Test
	public void testDeleteById() {
		User u = new User();
		u.setUsername("abc");
		u.setPassword("abc");
		userDao.save(u);
		Long id = u.getId();
		userDao.deleteById(id);
		User _u = userDao.findById(id);
		Assert.assertNull(_u);
	}

	@Test
	public void testFindById() {
		User u = new User();
		u.setUsername("abc");
		u.setPassword("abc");
		userDao.save(u);
		User _u = userDao.findById(u.getId());
		Assert.assertSame(u, _u);
	}

	@Test
	public void testSave() {
		User u = new User();
		u.setUsername("abc");
		u.setPassword("abc");
		userDao.save(u);
		User _u = userDao.findById(u.getId());
		Assert.assertNotNull(_u);
		Assert.assertEquals(u.getId(), _u.getId());
	}

	@Test
	public void testUpdate() {
		User u = new User();
		u.setUsername("abc");
		u.setPassword("abc");
		userDao.save(u);
		u.setChineseName("aaa");
		userDao.update(u);
		User _u = userDao.findById(u.getId());
		Assert.assertEquals(u.getChineseName(), _u.getChineseName());
	}

}
