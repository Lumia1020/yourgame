package com.tiema.role.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tiema.permission.entity.Permission;

/**
 * @ClassName: Role
 * @Description: 角色实体
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 1, 2010 10:49:08 AM
 * 
 */
@Entity
@Table(name = "T_ROLE")
public class Role implements Serializable {

	private static final long	serialVersionUID	= 2499370466638038681L;

	/** @Fields id : 角色id */
	private Long				id;

	/** @Fields roleName : 角色名 */
	private String				roleName;

	/** @Fields remark : 角色说明 */
	private String				remark;

	/** @Fields permissions : 角色拥有的权限集合 */
	private Set<Permission>		permissions			= new HashSet<Permission>();

	public Role() {
	}

	public Role(Long id, String roleName, String remark) {
		this.id = id;
		this.roleName = roleName;
		this.remark = remark;
	}

	public Role(String roleName, String remark, Set<Permission> permissions) {
		this.roleName = roleName;
		this.remark = remark;
		this.permissions = permissions;
	}

	public Role(Long roleId, String roleName) {
		this.id = roleId;
		this.roleName = roleName;
	}

	@Id
	@SequenceGenerator(name = "ROLE_SEQ_GEN", sequenceName = "T_ROLE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ_GEN")
	public Long getId() {
		return id;
	}

	@ManyToMany
	@JoinTable(name = "T_ROLE_PERMISSION_RELATION", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERMISSION_ID") })
	public Set<Permission> getPermissions() {
		return permissions;
	}

	@Column(length = 400)
	public String getRemark() {
		return remark;
	}

	@Column(length = 50, unique = true, nullable = false)
	public String getRoleName() {
		return roleName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
