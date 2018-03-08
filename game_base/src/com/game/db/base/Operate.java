package com.game.db.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
 

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 * @param <V>
 */
public class Operate<V> {
	private String sql;

	private EntityType<V> entityType;

	private DataSource dataSource;
 
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Operate(String sql, DataSource dataSource) {
		this.sql = sql;
		this.dataSource = dataSource;
	}

	public Operate(String sql, DataSource dataSource, EntityType<V> entityType) {
		this.sql = sql;
		this.dataSource = dataSource;
		this.entityType = entityType;
	}

	public void fillParam(PreparedStatement ps) throws SQLException {

	}

	public void fillBatchParam(PreparedStatement ps, V paramObject) throws SQLException {

	}

	public V parseResult(ResultSet rs) throws SQLException {
		if (entityType != null) {
			return entityType.parseResultSet(rs);
		}
		return null;
	}

	public void generatedKey(int key) {

	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
