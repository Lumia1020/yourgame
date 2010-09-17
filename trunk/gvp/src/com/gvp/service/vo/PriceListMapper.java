package com.gvp.service.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PriceListMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PriceListVO vo = new PriceListVO();
		vo.setListid(rs.getInt("listid"));
		vo.setPrice(rs.getString("price"));
		vo.setRecordTime(rs.getDate("recordTime"));
		vo.setRemark(rs.getString("remark"));
		vo.setSpeciesName(rs.getString("speciesName"));
		vo.setSpecName(rs.getString("specName"));
		vo.setStuffName(rs.getString("stuffName"));
		return vo;
	}

}
