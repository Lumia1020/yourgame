package com.gvp.core;


/**
 * File.java Create on 2008-12-17 ����04:58:29
 * 
 * ˵��: 文件信息类�ļ���
 * 
 * Copyright (c) 2008 by MTA.
 * 
 * @author �����
 * @version 1.0
 */
public class MyFile {

	/** 节点id */
	private String id;

	/** 节点名称 */
	private String name;

	/** 是否是叶子节点 */
	private boolean leaf;

	/** 文件名 */
	private String url;

	/** 文件尺寸 */
	private Integer size;

	/** 文件最后修改时间 */
	private Long lastmod;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getLastmod() {
		return lastmod;
	}

	public void setLastmod(Long lastmod) {
		this.lastmod = lastmod;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
