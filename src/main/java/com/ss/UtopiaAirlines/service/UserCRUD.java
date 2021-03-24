package com.ss.UtopiaAirlines.service;

import com.ss.UtopiaAirlines.dao.AirplaneTypeDAO;
import com.ss.UtopiaAirlines.dao.UserDAO;
import com.ss.UtopiaAirlines.entity.AirplaneType;
import com.ss.UtopiaAirlines.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserCRUD {

	Util util = new Util();

	default String addUser(User user) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			UserDAO userDAO = new UserDAO(conn);

			userDAO.addUser(user);

			conn.commit();

			return "User Added!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "User could not be added.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default List<User> readUsers(int offset) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		UserDAO userDAO = new UserDAO(conn);

		List<User> airplaneTypes = userDAO.readAllUser(offset, 10);

		if (conn != null) {
			conn.close();
		}

		return airplaneTypes;
	}

	default List<User> readUsersByRole(int offset, int roleId) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		UserDAO userDAO = new UserDAO(conn);

		List<User> airplaneTypes = userDAO.readAllUser(offset, 10, roleId);

		if (conn != null) {
			conn.close();
		}

		return airplaneTypes;
	}

	default User readUserById(int id) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		UserDAO userDAO = new UserDAO(conn);

		User u = new User();
		u.setId(id);

		User user = userDAO.readUserById(u).get(0);

		if (conn != null) {
			conn.close();
		}

		return user;
	}

	default User readUserByUP(String username, String password) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		UserDAO userDao = new UserDAO(conn);

		List<User> users = userDao.readUserByUP(username, password);

		if (conn != null) {
			conn.close();
		}

		return (users.isEmpty())? null : users.get(0);
	}

	default String updateUser(User user) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			UserDAO userDAO = new UserDAO(conn);

			userDAO.updateUser(user);

			conn.commit();

			return "User updated!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "User could not be updated.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default String deleteUser(int id) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			UserDAO userDAO = new UserDAO(conn);

			User u = new User();
			u.setId(id);

			userDAO.deleteUser(u);

			conn.commit();

			return "User deleted!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "User could not be deleted.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
