package com.tiema.permission.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @ClassName: Permission
 * @Description: 权限实体
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 1, 2010 4:27:59 PM
 * 
 */
@Entity
@Table(name = "T_PERMISSION")
public class Permission implements Serializable {

	private static final long	serialVersionUID	= 4832104101699686431L;

	/** @Fields id : 权限id */
	private Long				id;
	
	/** @Fields name : 权限名称 */
	private String				name;
	
	/** @Fields resource : 权限对应的资源 */
	private String				resources;
	
	/** @Fields parent : 权限父节点 */
	private Permission			parent;
	
	/** @Fields children : 权限子节点 */
	private Set<Permission>		children			= new HashSet<Permission>();

	public Permission() {
	}

	public Permission(Long id) {
		this.id = id;
	}

	public Permission(String name) {
		super();
		this.name = name;
	}

	public Permission(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Permission(String name, String resources, Permission parent) {
		super();
		this.name = name;
		this.resources = resources;
		this.parent = parent;
	}

	public Permission(Long id, String name, String resources) {
		super();
		this.id = id;
		this.name = name;
		this.resources = resources;
	}

	public Permission(String name, String resources, Permission parent, Set<Permission> children) {
		super();
		this.name = name;
		this.resources = resources;
		this.parent = parent;
		this.children = children;
	}

	@Id
	@SequenceGenerator(name = "PERMISSION_SEQ_GEN", sequenceName = "T_PERMISSION_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION_SEQ_GEN")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	public Permission getParent() {
		return parent;
	}

	public void setParent(Permission parent) {
		this.parent = parent;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	public Set<Permission> getChildren() {
		return children;
	}

	public void setChildren(Set<Permission> children) {
		this.children = children;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

}
