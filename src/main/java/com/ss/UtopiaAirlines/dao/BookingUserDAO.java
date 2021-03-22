package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.BookingUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingUserDAO extends BaseDAO<BookingUser> {
	
	public BookingUserDAO(Connection conn) {
		super(conn);
	}
	
	public Integer addBookingUser(BookingUser bookingUser) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO booking_user (booking_id, user_id) VALUES (?, ?)", new Object[] {
				bookingUser.getBookingId(),
				bookingUser.getUserId()
		}, true);
	}

	public void updateBookingUser(BookingUser bookingUser) throws ClassNotFoundException, SQLException {
		save("UPDATE booking_user SET user_id = ? WHERE booking_id = ?", new Object[] {
				bookingUser.getUserId(),
				bookingUser.getBookingId()
		});
	}

	public void deleteBookingUser(BookingUser bookingUser) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking_user WHERE booking_id = ?", new Object[] {
				bookingUser.getBookingId()
		});
	}

	public List<BookingUser> readAllBookingUsers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_user", null);
	}

	public List<BookingUser> readAllBookingUsers(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_user ORDER BY booking_id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<BookingUser> readBookingUsersById(BookingUser bookingUser) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_user WHERE booking_id = ?", new Object[] {
				bookingUser.getBookingId()
		});
	}

	@Override
	public List<BookingUser> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<BookingUser> bookingUsers = new ArrayList<>();

		while(resultSet.next()) {
			BookingUser bu = new BookingUser();

			bu.setBookingId(resultSet.getInt("booking_id"));
			bu.setUserId(resultSet.getInt("user_id"));

			bookingUsers.add(bu);
		}

		return bookingUsers;
	}
}
