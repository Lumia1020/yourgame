package com.gvp.core;

import java.sql.CallableStatement;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public interface IJdbcDao {

	public List executeQueryList(String strSQL, Object[] params);

	public List executeQueryList(String strSQL);

	public int queryForInt(String sql, Object[] args);

	public int execute(String strSQL, Object[] objects);

	// public Page findPage(String sql, int pageNo, int pageSize, Object[] args, int[] argTypes);

	// public Page findPage(String sql, int pageNo, int pageSize, Object[] args);

	List<?> executeQueryList(String strSQL, Object[] params, int[] argTypes);

	List<?> executeQueryList(String strSQL, Object[] params, Class<?> elementType);

	List<?> executeQueryList(String strSQL, Object[] params, RowMapper rowMapper);

	// public Page findPage(String sql, int pageNo, int pageSize, Object[] args, RowMapper rowMapper);

	public CallableStatement findProcedure(String sql);
}
