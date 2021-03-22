package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.Booking;
import com.ss.UtopiaAirlines.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO extends BaseDAO<Booking>{

	public BookingDAO(Connection conn) {
		super(conn);
	}

	public Integer addBooking(Booking booking) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO booking (is_active, confirmation_code) VALUES (?, ?)", new Object[] {
				booking.getActive(),
				booking.getConfirmationCode()
		}, true);
	}

	public void updateBooking(Booking booking) throws ClassNotFoundException, SQLException {
		save("UPDATE booking SET is_active = ?, confirmation_code = ? WHERE id = ?", new Object[] {
				booking.getActive(),
				booking.getConfirmationCode(),
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

	public List<Booking> readBookingById(Booking booking) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking WHERE id = ?", new Object[] {
				booking.getId()
		});
	}

	@Override
	public List<Booking> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<Booking> bookings = new ArrayList<>();

		while(resultSet.next()) {
			Booking b = new Booking();

			b.setId(resultSet.getInt("id"));
			b.setActive(resultSet.getBoolean("is_active"));
			b.setConfirmationCode(resultSet.getString("confirmation_code"));

			bookings.add(b);
		}

		return bookings;
	}
}
