package com.ss.UtopiaAirlines.service;

import com.ss.UtopiaAirlines.dao.AirplaneDAO;
import com.ss.UtopiaAirlines.dao.AirplaneTypeDAO;
import com.ss.UtopiaAirlines.dao.FlightDAO;
import com.ss.UtopiaAirlines.entity.Airplane;
import com.ss.UtopiaAirlines.entity.AirplaneType;
import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmployeeService extends BaseService implements FlightCRUD, SeatsCRUD, UserCRUD {

	Util util = new Util();

	@Override
	public String deleteFlight(int id) {
		return null;
	}

	@Override
	public String addFlight(Flight flight) {
		return null;
	}

	@Override
	public String addSeats(AirplaneType airplaneType) {
		return null;
	}

	@Override
	public String deleteSeats(int id) {
		return null;
	}

	@Override
	public String updateSeats(AirplaneType airplaneType) {
		return null;
	}

	@Override
	public String addUser(User user) {
		return null;
	}

	@Override
	public List<User> readUsers(int offset) {
		return null;
	}

	@Override
	public List<User> readUsersByRole(int offset, int roleId) throws SQLException, ClassNotFoundException {
		return null;
	}

	@Override
	public User readUserById(int id) {
		return null;
	}

	@Override
	public String updateUser(User user) {
		return null;
	}

	@Override
	public String deleteUser(int id) {
		return null;
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

			return "Seating could not be updated.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
