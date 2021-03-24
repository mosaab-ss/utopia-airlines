package com.ss.UtopiaAirlines.service;

import com.ss.UtopiaAirlines.dao.AirplaneTypeDAO;
import com.ss.UtopiaAirlines.dao.BookingDAO;
import com.ss.UtopiaAirlines.dao.BookingPaymentDAO;
import com.ss.UtopiaAirlines.dao.BookingUserDAO;
import com.ss.UtopiaAirlines.dao.FlightBookingsDAO;
import com.ss.UtopiaAirlines.dao.FlightDAO;
import com.ss.UtopiaAirlines.dao.PassengerDAO;
import com.ss.UtopiaAirlines.entity.AirplaneType;
import com.ss.UtopiaAirlines.entity.Booking;
import com.ss.UtopiaAirlines.entity.BookingPayment;
import com.ss.UtopiaAirlines.entity.BookingUser;
import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.FlightBookings;
import com.ss.UtopiaAirlines.entity.Passenger;
import com.ss.UtopiaAirlines.entity.User;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface TicketCRUD {

	Util util = new Util();

	default String addTicket(Flight flight, User user, Passenger passenger, BookingPayment bookingPayment, int seatClass, int[] remainingSeats) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			Booking booking = new Booking();
			booking.setActive(true);
			booking.setConfirmationCode(UUID.randomUUID().toString().replace("-", ""));
			booking.setSeatClass(seatClass);

			BookingUser bookingUser = new BookingUser();
			FlightBookings flightBookings = new FlightBookings();

			BookingDAO bookingDAO = new BookingDAO(conn);
			BookingPaymentDAO bookingPaymentDAO = new BookingPaymentDAO(conn);
			PassengerDAO passengerDAO = new PassengerDAO(conn);
			BookingUserDAO bookingUserDAO = new BookingUserDAO(conn);
			FlightBookingsDAO flightBookingsDAO = new FlightBookingsDAO(conn);
			FlightDAO flightDAO = new FlightDAO(conn);

			// Update flight to reduce seats
			if (booking.getSeatClass() == 1 && remainingSeats != null && remainingSeats[1] > 0) {
				flight.setReservedFirst(flight.getReservedFirst() + 1);
			} else if (booking.getSeatClass() == 2 && remainingSeats != null && remainingSeats[2] > 0) {
				flight.setReservedBusiness(flight.getReservedBusiness() + 1);
			} else if (booking.getSeatClass() == 3 && remainingSeats != null && remainingSeats[3] > 0) {
				flight.setReservedSeats(flight.getReservedSeats() + 1);
			} else {
				conn.rollback();
				return "Flight is full!";
			}

			// Add booking
			int bPK = bookingDAO.addBooking(booking);

			// Add booking payment
			bookingPayment.setBookingId(bPK);
			bookingPayment.setRefunded(false);
			bookingPaymentDAO.addBookingPayment(bookingPayment);

			// Add passenger
			passenger.setBookingId(bPK);
			int passengerPK = passengerDAO.addPassenger(passenger);

			// Add booking user
			bookingUser.setBookingId(bPK);
			bookingUser.setUserId(user.getId());
			bookingUserDAO.addBookingUser(bookingUser);

			// Add flight bookings
			flightBookings.setBookingId(bPK);
			flightBookings.setFlightId(flight.getId());
			flightBookingsDAO.addFlightBookings(flightBookings);

			// update flight seats
			flightDAO.updateFlight(flight);

			conn.commit();

			return "Flight booked successfully! Confirmation code: "+ booking.getConfirmationCode();
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Flight could not be booked.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default List<Booking> readTickets(int offset) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		BookingDAO bookingDAO = new BookingDAO(conn);

		List<Booking> bookings = bookingDAO.readAllBookingObject(offset, 10);

		if (conn != null) {
			conn.close();
		}

		return bookings;
	}

	default List<Booking> readTicketsByUserId(int offset, int userId) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		BookingDAO bookingDAO = new BookingDAO(conn);

		List<Booking> bookings = bookingDAO.readAllBookingByUserId(offset, 10, userId);

		if (conn != null) {
			conn.close();
		}

		return bookings;
	}

	default Booking readTicketById(int id) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		BookingDAO bookingDAO = new BookingDAO(conn);

		List<Booking> bookings = bookingDAO.readAllBookingObjectById(id);

		if (conn != null) {
			conn.close();
		}

		return (bookings.isEmpty())? null : bookings.get(0);
	}

	default String updateTicket(Booking booking) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			BookingDAO bookingDAO = new BookingDAO(conn);

			bookingDAO.updateBooking(booking);

			conn.commit();

			return "Ticket updated!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Ticket could not be updated.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default String updateTicketStateByUser(User user, Flight flight, Boolean state) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			BookingDAO bookingDAO = new BookingDAO(conn);
			FlightDAO flightDAO = new FlightDAO(conn);

			Booking booking = bookingDAO.readBookingById(user.getId()).get(0);
			Flight updatedFlight = flightDAO.readFlightsById(flight.getId()).get(0);

			int operation = (state)? 1 : -1;

			switch (booking.getSeatClass()) {
				case 1:
					updatedFlight.setReservedFirst(updatedFlight.getReservedFirst() + operation);
					break;
				case 2:
					updatedFlight.setReservedBusiness(updatedFlight.getReservedBusiness() + operation);
					break;
				case 3:
					updatedFlight.setReservedSeats(updatedFlight.getReservedSeats() + operation);
					break;
			}

			bookingDAO.updateBookingByUser(user, state);
			flightDAO.updateFlight(updatedFlight);

			conn.commit();

			return "Ticket updated successfully!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Ticket could not be updated.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	default String deleteTicket(int id) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			BookingDAO bookingDAO = new BookingDAO(conn);

			Booking b = new Booking();
			b.setId(id);

			bookingDAO.deleteBooking(b);

			conn.commit();

			return "Ticket deleted!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}

			return "Ticket could not be deleted.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
