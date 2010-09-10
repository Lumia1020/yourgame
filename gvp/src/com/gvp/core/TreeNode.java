/*
 * @(#)TreeNode.java 2009-9-25
 *
 * Copyright YOURGAME. All rights reserved.
 */

package com.gvp.core;

import java.io.Serializable;

/**
 * Create on 2009-9-25 上午10:24:35
 * 
 * 树节点对象
 * 
 * @author 廖瀚卿
 * @version
 */
@SuppressWarnings("serial")
public class TreeNode implements Serializable {

	private String id;

	private String text;

	private boolean leaf;

	private String field;
	
	private String method;
	
	private String qtip;
	
	private String iconCls;
	
	private boolean singleClickExpand;
	
	public TreeNode(){
		this.singleClickExpand = true;
	}

	public boolean isSingleClickExpand() {
		return singleClickExpand;
	}

	public void setSingleClickExpand(boolean singleClickExpand) {
		this.singleClickExpand = singleClickExpand;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String value) {
		this.method = value;
	}
}
