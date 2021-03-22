package com.ss.UtopiaAirlines.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;

public abstract class BaseDAO<T> {
	protected Connection conn = null;

	public BaseDAO(Connection conn) {
		this.conn = conn;
	}

	public void save(String sql, Object[] values) throws ClassNotFoundException, SQLException {
		PreparedStatement preStatement = conn.prepareStatement(sql);

		int count = 1;
		for (Object obj : values) {
			preStatement.setObject(count, obj);
			count++;
		}

		preStatement.executeUpdate();
	}

	public Integer save(String sql, Object[] values, boolean returnPK) throws ClassNotFoundException, SQLException {
		PreparedStatement preStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		int count = 1;
		for (Object obj : values) {
			preStatement.setObject(count, obj);
			count++;
		}

		int affectedRows = preStatement.executeUpdate();
		if (affectedRows > 0) {
			ResultSet keys = preStatement.getGeneratedKeys();
			if (keys.next()) {
				int pk = keys.getInt(1);
				return Math.max(pk, 0);
			}
		}

		return 0;
	}

	public List<T> read(String sql, Object[] values) throws ClassNotFoundException, SQLException {
		PreparedStatement preStatement = conn.prepareStatement(sql);

		int count = 1;
		for (Object obj : values) {
			preStatement.setObject(count, obj);
			count++;
		}

		ResultSet resultSet = preStatement.executeQuery();
		return extractData(resultSet);
	}

	abstract public List<T> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException;
}
