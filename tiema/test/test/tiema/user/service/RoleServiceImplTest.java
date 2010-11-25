package test.tiema.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import test.tiema.user.core.BaseTest;

import com.tiema.permission.entity.Permission;
import com.tiema.permission.service.PermissionService;
import com.tiema.role.entity.Role;
import com.tiema.role.service.RoleService;

public class RoleServiceImplTest extends BaseTest{
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private PermissionService permissionService;

	@Before
	public void setUp() throws Exception {
	}

//	@Rollback(false)
	@Test
	public final void testAdd() throws Exception {
		Role entity = new Role();
		int r = countRowsInTable("t_role");
		entity.setRemark("操作员");
		entity.setRoleName("操作员");
		roleService.add(entity);
		flush();
		int r2 = countRowsInTable("t_role");
		Assert.assertTrue(r2 > r);
	}

	@Test
	public final void testDelete() throws Exception {
		Role entity = new Role();
		entity.setRemark("test");
		entity.setRoleName("test");
		roleService.add(entity);
		flush();
		int r2 = countRowsInTable("t_role");
		roleService.delete(entity);
		flush();
		int r = countRowsInTable("t_role");
		
		Assert.assertTrue(r2 > r);
	}

	@Test
	public final void testDeleteById() throws Exception {
		Role entity = new Role();
		entity.setRemark("test");
		entity.setRoleName("test");
		roleService.add(entity);
		flush();
		int r2 = countRowsInTable("t_role");
		roleService.deleteById(entity.getId());
		flush();
		int r = countRowsInTable("t_role");
		
		Assert.assertTrue(r2 > r);
	}
	@Test
	@Rollback(false)
	public final void testDeleteById2() throws Exception {
		roleService.deleteById(2l);
	}

	@Test
	public final void testExists() throws Exception {
		Role entity = new Role();
		entity.setRemark("test");
		entity.setRoleName("test");
		roleService.add(entity);
		Role r = new Role();
		r.setRoleName("test");
		Assert.assertTrue(roleService.exists(r));
	}

	@Test
	public final void testFindById() throws Exception {
		Role entity = new Role();
		entity.setRemark("test");
		entity.setRoleName("test");
		roleService.add(entity);
		Role r = roleService.findById(entity.getId());
		Assert.assertSame(r, entity);
	}
	
	@Test
	@Rollback(false)
	public final void testFindById2() throws Exception{
		Role entity = new Role();
		entity.setRemark("test");
		entity.setRoleName("test");
		List list = permissionService.findEntities();
		Set sets = new HashSet<Permission>();
		sets.addAll(list);
		entity.setPermissions(sets);
		roleService.add(entity);
		Role r = roleService.findById(entity.getId());
	}

	@Test
	public final void testFindEntities() throws Exception {
		Role entity = new Role();
		entity.setRemark("test");
		entity.setRoleName("test");
		int s1 = roleService.findEntities().size();
		roleService.add(entity);
		int s2 = roleService.findEntities().size();
		Assert.assertFalse(s1 == s2);
	}


	@Test
	public final void testUpdate() throws Exception {
		Role entity = new Role();
		entity.setRemark("test");
		entity.setRoleName("test");
		roleService.add(entity);
		entity.setRemark("abc");
		Role u = roleService.update(entity);
		Assert.assertTrue("abc".equals(u.getRemark()));
	}

}

