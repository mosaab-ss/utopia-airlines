package com.ss.UtopiaAirlines.service;

import com.ss.UtopiaAirlines.dao.AirplaneTypeDAO;
import com.ss.UtopiaAirlines.entity.AirplaneType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface SeatsCRUD {

	Util util = new Util();

	default String addSeats(AirplaneType airplaneType) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(conn);

			airplaneTypeDAO.addAirplaneType(airplaneType);

			conn.commit();

			return "Seats Added!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Seats could not be added.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default List<AirplaneType> readSeats(int offset) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(conn);

		List<AirplaneType> airplaneTypes = airplaneTypeDAO.readAllAirplaneTypes(offset, 10);

		if (conn != null) {
			conn.close();
		}

		return airplaneTypes;
	}

	default AirplaneType readSeatsById(int id) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(conn);

		AirplaneType at = new AirplaneType();
		at.setId(id);

		List<AirplaneType> airplaneTypes = airplaneTypeDAO.readAirplaneTypesById(at);

		if (conn != null) {
			conn.close();
		}

		return (airplaneTypes.isEmpty())? null : airplaneTypes.get(0);
	}

	default String updateSeats(AirplaneType airplaneType) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(conn);

			airplaneTypeDAO.updateAirplaneType(airplaneType);

			conn.commit();

			return "Seats updated!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Seats could not be updated.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default String deleteSeats(int id) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(conn);

			AirplaneType at = new AirplaneType();
			at.setId(id);

			airplaneTypeDAO.deleteAirplaneType(at);

			conn.commit();

			return "Seats deleted!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Seats could not be deleted.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
