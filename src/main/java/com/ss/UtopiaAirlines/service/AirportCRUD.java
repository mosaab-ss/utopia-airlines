package com.ss.UtopiaAirlines.service;

import com.ss.UtopiaAirlines.dao.AirportDAO;
import com.ss.UtopiaAirlines.entity.Airport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AirportCRUD {

	Util util = new Util();

	default String addAirport(Airport airport) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			AirportDAO airportDAO = new AirportDAO(conn);

			airportDAO.addAirport(airport);

			conn.commit();

			return "Airport Added!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Airport could not be added.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default List<Airport> readAirports(int offset) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		AirportDAO airportDAO = new AirportDAO(conn);

		List<Airport> airports = airportDAO.readAllAirports(offset, 10);

		if (conn != null) {
			conn.close();
		}

		return airports;
	}

	default Airport readAirportById(String iata_id) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		AirportDAO airportDAO = new AirportDAO(conn);

		Airport a = new Airport();
		a.setIataId(iata_id);

		List<Airport> airports = airportDAO.readAirportsByCode(a);

		if (conn != null) {
			conn.close();
		}

		return (airports.isEmpty()) ? null : airports.get(0);
	}

	default String updateAirport(Airport airport) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			AirportDAO airportDAO = new AirportDAO(conn);

			airportDAO.updateAirport(airport);

			conn.commit();

			return "Airport updated!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Airport could not be updated.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default String deleteAirport(String iata_id) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			AirportDAO airportDAO = new AirportDAO(conn);

			Airport a = new Airport();
			a.setIataId(iata_id);

			airportDAO.deleteAirport(a);

			conn.commit();

			return "Airport deleted!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Airport could not be deleted.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
