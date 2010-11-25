package com.tiema.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.tiema.core.constant.Sex;
import com.tiema.core.constant.State;

/**
 * @ClassName: UserView
 * @Description: 用户类对用数据库的一个视图,此视图主要查询一个用户以及用户拥有的所有角色名称,视图语句如下:
 * 
 * <pre>
 * CREATE OR replace VIEW VIEW_USER
 * AS
 *  SELECT tu.*,
 *         (SELECT wmsys.Wm_concat(r. id)
 *          FROM   t_role r,
 *                t_user u,
 *                 t_user_role_relation trr
 *          WHERE  r. id = trr.role_id
 *                 AND u . id = trr.user_id
 *                 AND u . id = tu. id
 *          GROUP  BY 1)AS roleids,
 *         (SELECT wmsys.Wm_concat(r.rolename)
 *          FROM   t_role r,
 *                 t_user u,
 *                 t_user_role_relation trr
 *          WHERE  r. id = trr.role_id
 *                 AND u . id = trr.user_id
 *                 AND u . id = tu. id
 *          GROUP  BY 1)AS rolenames
 *  FROM   t_user tu;  
 * </pre>
 * 
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 11, 2010 6:39:51 PM
 * 
 */
@Entity
@Table(name = "VIEW_USER")
public class UserView implements Serializable {

	private static final long serialVersionUID = -2124614882979521702L;

	/** @Fields id : 用户id */
	private Long id;

	/** @Fields username : 用户名 */
	private String username;

	/** @Fields password : 密码 */
	private String password;

	/** @Fields chineseName : 姓名 */
	private String chineseName;

	/** @Fields sex : 性别 */
	private Sex sex;

	/** @Fields jobTitle : 职称 */
	private String jobTitle;

	/** @Fields mobile : 移动电话 */
	private String mobile;

	/** @Fields homeAddress : 家庭住址 */
	private String homeAddress;

	/** @Fields zipCode : 邮政编码 */
	private String zipCode;

	/** @Fields birthday : 生日 */
	private Date birthday;

	/** @Fields email : 邮件 */
	private String email;

	/** @Fields emergencyContacts : 紧急情况联系人,该用户出现了紧急情况需要联系的人 */
	private String emergencyContacts;

	/** @Fields emergencyPhone : 紧急情况联系电话 */
	private String emergencyPhone;

	/** @Fields entryDate : 入职日期 */
	private Date entryDate;

	/** @Fields state : 状态:是否有效,有效就可以登录系统,否则不能,1有效,2无效 */
	private State state;

	/** @Fields State : 和用户关联的界面皮肤ID */
	private String skin;

	/** @Fields remark : 备注 */
	private String remark;

	/** @Fields roleIds : 角色id集合 */
	private String roleIds;

	/** @Fields roleNames : 角色名称集合 */
	private String roleNames;

	@Temporal(TemporalType.DATE)
	@JSON(format = "yyyy-MM-dd")
	@Column(insertable = false, updatable = false)
	public Date getBirthday() {
		return birthday;
	}

	@Column(insertable = false, updatable = false)
	public String getChineseName() {
		return chineseName;
	}

	@Column(insertable = false, updatable = false)
	public String getEmail() {
		return email;
	}

	@Column(insertable = false, updatable = false)
	public String getEmergencyContacts() {
		return emergencyContacts;
	}

	@Column(insertable = false, updatable = false)
	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format = "yyyy-MM-dd")
	@Column(insertable = false, updatable = false)
	public Date getEntryDate() {
		return entryDate;
	}

	@Column(insertable = false, updatable = false)
	public String getHomeAddress() {
		return homeAddress;
	}

	@Id
	@GeneratedValue(generator = "USER_VIEW_ASSIGNED")
	@GenericGenerator(name = "USER_VIEW_ASSIGNED", strategy = "assigned")
	@Column(insertable = false, updatable = false)
	public Long getId() {
		return id;
	}

	@Column(insertable = false, updatable = false)
	public String getJobTitle() {
		return jobTitle;
	}

	@Column(insertable = false, updatable = false)
	public String getMobile() {
		return mobile;
	}

	@Column(insertable = false, updatable = false)
	public String getPassword() {
		return password;
	}

	@Column(insertable = false, updatable = false)
	public String getRemark() {
		return remark;
	}

	@Column(insertable = false, updatable = false)
	public String getRoleIds() {
		return roleIds;
	}

	@Column(insertable = false, updatable = false)
	public String getRoleNames() {
		return roleNames;
	}

	@Column(insertable = false, updatable = false)
	@Enumerated
	public Sex getSex() {
		return sex;
	}

	@Column(insertable = false, updatable = false)
	@Enumerated
	public State getState() {
		return state;
	}

	@Column(insertable = false, updatable = false)
	public String getUsername() {
		return username;
	}

	@Column(insertable = false, updatable = false)
	public String getZipCode() {
		return zipCode;
	}

	@Column(insertable = false, updatable = false)
	public String getSkin() {
		return skin;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmergencyContacts(String emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

}
