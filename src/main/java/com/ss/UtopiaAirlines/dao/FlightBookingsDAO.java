package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.FlightBookings;
import com.ss.UtopiaAirlines.entity.FlightBookings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightBookingsDAO extends BaseDAO<FlightBookings> {

	public FlightBookingsDAO(Connection conn) {
		super(conn);
	}

	public Integer addFlightBookings(FlightBookings flightBookings) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO flight_bookings (flight_id, booking_id) VALUES (?, ?)", new Object[] {
				flightBookings.getFlightId(),
				flightBookings.getBookingId()
		}, true);
	}

	public void updateFlightBookings(FlightBookings flightBookings) throws ClassNotFoundException, SQLException {
		save("UPDATE flight_bookings SET booking_id = ? WHERE flight_id = ?", new Object[] {
				flightBookings.getBookingId(),
				flightBookings.getFlightId()
		});
	}

	public void deleteFlightBookings(FlightBookings flightBookings) throws ClassNotFoundException, SQLException {
		save("DELETE FROM flight_bookings WHERE flight_id = ?", new Object[] {
				flightBookings.getFlightId()
		});
	}

	public List<FlightBookings> readAllFlightBookings() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight_bookings", null);
	}

	public List<FlightBookings> readAllFlightBookings(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight_bookings ORDER BY flight_id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<FlightBookings> readFlightBookingsById(FlightBookings flightBookings) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight_bookings WHERE flight_id = ?", new Object[] {
				flightBookings.getFlightId()
		});
	}

	@Override
	public List<FlightBookings> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<FlightBookings> flightBookings = new ArrayList<>();

		while(resultSet.next()) {
			FlightBookings bu = new FlightBookings();

			bu.setFlightId(resultSet.getInt("flight_id"));
			bu.setBookingId(resultSet.getInt("booking_id"));

			flightBookings.add(bu);
		}

		return flightBookings;
	}
}
