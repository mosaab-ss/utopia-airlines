package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.Booking;
import com.ss.UtopiaAirlines.entity.Passenger;
import com.ss.UtopiaAirlines.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingDAO extends BaseDAO<Booking>{

	public BookingDAO(Connection conn) {
		super(conn);
	}

	public Integer addBooking(Booking booking) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO booking (is_active, confirmation_code, seat_class) VALUES (?, ?, ?)", new Object[] {
				booking.getActive(),
				booking.getConfirmationCode(),
				booking.getSeatClass()
		}, true);
	}

	public void updateBooking(Booking booking) throws ClassNotFoundException, SQLException {
		save("UPDATE booking SET is_active = ?, confirmation_code = ?, seat_class = ? WHERE id = ?", new Object[] {
				booking.getActive(),
				booking.getConfirmationCode(),
				booking.getSeatClass(),
				booking.getId()
		});
	}

	public void updateBookingByUser(User user, Boolean isActive) throws ClassNotFoundException, SQLException {
		save("UPDATE booking " +
				"INNER JOIN booking_user AS bu " +
					"ON bu.user_id = ? " +
				"SET booking.is_active = ? " +
				"WHERE booking.id = bu.booking_id", new Object[] {
						user.getId(),
						isActive
		});
	}

	public void deleteBooking(Booking booking) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking WHERE id = ?", new Object[] {
				booking.getId()
		});
	}

	public List<Booking> readAllBooking() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking", null);
	}

	public List<Booking> readAllBooking(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking ORDER BY id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<Booking> readAllBookingByUserId(int offset, int limit, int userId) throws ClassNotFoundException, SQLException {
		return read("SELECT b.id, b.is_active, b.confirmation_code, b.seat_class FROM booking AS b " +
				"INNER JOIN booking_user " +
					"ON b.id = booking_user.booking_id " +
					"AND booking_user.user_id = ? " +
				"ORDER BY id ASC LIMIT ?, ?", new Object[] {
				userId,
				offset,
				limit
		});
	}

	public List<Booking> readAllBookingObject(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT booking.id, booking.is_active, booking.confirmation_code, booking.seat_class, " +
				"passenger.id AS pId, passenger.given_name, passenger.family_name, passenger.dob, passenger.gender, passenger.address " +
				"FROM booking " +
				"INNER JOIN passenger " +
					"ON booking.id = passenger.booking_id " +
				"ORDER BY booking.id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<Booking> readBookingById(int id) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking WHERE id = ?", new Object[] {
				id
		});
	}

	public List<Booking> readAllBookingObjectById(int id) throws ClassNotFoundException, SQLException {
		return read("SELECT booking.id, booking.is_active, booking.confirmation_code, booking.seat_class, " +
				"passenger.id AS pId, passenger.given_name, passenger.family_name, passenger.dob, passenger.gender, passenger.address " +
				"FROM booking " +
				"INNER JOIN passenger " +
				"ON booking.id = passenger.booking_id " +
				"WHERE booking.id = ?", new Object[] {id});
	}

	@Override
	public List<Booking> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<Booking> bookings = new ArrayList<>();

		while(resultSet.next()) {
			Booking b = new Booking();

			b.setId(resultSet.getInt("id"));
			b.setActive(resultSet.getBoolean("is_active"));
			b.setConfirmationCode(resultSet.getString("confirmation_code"));
			b.setSeatClass(resultSet.getInt("seat_class"));

			if (doesColumnExist(resultSet, "pId")) {
				Passenger p = new Passenger();

				p.setId(resultSet.getInt("pId"));
				p.setBookingId(resultSet.getInt("id"));
				p.setGivenName(resultSet.getString("given_name"));
				p.setFamilyName(resultSet.getString("family_name"));
				p.setDob(resultSet.getDate("dob"));
				p.setGender(resultSet.getString("gender"));
				p.setAddress(resultSet.getString("address"));

				b.setPassengers(Collections.singletonList(p));
			}

			bookings.add(b);
		}

		return bookings;
	}
}
