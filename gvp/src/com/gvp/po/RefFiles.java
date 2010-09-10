package com.gvp.po;

import java.io.Serializable;

/**
 * 参考资料子表(图纸PDF等)
 * 
 * @author yourgame
 * 
 */
@SuppressWarnings("serial")
public class RefFiles implements Serializable {

	private Integer fid;

	/** 路径 */
	private String url;

	/** 参考资料说明(可以根据这个字段的内容模糊查询到相关联的资料) */
	private String remark;

	/** 所属报时资料的id */
	private Integer qid;


	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer rid) {
		this.qid = rid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
