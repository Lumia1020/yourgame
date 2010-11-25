package com.tiema.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.util.CreateIfNull;
import com.opensymphony.xwork2.util.KeyProperty;
import com.tiema.core.constant.Sex;
import com.tiema.core.constant.State;
import com.tiema.role.entity.Role;

/**
 * @ClassName: User
 * @Description: 用户实体
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 25, 2010 10:59:33 PM
 */
@Entity
@Table(name = "T_USER")
public class User implements Serializable {

	private static final long	serialVersionUID	= 1351308445276915495L;

	/** @Fields id : 用户id */
	private Long				id;

	/** @Fields username : 用户名 */
	private String				username;

	/** @Fields password : 密码 */
	private String				password;

	/** @Fields chineseName : 姓名 */
	private String				chineseName;

	/** @Fields sex : 性别 */
	private Sex					sex;

	/** @Fields jobTitle : 职称 */
	private String				jobTitle;

	/** @Fields mobile : 移动电话 */
	private String				mobile;

	/** @Fields homeAddress : 家庭住址 */
	private String				homeAddress;

	/** @Fields zipCode : 邮政编码 */
	private String				zipCode;

	/** @Fields birthday : 生日 */
	private Date				birthday;

	/** @Fields email : 邮件 */
	private String				email;

	/** @Fields emergencyContacts : 紧急情况联系人,该用户出现了紧急情况需要联系的人 */
	private String				emergencyContacts;

	/** @Fields emergencyPhone : 紧急情况联系电话 */
	private String				emergencyPhone;

	/** @Fields entryDate : 入职日期 */
	private Date				entryDate;

	/** @Fields state : 状态:是否有效,有效就可以登录系统,否则不能,1有效,2无效 */
	private State				state;

	/** @Fields State : 和用户关联的界面皮肤ID */
	private String				skin;

	/** @Fields remark : 备注 */
	private String				remark;

	/** @Fields roles : 用户拥有的角色集合 */
	@KeyProperty
	@CreateIfNull
	private Set<Role>			roles				= new HashSet<Role>();

	public User() {
	}

	public User(Long userId, String userName) {
		this.id = userId;
		this.username = userName;
	}

	@Id
	@SequenceGenerator(name = "USER_SEQ_GEN", sequenceName = "T_USER_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GEN")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 50, nullable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(length = 50, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 50, nullable = false)
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	@Column(length = 50)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(length = 50)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(length = 400)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToMany
	@JoinTable(name = "T_USER_ROLE_RELATION", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Column(length = 150)
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format = "yyyy-MM-dd")
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Column(length = 200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 50)
	public String getEmergencyContacts() {
		return emergencyContacts;
	}

	public void setEmergencyContacts(String emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

	@Column(length = 50)
	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	@Column(nullable = false)
	@Enumerated
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Column(length = 6)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Enumerated
	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@Column(length = 20)
	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

}
