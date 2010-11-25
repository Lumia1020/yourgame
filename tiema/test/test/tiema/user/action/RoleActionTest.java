package test.tiema.user.action;

import static org.junit.Assert.*;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.tiema.role.action.RoleAction;

import test.tiema.user.core.BaseTest;

public class RoleActionTest extends BaseTest {

	private RoleAction roleAction;

	@Before
	public void setUp() {
		roleAction.setSession(new HashMap<String, Object>());
		roleAction.setParameters(new HashMap<String, String[]>());
		roleAction.setRequest(new HashMap<String, Object>());
		roleAction.setApplication(new HashMap<String, Object>());
	}


	@Test
	public final void testFindRolePermission() {
		roleAction.findRolePermission();
	}

	@Resource
	public void setRoleAction(RoleAction roleAction) {
		this.roleAction = roleAction;
	}

}
