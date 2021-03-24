package com.ss.UtopiaAirlines.service;

import com.ss.UtopiaAirlines.dao.FlightDAO;
import com.ss.UtopiaAirlines.dao.RouteDAO;
import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.Route;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

interface FlightCRUD {

	Util util = new Util();

	default String addFlight(Flight flight) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			FlightDAO flightDao = new FlightDAO(conn);

			flightDao.addFlight(flight);

			conn.commit();

			return "Flight added!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Flight could not be added.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default List<Flight> readFlights(int offset) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		FlightDAO flightDao = new FlightDAO(conn);
		RouteDAO routeDao = new RouteDAO(conn);

		List<Flight> flights = flightDao.readAllFlights(offset,10);

		for (Flight flight : flights) {
			flight.setRoutes(routeDao.readRouteById(flight.getRouteId()));
		}

		if (conn != null) {
			conn.close();
		}

		return flights;
	}

	default Flight readFlightById(int id) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		FlightDAO flightDao = new FlightDAO(conn);

		List<Flight> flights = flightDao.readFlightObjectById(id);

		if (conn != null) {
			conn.close();
		}

		return (flights.isEmpty())? null : flights.get(0);
	}

	default String updateFlight(Flight flight) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			FlightDAO flightDao = new FlightDAO(conn);
			RouteDAO routeDAO = new RouteDAO(conn);

			// Only if route is different
			if (!flight.getRoutes().isEmpty()) {
				Flight oldFlight = flightDao.readFlightObjectById(flight.getId()).get(0);

				Route oldRoute = oldFlight.getRoutes().get(0);
				Route newRoute = flight.getRoutes().get(0);

				if (!newRoute.getOriginId().equals(oldRoute.getOriginId()) ||
						!newRoute.getDestinationId().equals(oldRoute.getDestinationId())) {
					int routePK = routeDAO.addRoute(newRoute);

					flight.setRouteId(routePK);
				}
			}

			flightDao.updateFlight(flight);

			conn.commit();

			return "Flight updated!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Flight could not be updated.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default String deleteFlight(int id) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			FlightDAO flightDao = new FlightDAO(conn);

			flightDao.deleteFlight(id);

			conn.commit();

			return "Flight deleted!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Flight could not be deleted.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
