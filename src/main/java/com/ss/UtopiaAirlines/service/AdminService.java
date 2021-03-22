package com.ss.UtopiaAirlines.service;

import com.ss.UtopiaAirlines.dao.AirportDAO;
import com.ss.UtopiaAirlines.dao.RouteDAO;
import com.ss.UtopiaAirlines.dao.UserDAO;
import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminService {

	Util util = new Util();

	public User getUser(String username, String password) throws ClassNotFoundException, SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			UserDAO userDao = new UserDAO(conn);

			User user = userDao.readUserByUP(username, password).get(0);

			conn.commit();
			return user;
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
			return null;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
