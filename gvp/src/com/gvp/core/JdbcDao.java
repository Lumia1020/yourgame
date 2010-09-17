package com.gvp.core;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcDao extends JdbcDaoSupport implements IJdbcDao {
	private static final Log log = LogFactory.getLog(JdbcDao.class);

	public List<?> executeQueryList(String strSQL, Object[] params, RowMapper rowMapper) {
		List<?> result = null;
		try {
			if ((params == null) || (params.length < 1)) {
				result = getJdbcTemplate().query(strSQL, rowMapper);
			} else {
				result = getJdbcTemplate().query(strSQL, params, rowMapper);
			}
			if (result == null) {
				result = new ArrayList();
			}
		} catch (Exception e) {
			log.error("执行sql失败" + strSQL, e);
		}
		return result;
	}

	public List<?> executeQueryList(String strSQL, Object[] params, Class<?> elementType) {
		List<?> result = null;
		try {
			if ((params == null) || (params.length < 1)) {
				result = getJdbcTemplate().queryForList(strSQL, elementType);
			} else {
				result = getJdbcTemplate().queryForList(strSQL, params, elementType);
			}
			if (result == null) {
				result = new ArrayList();
			}
		} catch (Exception e) {
			log.error("执行sql失败" + strSQL, e);
		}
		return result;
	}

	public List<?> executeQueryList(String strSQL, Object[] params) {
		List<?> result = new ArrayList();
		try {
			if ((params == null) || (params.length < 1)) {
				result = getJdbcTemplate().queryForList(strSQL);
			} else {
				result = getJdbcTemplate().queryForList(strSQL, params);
			}
		} catch (Exception e) {
			log.error("执行sql失败" + strSQL, e);
		}
		return result;
	}

	public List<?> executeQueryList(String strSQL, Object[] params, int[] argTypes) {
		List<?> result = null;
		try {
			if ((params == null) || (params.length < 1)) {
				result = getJdbcTemplate().queryForList(strSQL);
			} else {
				if (argTypes != null && argTypes.length > 0) {
					result = getJdbcTemplate().queryForList(strSQL, params, argTypes);
				} else {
					result = getJdbcTemplate().queryForList(strSQL, params);
				}
			}
		} catch (Exception e) {
			log.error("执行sql失败" + strSQL, e);
		}
		return result;
	}

	public List<?> executeQueryList(String strSQL) {
		List<?> result = null;
		try {
			result = getJdbcTemplate().queryForList(strSQL);
			if (result == null) {
				result = new ArrayList();
			}
		} catch (Exception e) {
			log.error("查询sql失败" + strSQL, e);
		}
		return result;
	}

	public int queryForInt(String sql, Object[] args) {
		try {
			return getJdbcTemplate().queryForInt(sql, args);
		} catch (DataAccessException e) {
			this.logger.error("查询 " + sql + ":" + sql + ":" + args + " 失败", e);
		}
		return 0;
	}

	public int execute(String strSQL, Object[] objects) {
		int i = -1;
		try {
			if ((objects == null) || (objects.length == 0))
				i = getJdbcTemplate().update(strSQL);
			else {
				i = getJdbcTemplate().update(strSQL, objects);
			}
			return i;
		} catch (Exception e) {
			log.error("更新sql失败" + strSQL, e);
		}
		return i;
	}

	/**
	 * @param sql -
	 *            SQL query to execute
	 * @param pageNo -
	 *            开始记录
	 * @param pageSize -
	 *            每页多少条
	 * @param args -
	 *            arguments to bind to the query
	 * @param argTypes -
	 *            SQL types of the arguments (constants from java.sql.Types)
	 * @return
	 */
	// public Page findPage(String sql, int pageNo, int pageSize, Object[] args,
	// int[] argTypes) {
	// int _pageSize = (pageSize == 0 ? Page.PAGESIZE : pageSize);
	// int _pageNo = (pageNo * pageSize - pageSize);
	//
	// Page p = new Page();
	// if (StringUtils.isNotEmpty(sql)) {
	// return p;
	// }
	// // 求总数
	// StringBuffer totalSql = new StringBuffer(" SELECT count(*) FROM ( ");
	// totalSql.append(sql);
	// totalSql.append(" ) totalTable ");
	// p.setRecordSize(getJdbcTemplate().queryForInt(totalSql.toString(), args,
	// argTypes));
	//
	// // 求分页集合
	// StringBuffer resultSql = new StringBuffer(" SELECT * FROM ( ");
	// resultSql.append(" SELECT temp.* ,ROWNUM num FROM ( ");
	// resultSql.append(sql);
	// resultSql.append(" ) temp where ROWNUM <= " + _pageSize);
	// resultSql.append(" ) WHERE num > " + _pageNo);
	// p.setResults(getJdbcTemplate().queryForList(resultSql.toString(), args,
	// argTypes));
	// return p;
	// }
	/**
	 * @param sql -
	 *            SQL query to execute
	 * @param pageNo -
	 *            开始记录
	 * @param pageSize -
	 *            每页多少条
	 * @param args -
	 *            arguments to bind to the query
	 * @return
	 */
	// public Page findPage(String sql, int pageNo, int pageSize, Object[] args)
	// {
	// int _pageSize = (pageSize == 0 ? Page.PAGESIZE : pageSize * pageNo);
	// int _pageNo = (pageNo * pageSize - pageSize);
	//
	// Page p = new Page(null, pageNo, pageSize, 0);
	// if (StringUtils.isEmpty(sql)) {
	// return p;
	// }
	// // 求总数
	// StringBuffer totalSql = new StringBuffer(" SELECT count(*) FROM ( ");
	// totalSql.append(sql);
	// totalSql.append(" ) totalTable ");
	// p.setRecordSize(getJdbcTemplate().queryForInt(totalSql.toString(),
	// args));
	//
	// // 求分页集合
	// StringBuffer resultSql = new StringBuffer(" SELECT * FROM ( ");
	// resultSql.append(" SELECT temp.* ,ROWNUM num FROM ( ");
	// resultSql.append(sql);
	// resultSql.append(" ) temp where ROWNUM <= " + _pageSize);
	// resultSql.append(" ) WHERE num > " + _pageNo);
	// p.setResults(getJdbcTemplate().queryForList(resultSql.toString(), args));
	// return p;
	// }
	// public Page findPage(String sql, int pageNo, int pageSize, Object[] args,
	// RowMapper rowMapper) {
	// int _pageSize = (pageSize == 0 ? Page.PAGESIZE : pageSize * pageNo);
	// int _pageNo = (pageNo * pageSize - pageSize);
	//
	// Page p = new Page(null, pageNo, pageSize, 0);
	// if (StringUtils.isEmpty(sql)) {
	// return p;
	// }
	// // 求总数
	// StringBuffer totalSql = new StringBuffer(" SELECT count(*) FROM ( ");
	// totalSql.append(sql);
	// totalSql.append(" ) totalTable ");
	// p.setRecordSize(getJdbcTemplate().queryForInt(totalSql.toString(),
	// args));
	//
	// // 求分页集合
	// StringBuffer resultSql = new StringBuffer(" SELECT * FROM ( ");
	// resultSql.append(" SELECT temp.* ,ROWNUM num FROM ( ");
	// resultSql.append(sql);
	// resultSql.append(" ) temp where ROWNUM <= " + _pageSize);
	// resultSql.append(" ) WHERE num > " + _pageNo);
	// p.setResults(getJdbcTemplate().query(resultSql.toString(), args,
	// rowMapper));
	// return p;
	// }
	/**
	 * 调用存储过程
	 */
	public CallableStatement findProcedure(String sql) {
		CallableStatement cs = null;
		try {
			cs = this.getConnection().prepareCall(sql);
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cs;
	}

}
