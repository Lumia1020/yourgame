package com.gvp.service.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gvp.po.QuoteInfo;

public class QuoteInfoMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		QuoteInfo q = new QuoteInfo();
		q.setCid(rs.getInt("cid"));
		q.setCustomerName(rs.getString("customerName"));
		q.setCustomerType(rs.getString("customerType"));
		q.setDccNo(rs.getString("dccNo"));
		q.setModifyTime(rs.getDate("modifyTime"));
		q.setOwnerId(rs.getInt("ownerId"));
		q.setPageNo(rs.getString("pageNo"));
		q.setPrice(rs.getString("price"));
		q.setProductCode(rs.getString("productCode"));
		q.setQid(rs.getInt("qid"));
		q.setQuoter(rs.getString("quoter"));
		q.setRecordTime(rs.getDate("recordTime"));
		q.setState(rs.getString("state"));
		return q;
	}

}
