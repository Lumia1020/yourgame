package com.gvp.po;

import java.io.Serializable;

/**
 * 
 * @ClassName: ProductCode
 * @Description: 产品编码实体
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 20, 2010 11:09:44 AM
 * 
 */
@SuppressWarnings("serial")
public class ProductCode implements Serializable {

	private Integer	pcid;

	/** 编码 */
	private String	code;

	private String	remark;

	/** 关联报时表 */
	private Integer	qid;

	public Integer getPcid() {
		return pcid;
	}

	public String getCode() {
		return code;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getQid() {
		return qid;
	}

	public void setPcid(Integer pcid) {
		this.pcid = pcid;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

}
