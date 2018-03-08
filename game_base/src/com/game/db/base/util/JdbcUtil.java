package com.game.db.base.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.db.base.Operate;

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JdbcUtil {

	private static final int BatchSize = 500;
	
	 private static final Logger LOG =LogManager.getLogger(JdbcUtil.class);
	
	private static final JdbcUtil instance = new JdbcUtil();

	public static JdbcUtil getInstance() {
		return instance;
	}

	public boolean insert(Operate op) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {

			conn = getConn(op);
			ps = conn.prepareStatement(op.getSql(), Statement.RETURN_GENERATED_KEYS);
			op.fillParam(ps);
			boolean flag = ps.executeUpdate() > 0;
			if (flag) {
				rs = ps.getGeneratedKeys();
				if (rs != null && rs.next()) {
					int generatedKey = rs.getInt(1);
					op.generatedKey(generatedKey);
				}
			}
			return flag;
		} finally {
			closeRSC(rs, ps, conn);
		}
	}

	public Object queryUnique(Operate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		Object v = null;
		try {

			conn = getConn(op);
			if (conn == null) {
				throw new IllegalStateException("No SQL connection found for op: " + op.toString());
			}
			ps = conn.prepareStatement(op.getSql());
			op.fillParam(ps);
			rs = ps.executeQuery();
			if (rs.next()) {
				v = op.parseResult(rs);
			}
			return v;
		} finally {
			closeRSC(rs, ps, conn);

		}
	}

	public List queryList(Operate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = getConn(op);
			if (conn == null) {
				throw new IllegalStateException("No SQL connection found for op: " + op.toString());
			}
			ps = conn.prepareStatement(op.getSql());
			op.fillParam(ps);
			rs = ps.executeQuery();
			List result = new ArrayList();
			while (rs.next())
				result.add(op.parseResult(rs));
			return result;
		} finally {
			closeRSC(rs, ps, conn);
		}
	}

	public int update(Operate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {

			conn = getConn(op);
			if (conn == null) {
				throw new IllegalStateException("No SQL connection found for op: " + op.toString());
			}
			ps = conn.prepareStatement(op.getSql());
			op.fillParam(ps);

			return ps.executeUpdate();
		} catch (Exception e) {
			throw new SQLException(this.sqlLog(ps), e);
		} finally {
			closeRSC(rs, ps, conn);
		}
	}

	public int[] batchUpdate(Operate op, List params) throws SQLException {
		PreparedStatement ps = null;
		Connection conn = null;
		int[] executeResults = {};
		try {

			conn = getConn(op);
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(op.getSql());
			for (int i = 0; i < params.size(); i++) {
				Object param = params.get(i);
				op.fillBatchParam(ps, param);
				ps.addBatch();

				if ((i + 1) % BatchSize == 0) {
					int[] results = ps.executeBatch();
					executeResults = ArrayUtils.addAll(executeResults, results);
					ps.clearBatch();

				}
			}
			if ((params.size() + 1) % BatchSize != 0) {
				int[] results = ps.executeBatch();
				executeResults = ArrayUtils.addAll(executeResults, results);
			}

		} catch (Exception e) {
			conn.rollback();
			throw new SQLException(e);
		} finally {
			closeRSC(ps, conn);

		}
		return executeResults;
	}

	private Connection getConn(Operate op) throws SQLException {
		return op.getDataSource().getConnection();
	}

	public long getQueryId(final Operate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long result = 0;

		try {

			conn = getConn(op);
			ps = conn.prepareStatement(op.getSql());
			op.fillParam(ps);
			rs = ps.executeQuery();
			if (rs.next())
				result = rs.getLong(1);
			return result;
		} finally {
			closeRSC(rs, ps, conn);

		}
	}

	public boolean queryExist(final Operate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try {

			conn = getConn(op);
			if (conn == null) {
				throw new IllegalStateException("No SQL connection found for op: " + op.toString());
			}
			ps = conn.prepareStatement(op.getSql());
			op.fillParam(ps);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
			return false;
		} finally {
			closeRSC(rs, ps, conn);

		}
	}

	public int queryId(final Operate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int result = 0;

		try {

			conn = getConn(op);
			if (conn == null) {
				throw new IllegalStateException("No SQL connection found for op: " + op.toString());
			}
			ps = conn.prepareStatement(op.getSql());
			op.fillParam(ps);
			rs = ps.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
			return result;
		} finally {
			closeRSC(rs, ps, conn);

		}
	}

	public int queryInt(final Operate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int result = 0;

		try {
			// 记录sql执行的开始时间

			// 从不同的数据源获得connection
			conn = getConn(op);
			if (conn == null) {
				throw new IllegalStateException("No SQL connection found for op: " + op.toString());
			}
			ps = conn.prepareStatement(op.getSql());

			op.fillParam(ps);
			// op.log(logger);
			rs = ps.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
			else
				return 0;
			return result;
		} finally {
			closeRSC(rs, ps, conn);

		}
	}

	public long queryLong(final Operate op) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long result = 0;

		try {

			conn = getConn(op);
			ps = conn.prepareStatement(op.getSql());

			op.fillParam(ps);
			rs = ps.executeQuery();
			if (rs.next())
				result = rs.getLong(1);
			else
				return 0;
			return result;
		} finally {
			closeRSC(rs, ps, conn);
		}
	}

	private String sqlLog(PreparedStatement ps) {
		if (LOG.isDebugEnabled()) {
			return ps.toString();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public void closeRSC(final ResultSet rs, final PreparedStatement st,Connection conn) {
		if(null!=rs){
			try {
				rs.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		
		closeRSC(st, conn);
		  
	}
 
	public void closeRSC(final PreparedStatement st, Connection conn) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
			}

		}
		
		if(null!=conn){
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
			}
		}

	}

	public int batchInsert(Operate op, List params) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int rowCount = 0;
		try {

			conn = getConn(op);
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(op.getSql());
			for (int i = 0; i < params.size(); i++) {
				Object param = params.get(i);
				op.fillBatchParam(ps, param);
				ps.addBatch();
				if ((i + 1) % BatchSize == 0) {
					int[] results = ps.executeBatch();
					rowCount += getCount(results);
					ps.clearBatch();

				}
			}
			if (params.size() % BatchSize != 0) {
				int[] results = ps.executeBatch();
				rowCount += getCount(results);
			}
		} catch (Exception e) {
			conn.rollback();
			throw new SQLException(e);
		} finally {
			closeRSC(rs, ps, conn);
		}
		return rowCount;
	}

	private int getCount(int[] results) {
		int count = 0;
		if (!ArrayUtils.isEmpty(results)) {
			for (int value : results) {
				count = count + value;
			}
		}
		return count;
	}
}
