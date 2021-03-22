package com.ss.UtopiaAirlines.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Util {

	private static ResourceBundle reader = null;

	public Util() {
		if (reader == null) {
			reader = ResourceBundle.getBundle("dbconfig");
		}
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException, NullPointerException, MissingResourceException {

		Class.forName(reader.getString("db.driver"));
		Connection conn = DriverManager.getConnection(
				reader.getString("db.url"),
				reader.getString("db.username"),
				reader.getString("db.password")
		);

		conn.setAutoCommit(Boolean.FALSE);

		return conn;
	}
}
