package com.tiema.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @ClassName: User
 * @Description: 用户实体
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Oct 25, 2010 10:59:33 PM
 */
@Entity
@Table(
        name="t_user"/*, 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"id", "username"})*/
    )
public class User implements Serializable {

	private static final long serialVersionUID = 1351308445276915495L;

	/** @Fields id : 用户id */
	private Long id;

	/** @Fields username : 用户名 */
	private String username;

	/** @Fields password : 密码 */
	private String password;

	/** @Fields chineseName : 姓名 */
	private String chineseName;

	/** @Fields jobTitle : 职称 */
	private String jobTitle;

	/** @Fields mobile : 移动电话 */
	private String mobile;

	public User() {
	}

	private String remark;

	@Id
	@SequenceGenerator(name = "USER_SEQ_GEN", sequenceName = "t_user_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GEN")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, length = 50,unique=true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = false, length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 50)
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

}
