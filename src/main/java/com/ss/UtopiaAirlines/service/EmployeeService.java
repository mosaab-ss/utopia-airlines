package com.ss.UtopiaAirlines.service;
import com.ss.UtopiaAirlines.dao.AirplaneDAO;
import com.ss.UtopiaAirlines.dao.AirplaneTypeDAO;
import com.ss.UtopiaAirlines.dao.FlightDAO;
import com.ss.UtopiaAirlines.dao.RouteDAO;
import com.ss.UtopiaAirlines.dao.UserDAO;
import com.ss.UtopiaAirlines.entity.Airplane;
import com.ss.UtopiaAirlines.entity.AirplaneType;
import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmployeeService {

	Util util = new Util();

	public List<Flight> getFlights(int offset) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			FlightDAO flightDao = new FlightDAO(conn);
			RouteDAO routeDao = new RouteDAO(conn);

			List<Flight> flights = flightDao.readAllFlights(offset,10);

			for (Flight flight : flights) {
				flight.setRoutes(routeDao.readRouteById(flight.getRouteId()));
			}

			conn.commit();

			return flights;
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

	public Flight getFlight(int id) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			FlightDAO flightDao = new FlightDAO(conn);

			List<Flight> flights = flightDao.readFlightById(id);

			conn.commit();

			return flights.get(0);
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

	public String updateFlight(Flight flight, Flight updatedFlight) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			FlightDAO flightDao = new FlightDAO(conn);
			RouteDAO routeDAO = new RouteDAO(conn);

			if (!flight.getRoutes().get(0).getOriginId().equals(updatedFlight.getRoutes().get(0).getOriginId()) ||
					!flight.getRoutes().get(0).getDestinationId().equals(updatedFlight.getRoutes().get(0).getDestinationId())) {
				int routePK = routeDAO.addRoute(updatedFlight.getRoutes().get(0));
				updatedFlight.setRouteId(routePK);
			}

			flightDao.updateFlight(updatedFlight);

			conn.commit();

			return "Flight updated!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
			return "Flight could not be updated.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public String updateSeating(int seatingClass, int numberOfSeats, Flight flight) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			AirplaneType newType = new AirplaneType();
			newType.setMaxCapacity(flight.getAirplane().getAirplaneType().getMaxCapacity());
			newType.setBusinessClass(flight.getAirplane().getAirplaneType().getBusinessClass());
			newType.setFirstClass(flight.getAirplane().getAirplaneType().getFirstClass());

			switch (seatingClass) {
				case 1:
					newType.setFirstClass(numberOfSeats);
					break;
				case 2:
					newType.setBusinessClass(numberOfSeats);
					break;
				case 3:
					newType.setMaxCapacity(numberOfSeats);
					break;
			}

			FlightDAO flightDao = new FlightDAO(conn);
			AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(conn);
			AirplaneDAO airplaneDAO = new AirplaneDAO(conn);

			int aptPK = airplaneTypeDAO.addAirplaneType(newType);

			Airplane newAirplane = new Airplane();
			newAirplane.setTypeId(aptPK);
			int apPK = airplaneDAO.addAirplane(newAirplane);

			flight.setAirplaneId(apPK);
			flightDao.updateFlight(flight);

			conn.commit();

			return "Seating Updated!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
			return "Seating could not be updated.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

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
