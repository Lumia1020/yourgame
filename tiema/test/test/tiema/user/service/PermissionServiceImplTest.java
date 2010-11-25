package test.tiema.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import test.tiema.user.core.BaseTest;

import com.tiema.agency.company.entity.AgencyCompany;
import com.tiema.agency.company.service.AgencyCompanyService;
import com.tiema.core.constant.Sex;
import com.tiema.core.constant.State;
import com.tiema.golf.club.entity.GolfClub;
import com.tiema.golf.club.service.GolfClubService;
import com.tiema.permission.entity.Permission;
import com.tiema.permission.service.PermissionService;
import com.tiema.role.entity.Role;
import com.tiema.role.service.RoleService;
import com.tiema.seller.entity.Seller;
import com.tiema.seller.service.SellerService;
import com.tiema.user.entity.User;
import com.tiema.user.service.UserService;

public class PermissionServiceImplTest extends BaseTest {

	@Resource
	private SellerService			sellerService;
	@Resource
	private PermissionService		permissionService;
	@Resource
	private UserService				userService;
	@Resource
	private RoleService				roleService;

	@Resource
	private GolfClubService			golfClubService;
	@Resource
	private AgencyCompanyService	agencyCompanyService;

	@Before
	public void setUp() throws Exception {
	}

	//@Test
	//@Rollback(false)
	public void initPermission() throws Exception {
		//		1	预定管理		
		//		2	财务管理		
		//		3	客户管理		
		//		4	系统管理		
		//		5	用户设置	default/user/user_manage	4
		//		6	角色设置	default/role/role_manage	4
		//		7	高尔夫俱乐部设置		4
		//		8	中介公司设置		4
		//		9	销售员设置		4
		//		10	会籍种类设置		4
		//		11	付款方式设置		4
		//		12	增加用户	<input id="btn_show_add" value="增加用户" class="wd3 btn" type="button" />	5
		//		13	修改用户	<input id="btn_show_update" value="修改用户" class="wd3 btn" disabled="disabled" type="button" />	5
		//		14	删除用户	<input id="btn_delete" value="删除用户" class="wd3 btn" disabled="disabled" type="button" />	5
		//		15	查询用户	<input id="btn_show_query" value="综合查询" class="wd3 btn" type="button" />	5
		//		16	增加角色	<input id="btn_show_add" value="增加角色" class="wd3 btn" type="button" />	6
		//		17	修改角色	<input id="btn_show_update" value="修改角色" class="wd3 btn" disabled="disabled" type="button" />	6
		//		18	删除角色	<input id="btn_delete" value="删除角色" class="wd3 btn" disabled="disabled" type="button" />	6
		//		19	权限设置	<input id="btn_show_role_setup" value="权限设置" class="wd3 btn" disabled="disabled" type="button" />	6
		//		20	查询角色	<input id="btn_show_query" value="综合查询" class="wd3 btn" type="button" />	6

		Permission p1 = new Permission("预定管理");
		Permission p2 = new Permission("财务管理");
		Permission p3 = new Permission("客户管理");
		Permission p4 = new Permission("系统管理");
		Permission p41 = new Permission("用户设置", "default/user/user_manage", p4);
		Permission p42 = new Permission("角色设置", "default/role/role_manage", p4);
		Permission p43 = new Permission("高尔夫俱乐部设置", null, p4);
		Permission p44 = new Permission("中介公司设置", null, p4);
		Permission p45 = new Permission("销售员设置", null, p4);
		Permission p46 = new Permission("会籍种类设置", null, p4);
		Permission p47 = new Permission("付款方式设置", null, p4);
		Permission p48 = new Permission("增加用户", "<input id='btn_show_add' value='增加用户' class='wd3 btn' type='button' />", p41);
		Permission p49 = new Permission("修改用户", "<input id='btn_show_update' value='修改用户' class='wd3 btn' disabled='disabled' type='button' />	", p41);
		Permission p50 = new Permission("删除用户", "<input id='btn_delete' value='删除用户' class='wd3 btn' disabled='disabled' type='button' />", p41);
		Permission p51 = new Permission("查询用户", "<input id='btn_show_query' value='综合查询' class='wd3 btn' type='button' />", p41);
		Permission p52 = new Permission("增加角色", "<input id='btn_show_add' value='增加角色' class='wd3 btn' type='button' />", p42);
		Permission p53 = new Permission("修改角色", "<input id='btn_show_update' value='修改角色' class='wd3 btn' disabled='disabled' type='button' />", p42);
		Permission p54 = new Permission("删除角色", "<input id='btn_delete' value='删除角色' class='wd3 btn' disabled='disabled' type='button' />", p42);
		Permission p55 = new Permission("权限设置", "<input id='btn_show_role_setup' value='权限设置' class='wd3 btn' disabled='disabled' type='button' />", p42);
		Permission p56 = new Permission("查询角色", "<input id='btn_show_query' value='综合查询' class='wd3 btn' type='button' />", p42);

		p4.getChildren().add(p41);
		p4.getChildren().add(p42);
		p4.getChildren().add(p43);
		p4.getChildren().add(p44);
		p4.getChildren().add(p45);
		p4.getChildren().add(p46);
		p4.getChildren().add(p47);
		p41.getChildren().add(p48);
		p41.getChildren().add(p49);
		p41.getChildren().add(p50);
		p41.getChildren().add(p51);
		p42.getChildren().add(p52);
		p42.getChildren().add(p53);
		p42.getChildren().add(p54);
		p42.getChildren().add(p55);
		p42.getChildren().add(p56);

		p56.setParent(p42);
		p55.setParent(p42);
		p54.setParent(p42);
		p53.setParent(p42);
		p52.setParent(p42);
		p51.setParent(p41);
		p50.setParent(p41);
		p49.setParent(p41);
		p48.setParent(p41);
		p47.setParent(p4);
		p46.setParent(p4);
		p45.setParent(p4);
		p44.setParent(p4);
		p43.setParent(p4);
		p42.setParent(p4);
		p41.setParent(p4);

		permissionService.add(p1);
		permissionService.add(p2);
		permissionService.add(p3);
		permissionService.add(p4);
	}

	@Test
	@Rollback(false)
	public void initUserRoleAndPermissionRelation() throws Exception {
		initPermission();

		User user = new User();
		user.setChineseName("廖瀚卿");
		user.setUsername("test");
		user.setPassword("test");
		user.setSex(Sex.MALE);
		user.setSkin("22");
		user.setState(State.VALID);
		List<Permission> list = permissionService.findEntities();
		Set<Permission> pset = new HashSet<Permission>();
		pset.addAll(list);
		Role role = new Role("管理员", "管理员备注", pset);
		Role role1 = new Role("操作员", "操作员是什么角色呀?男的女d？", pset);
		Role role2 = new Role("服务员", "好好的服务客户的人", pset);
		Role role3 = new Role("网络工程师", "管理员备注", pset);
		Role role4 = new Role("软件工程师", "??码农吧", pset);
		Role role5 = new Role("测试员", "备注", pset);
		user.getRoles().add(role);
		user.getRoles().add(role1);
		user.getRoles().add(role2);
		user.getRoles().add(role3);
		user.getRoles().add(role4);
		user.getRoles().add(role5);
		roleService.add(role);
		roleService.add(role1);
		roleService.add(role2);
		roleService.add(role3);
		roleService.add(role4);
		roleService.add(role5);
		userService.add(user);

		user = new User();
		user.setChineseName("张信哲");
		user.setUsername("a");
		user.setPassword("a");
		user.setSex(Sex.MALE);
		user.setSkin("22");
		user.setState(State.VALID);
		pset.addAll(list);
		user.getRoles().add(role);
		userService.add(user);

		GolfClub gc = new GolfClub();
		gc.setAddress("在天上人间");
		gc.setClubName("朝向高尔夫俱乐部");
		gc.setContact("陈朝行");
		gc.setFax("0755-3837403");
		gc.setIm("3990995");
		gc.setRemark("人间地狱");
		gc.setRoute("深圳深南中路6008号特区报业大厦25楼FGT运动服务有限公司");
		gc.setState(State.VALID);
		gc.setTelephone("0755-33229665");
		gc.setUrl("http://www.fgt.com.cn");
		gc.setZipCode("518000");
		golfClubService.add(gc);

		gc = new GolfClub();
		gc.setAddress("深圳东部华侨城云海谷高尔夫球场");
		gc.setClubName("东部华侨城云海谷高尔夫俱乐部");
		gc.setContact("刘德华");
		gc.setFax("0755-88785458");
		gc.setIm("116121212");
		gc.setRemark("fuc");
		gc.setRoute("610,382");
		gc.setState(State.VALID);
		gc.setTelephone("0755-8888888");
		gc.setUrl("http://www.yhg.com");
		gc.setZipCode("518000");
		golfClubService.add(gc);

		AgencyCompany ag = new AgencyCompany();
		ag.setAddress("深圳特区报社25楼C");
		ag.setCompanyName("华强中介公司");
		ag.setContact("廖瀚卿");
		ag.setEmail("yourgame@163.com");
		ag.setFax("07383322966");
		ag.setIm("3990995");
		ag.setMobile("15989300244");
		ag.setRemark("O(∩_∩)O哈哈~");
		ag.setState(State.VALID);
		ag.setTelephone("07383322966");
		ag.setZipCode("417605");
		agencyCompanyService.add(ag);

		ag = new AgencyCompany();
		ag.setAddress("深圳深南中路腾讯大厦");
		ag.setCompanyName("天上人间中介机构");
		ag.setContact("孙红雷");
		ag.setEmail("example@vip.qq.com");
		ag.setFax("010-5545852");
		ag.setIm("3837403");
		ag.setMobile("13800138000");
		ag.setRemark("(*^__^*) 嘻嘻……");
		ag.setState(State.VALID);
		ag.setTelephone("010-3325986");
		ag.setZipCode("518000");
		agencyCompanyService.add(ag);

		Seller se = new Seller();
		se.setDeptName("会员部");
		se.setEmail("yourgame@163.com");
		se.setIm("3990995");
		se.setJobTitle("特务");
		se.setMobile("13800137000");
		se.setRemark("嘻嘻哈哈");
		se.setSellerName("李强");
		se.setSex(Sex.MALE);
		se.setState(State.VALID);
		sellerService.add(se);

		se = new Seller();
		se.setDeptName("丽人部");
		se.setEmail("yourgame@vip.qq.com");
		se.setIm("116121212");
		se.setJobTitle("模特");
		se.setMobile("13800138000");
		se.setRemark("美眉");
		se.setSellerName("刘丽");
		se.setSex(Sex.FEMALE);
		se.setState(State.VALID);
		sellerService.add(se);

	}

}
