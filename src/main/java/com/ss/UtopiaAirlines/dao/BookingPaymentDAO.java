package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.BookingPayment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingPaymentDAO extends BaseDAO<BookingPayment> {
	
	public BookingPaymentDAO(Connection conn) {
		super(conn);
	}

	public Integer addBookingPayment(BookingPayment bookingPayment) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO booking_payment (booking_id, stripe_id, refunded) VALUES (?, ?, ?)", new Object[] {
				bookingPayment.getBookingId(),
				bookingPayment.getStripeId(),
				bookingPayment.getRefunded()
		}, true);
	}

	public void updateBookingPayment(BookingPayment bookingPayment) throws ClassNotFoundException, SQLException {
		save("UPDATE booking_payment SET stripe_id = ?, refunded = ? WHERE booking_id = ?", new Object[] {
				bookingPayment.getStripeId(),
				bookingPayment.getRefunded(),
				bookingPayment.getBookingId()
		});
	}

	public void deleteBookingPayment(BookingPayment bookingPayment) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking_payment WHERE booking_id = ?", new Object[] {
				bookingPayment.getBookingId()
		});
	}

	public List<BookingPayment> readAllBookingPayments() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_payment", null);
	}

	public List<BookingPayment> readAllBookingPayments(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_payment ORDER BY booking_id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<BookingPayment> readBookingPaymentsById(BookingPayment bookingPayment) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_payment WHERE booking_id = ?", new Object[] {
				bookingPayment.getBookingId()
		});
	}

	@Override
	public List<BookingPayment> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<BookingPayment> bookingPayments = new ArrayList<>();

		while(resultSet.next()) {
			BookingPayment bp = new BookingPayment();

			bp.setBookingId(resultSet.getInt("booking_payment"));
			bp.setStripeId(resultSet.getString("stripe_id"));
			bp.setRefunded(resultSet.getBoolean("refunded"));

			bookingPayments.add(bp);
		}

		return bookingPayments;
	}
}
