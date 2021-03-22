package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.BookingGuest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingGuestDAO extends BaseDAO<BookingGuest> {
	
	public BookingGuestDAO(Connection conn) {
		super(conn);
	}

	public Integer addBookingGuest(BookingGuest bookingGuest) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO booking_guest (booking_id, contact_email, contact_phone) VALUES (?, ?, ?)", new Object[] {
				bookingGuest.getBookingId(),
				bookingGuest.getContactEmail(),
				bookingGuest.getContactPhone()
		}, true);
	}

	public void updateBookingGuest(BookingGuest bookingGuest) throws ClassNotFoundException, SQLException {
		save("UPDATE booking_guest SET contact_email = ?, contact_phone = ? WHERE booking_id = ?", new Object[] {
				bookingGuest.getContactEmail(),
				bookingGuest.getContactPhone(),
				bookingGuest.getBookingId()
		});
	}

	public void deleteBookingGuest(BookingGuest bookingGuest) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking_guest WHERE booking_id = ?", new Object[] {
				bookingGuest.getBookingId()
		});
	}

	public List<BookingGuest> readAllBookingGuests() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_guest", null);
	}

	public List<BookingGuest> readAllBookingGuests(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_guest ORDER BY booking_id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<BookingGuest> readBookingGuestsById(BookingGuest bookingGuest) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_guest WHERE booking_id = ?", new Object[] {
				bookingGuest.getBookingId()
		});
	}

	@Override
	public List<BookingGuest> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<BookingGuest> bookingGuests = new ArrayList<>();

		while(resultSet.next()) {
			BookingGuest bg = new BookingGuest();

			bg.setBookingId(resultSet.getInt("booking_id"));
			bg.setContactEmail(resultSet.getString("contact_email"));
			bg.setContactPhone(resultSet.getString("contact_phone"));

			bookingGuests.add(bg);
		}

		return bookingGuests;
	}
}
